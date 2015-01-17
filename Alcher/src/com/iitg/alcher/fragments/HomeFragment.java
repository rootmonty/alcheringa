package com.iitg.alcher.fragments;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.iitg.alcher.R;
import com.iitg.alcher.activities.MainActivity;
import com.iitg.alcher.database.DatabaseHandler;
import com.iitg.alcher.dialog.NewsDialog;

public class HomeFragment extends Fragment {

	private TextSwitcher mSwitcher;
	private LinearLayout linearLayout;
	private ArrayList<String> textToShow = new ArrayList<String>();
	private int messageCount;
	private int currentIndex = -1;
	private String data;
	private View rootView;
	private Thread background;
	private final String file_url_news = "https://dl.dropboxusercontent.com/s/dgllbdp82fsyttv/news.txt?dl=1&token_hash=AAECvCXQeECMS6ZP6hia8VdpqTGl3rfFCSX-5tT7PyVXMw";
	private final String file_url_schedule = "https://dl.dropboxusercontent.com/s/9cfjb1j70t6b8qw/schedule.csv?dl=1&token_hash=AAEvxE1JpxhLJ_DjWOUi-Qp1MLl8QHalRQHmCBwe0lI5dQ";
	
	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_home, container, false);
		linearLayout = (LinearLayout)rootView.findViewById(R.id.linearLayoutHome);
		linearLayout.setOnClickListener(myhandler1);
		
		
		initialiseTextSwitcher();

		try {
			readnews();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		new DownloadFileFromURL_SCHEDULE().execute(file_url_schedule);
		new DownloadFileFromURL_NEWS().execute(file_url_news);
		return rootView;
	}

	private void initialiseTextSwitcher() {
		mSwitcher = (TextSwitcher) rootView.findViewById(R.id.textSwitcher);
		mSwitcher.setFactory(new ViewFactory() {
			public View makeView() {
				TextView myText = new TextView(getActivity());
				myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
				myText.setTextSize(28);
				myText.setTextColor(Color.BLACK);
				return myText;
			}
		});

		// Declare the in and out animations and initialize them
		Animation in = AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.fade_in);
		Animation out = AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.fade_out);
		mSwitcher.setInAnimation(in);
		mSwitcher.setOutAnimation(out);

		mSwitcher.setOnClickListener(myhandler1);
		
		/***********************************************************/

		// Create Inner Thread Class
		background = new Thread(new Runnable() {

			// After call for background.start this run method call
			public void run() {
				try {
					while(true)
					{
						handler.sendEmptyMessage(0);
						SystemClock.sleep(2200);
					}
				} catch (Throwable t) {
				}
			}

			// Define the Handler that receives messages from the thread and update the progress
			@SuppressLint("HandlerLeak")
			private final Handler handler = new Handler() {

				public void handleMessage(Message msg) {
					doUpdate();

				}
			};

		});
		// Start Thread
		background.start();  //After call start method thread called run Method

		/***********************************************************************************/
	}

	View.OnClickListener myhandler1 = new View.OnClickListener() {
		public void onClick(View v) {
			int id = v.getId();
			switch(id){
			case R.id.textSwitcher:
				Intent intent = new Intent(getActivity(), NewsDialog.class);
				startActivity(intent);
				break;
			case R.id.linearLayoutHome:
				((MainActivity) getActivity()).openDrawer();
				break;
			default:
				break;
			}
			
		}
	};

	class DownloadFileFromURL_SCHEDULE extends
			AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				int lenghtOfFile = conection.getContentLength();
				InputStream input = new BufferedInputStream(url.openStream(),
						8192);
				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/alcheringa/schedule.csv");
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					output.write(data, 0, count);
				}
				output.flush();
				output.close();
				input.close();
				
				DatabaseHandler db = new DatabaseHandler(getActivity());
				db.ManuallyUpgrade();
				db.close();

			} catch (Exception e) {
				Log.e("Error: ", " " + e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
		}

	}

	class DownloadFileFromURL_NEWS extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				int lenghtOfFile = conection.getContentLength();
				InputStream input = new BufferedInputStream(url.openStream(),
						8192);
				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/alcheringa/news.txt");
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					output.write(data, 0, count);
				}
				output.flush();
				output.close();
				input.close();

				try {
					readnews();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			
		}

	}

	private void readnews() throws FileNotFoundException {
		textToShow.clear();
		File dir = Environment.getExternalStorageDirectory();
		File yourFile = new File(dir, "alcheringa/news.txt");
		FileInputStream is = null;
		is = new FileInputStream(yourFile);

		try {
			Scanner inputstream = new Scanner(is);
			while (inputstream.hasNextLine()) {
				data = inputstream.nextLine(); // gets a whole line
				textToShow.add(data);

			}
			inputstream.close();

			messageCount = textToShow.size();

		} finally {
		}

		doUpdate();
	}

	private void doUpdate() {
		currentIndex++;
		if (currentIndex == messageCount) {
			currentIndex = 0;
		}
		mSwitcher.setText(textToShow.get(currentIndex));
	}

//	@Override
//	public void onResume() {
//		Log.d("thread", "started");
//		background.start();
//	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("thread", "interuppted");
		background.interrupt();
	}
	
	
	
	

}
