package com.iitg.alcheringa.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.iitg.alcheringa.R;

public class ThankYouActivity extends Activity{

	private static String TAG = ThankYouActivity.class.getName();
	private static long SLEEP_TIME = 2;    // Sleep for some time
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,     WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
		setContentView(R.layout.thankyou);

		IntentLauncher launcher = new IntentLauncher();
		launcher.start();


	}

	private class IntentLauncher extends Thread {
		@Override
		/**
		 * Sleep for some time and than start new activity.
		 */
		public void run() {
			try {
				// Sleeping
				Thread.sleep(SLEEP_TIME*1000);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			android.os.Process.killProcess(android.os.Process.myPid());
		}

	}
}




