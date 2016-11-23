package uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends ApplicationAdapter {

  private String vertexShader;
	private String fragmentShader;
  private MyShaderProgram program;
  private Mesh mesh;

  public String path = "";

  private final Pattern versionPattern = Pattern.compile("#version ...", Pattern.CASE_INSENSITIVE);

  @Override
  public void create () {
    vertexShader = Gdx.files.internal("shader.vert").readString();
    fragmentShader = Gdx.files.absolute(path + "shader.frag").readString();
    mesh = new Mesh(true, 4, 6, new VertexAttribute(VertexAttributes.Usage.Position, 2, "aVertexPosition"));
    mesh.setVertices(new float[]
        {
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, -1.0f,
            1.0f, 1.0f,

        });
    mesh.setIndices(new short[]{0, 1, 2, 2, 3, 0});

    Matcher matcher = versionPattern.matcher(fragmentShader);

    if(!matcher.find()) {
      throw new PrepareShaderException("MISSING VERSION NUMBER");
    }

    Gdx.app.log("Main","Found #version string.");

    Gdx.app.log("Main","Compiling shader.");

    program = new MyShaderProgram(matcher.group() + "\n" + vertexShader, fragmentShader);

    if(program.fragmentOrVertexFailedToCompile()) {
      throw new PrepareShaderException("COMPILE_ERROR\n" + program.getLog());
    }

    if(program.failedToLink()) {
      throw new PrepareShaderException("LINK_ERROR\n" + program.getLog());
    }

    if(!program.isCompiled()) {
      throw new PrepareShaderException("Did not expect to get here! " +
          "Shader failed to compile but there was no compile or link error?");
    }

    Gdx.app.log("Main","Compiled shader.");

  }

  @Override
  public void render () {

    Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
    checkForGlError();

    Gdx.gl.glClearColor(0, 0, 0, 0);
    checkForGlError();

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    checkForGlError();

    program.begin();
    checkForGlError();

    if (program.getUniformLocation("time") != -1) {
      program.setUniformf("time", 0.0f);
    }
    checkForGlError();

    if (program.getUniformLocation("mouse") != -1) {
      program.setUniformf("mouse", 0.0f, 0.0f);
    }
    checkForGlError();

    if (program.getUniformLocation("resolution") != -1) {
      program.setUniformf(
          "resolution",
          (float) Gdx.graphics.getBackBufferWidth(),
          (float) Gdx.graphics.getBackBufferHeight());
    }
    checkForGlError();

    if (program.getUniformLocation("injectionSwitch") != -1) {
      program.setUniformf("injectionSwitch", 0.0f, 1.0f);
    }
    checkForGlError();

    mesh.render(program, Gdx.gl.GL_TRIANGLES);
    checkForGlError();

    program.end();
    checkForGlError();

    Gdx.gl.glFlush();
    checkForGlError();

    Gdx.gl.glFinish();
    checkForGlError();
  }

  @Override
  public void dispose () {
    if(program != null) program.dispose();
    if(mesh != null) mesh.dispose();
  }

  private void checkForGlError() throws GlErrorException {
    int error = Gdx.gl.glGetError();
    if(error != GL20.GL_NO_ERROR) {
      throw new GlErrorException(error);
    }
  }

}
