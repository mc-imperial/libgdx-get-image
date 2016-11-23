package uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MyShaderProgram extends ShaderProgram {

  private boolean calledCreatedProgram;

  public MyShaderProgram(String vertexShader, String fragmentShader) {
    super(vertexShader, fragmentShader);
  }

  public MyShaderProgram(FileHandle vertexShader, FileHandle fragmentShader) {
    super(vertexShader, fragmentShader);
  }

  @Override
  protected int createProgram() {
    int res = super.createProgram();
    calledCreatedProgram = true;
    return res;
  }

  public boolean fragmentOrVertexFailedToCompile() {
    return !calledCreatedProgram;
  }

  public boolean failedToLink() {
    return calledCreatedProgram && !isCompiled();
  }
}
