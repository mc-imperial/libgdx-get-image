package uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage.Main;
import uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage.PrepareShaderException;

public class DesktopLauncher {

	private static final int COMPILE_ERROR_EXIT_CODE = 101;
	private static final int LINK_ERROR_EXIT_CODE = 102;

	public static void main (String[] args) {

		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			e.printStackTrace();
			if(e instanceof PrepareShaderException) {
				PrepareShaderException ex = (PrepareShaderException) e;
				if(ex.compileError) {
					System.exit(COMPILE_ERROR_EXIT_CODE);
				} else if(ex.linkError) {
					System.exit(LINK_ERROR_EXIT_CODE);
				}
				// else: fall through
			}
			// else:
			System.exit(1);
		});

    ArgumentParser parser = ArgumentParsers.newArgumentParser("java -jar libgdx-get-image-desktop.jar")
        .defaultHelp(true);

    parser.description("Takes a GLSL fragment shader as input, renders the shader "
				+ "on a flat rectangle, and outputs a PNG file of the rendered image "
				+ "(or just displays the image).");

		parser.addArgument("--frag", "-frag")
				.help("Path of the fragment shader to render.")
				.setDefault("shader.frag")
				.type(String.class);

		parser.addArgument("--output", "-output")
				.help("Output PNG file for --frag option.")
				.setDefault("output.png")
				.type(String.class);

		parser.addArgument("--persist", "-persist")
				.action(Arguments.storeTrue())
				.help("Display the image and don't close.");

		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
		}
		catch (ArgumentParserException e) {
			e.getParser().handleError(e);
			return;
		}

		String frag = ns.get("frag");
		String output = ns.get("output");
		if(ns.get("persist")) {
			output = null;
		}

		// end of args

		LwjglApplicationConfiguration.disableAudio = true;

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.useGL30 = false;
		config.useHDPI = true;
		config.title = "OGLTesting LibGDX client";
		config.resizable = false;

		Main main = new Main();

		main.fragmentShaderPath = frag;
		main.outputPngPath = output;

		new LwjglApplication(main, config);
	}
}
