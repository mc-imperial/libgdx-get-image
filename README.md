# libgdx-get-image

Takes a GLSL fragment shader as input, renders the shader on a flat rectangle, 
and outputs a PNG file of the rendered image (or just displays the image).

The vec2 array of vertices:

```
{
    -1.0f, 1.0f,
    -1.0f, -1.0f,
    1.0f, -1.0f,
    1.0f, 1.0f,

}
```

The indices: `{0, 1, 2, 2, 3, 0}`.

The vertex shader is:

```
#version X
attribute vec3 aVertexPosition;

void main(void) {
    gl_Position = vec4(aVertexPosition, 1.0);
}
```

where X is replaced with the version of the fragment shader.
I believe the vertex shader will need to be adapted when we start testing
newer versions of GLSL; we have so far only tested: 100, 440.


The rendering code does the following:

* Set the viewport.
* glClearColor(0, 0, 0, 0);
* glClear(GL_COLOR_BUFFER_BIT);

The rendering code sets the values of the following uniforms, if they are present:

* time: 0.0f
* mouse: (0.0f, 0.0f)
* resolution: (width, height) [of the display, as floats]
* injectionSwitch (0.0f, 1.0f)

The location of the uniforms is checked and the value is set on every frame,
which is unnecessary.

The rectangle is rendered using `glDrawElements` and `GL_TRIANGLES`.

Finally: 

* glFlush()
* glFinish()

The contents of the frame is then captured (after rendering a few times initially).

The rendering code can be see in [Main.java](core/src/uk/ac/ic/doc/multicore/oglfuzzer/libgdx/getimage/Main.java#L78).

Some of the rendering code may be unclear as we use LibGDX.
A more precise, low-level version of the code is available in our WebGL client:
https://github.com/mc-imperial/shader-compiler-bugs/blob/master/utils/webgl/webgl_viewer.html#L59

In the WebGL version, the uniforms are only set once.




## Desktop usage:

```bash
$ java -jar libgdx-get-image-desktop.jar --help
usage: java -jar libgdx-get-image-desktop.jar
       [-h] [--frag FRAG] [--output OUTPUT] [--persist]

Takes a GLSL fragment shader as input, renders the shader on a flat rectangle, 
and outputs a PNG file of the rendered image (or just displays the image).

optional arguments:
  -h, --help             show this help message and exit
  --frag FRAG, -frag FRAG
                         Path of the fragment shader to render. (default: shader.frag)
  --output OUTPUT, -output OUTPUT
                         Output PNG file for --frag option. (default: output.png)
  --persist, -persist    Display the image and don't close. (default: false)
```

E.g. 

```bash
# creates temp.png from variant_27.frag.
java -jar libgdx-get-image-desktop.jar --frag variant_27.frag --output temp.png

# renders variant_27.frag in a window at 60FPS.
java -jar libgdx-get-image-desktop.jar --frag variant_27.frag --persist
```

### Exit codes

The exit codes should match the [get-image](http://github.com/mc-imperial/get-image/) project.
Thus, they are currently:

* 101 for fragment shader compile error.
* 102 for fragment shader link error.
* 1 for all other failures.
* 0 for success.

Check [DesktopLauncher.java](desktop/src/uk/ac/ic/doc/multicore/oglfuzzer/libgdx/getimage/desktop/DesktopLauncher.java)
for up-to-date exit codes.

## Android usage:

* Copy the apk to the device (or download directly) and install it.
* Copy or download your shader to the "external storage" of the device
(where you have directories like Downloads, Videos, etc.).
Name it `shader.frag`.
* Run the app.
No image file is output but the shader will be rendered. 
