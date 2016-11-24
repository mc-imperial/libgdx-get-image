package uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage.Main;

public class DesktopLauncher {
	public static void main (String[] args) {

    ArgumentParser parser = ArgumentParsers.newArgumentParser("libgdx-get-image")
        .defaultHelp(true);

		parser.addArgument("--frag", "-frag")
				.help("Path of the fragment shader to render.")
				.setDefault("shader.frag")
				.type(String.class);

		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
		}
		catch (ArgumentParserException e) {
			e.getParser().handleError(e);
			return;
		}

		String frag = ns.get("frag");

		// end of args

		LwjglApplicationConfiguration.disableAudio = true;

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.useGL30 = false;
		config.useHDPI = true;
		config.title = "OGLTesting LibGDX client";
		config.resizable = false;
		config.foregroundFPS = 10;
		config.backgroundFPS = 10;

		Main main = new Main();

		main.fragmentShaderPath = frag;

		new LwjglApplication(main, config);
	}
}
