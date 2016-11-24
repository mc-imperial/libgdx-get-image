package uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage.Main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int res = ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if(res != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(
					this,
					new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
					0);
		}
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.disableAudio = true;
		config.useWakelock = true;
		config.useCompass = false;
		config.useAccelerometer = false;
		config.useImmersiveMode = true;
//		config.hideStatusBar = true;
		config.useGL30 = true;
		Main main = new Main();
		main.fragmentShaderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/shader.frag";
		initialize(main, config);
	}
}
