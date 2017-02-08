# libgdx-get-image

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
* 1 for everything else.

Check [DesktopLauncher.java](desktop/src/uk/ac/ic/doc/multicore/oglfuzzer/libgdx/getimage/desktop/DesktopLauncher.java)
for up-to-date exit codes.

## Android usage:

* Copy the apk to the device (or download directly) and install it.
* Copy or download your shader to the "external storage" of the device
(where you have directories like Downloads, Videos, etc.).
Name it `shader.frag`.
* Run the app.
No image file is output but the shader will be rendered. 
