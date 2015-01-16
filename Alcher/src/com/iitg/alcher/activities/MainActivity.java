package com.iitg.alcher.activities;

import static com.iitg.alcher.utils.CommonUtilities.DEVELOPERS;
import static com.iitg.alcher.utils.CommonUtilities.SENDER_ID;
import static com.iitg.alcher.utils.CommonUtilities.SHARED_PREF_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.iitg.alcher.R;
import com.iitg.alcher.adapter.NavDrawerListAdapter;
import com.iitg.alcher.fragments.ConcertFragment;
import com.iitg.alcher.fragments.ContactsFragment;
import com.iitg.alcher.fragments.HomeFragment;
import com.iitg.alcher.fragments.MapFragment;
import com.iitg.alcher.fragments.OngoingFragment;
import com.iitg.alcher.fragments.QuestionFragment;
import com.iitg.alcher.fragments.ScheduleFragment;
import com.iitg.alcher.fragments.SponsorFragment;
import com.iitg.alcher.model.NavDrawerItem;
import com.iitg.alcher.utils.ConnectionDetector;
import com.iitg.alcher.utils.Notifications;
import com.iitg.alcher.utils.ServerUtilities;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBar actionBar;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private ConnectionDetector cd;
	private AsyncTask<Void, Void, Void> mRegisterTask;

	public static String name;
	public static String email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		initializeDirectories();

		setActionBar();
		
		setNavigationDrawer();
		
		
		Intent intent = getIntent();
		String position = "NULL";
		if(intent.hasExtra("TAB"))
		{
			position = intent.getStringExtra("TAB");
		}
		if (savedInstanceState == null) {
			if(position.equals("NULL"))
			{
				displayView(0);
			}
			else
			{
				displayView(Integer.parseInt(position));

			}
		}


		SharedPreferences prefs = this.getSharedPreferences(SHARED_PREF_NAME,0);
		if(!prefs.getBoolean("GCM_REGISTERED", false)){
			tryRegisterUser();
		}
		
		

	}

	private void setNavigationDrawer() {
		mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		for(int i=0;i<navMenuTitles.length;i++){
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons
					.getResourceId(i, -1)));
		}
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		/* Code to increase drawer sensibility area - start */
		Field mDragger = null;
		try {
			mDragger = mDrawerLayout.getClass()
					.getDeclaredField("mLeftDragger");
			mDragger.setAccessible(true);
			ViewDragHelper draggerObj = (ViewDragHelper) mDragger
					.get(mDrawerLayout);
			Field mEdgeSize = draggerObj.getClass().getDeclaredField(
					"mEdgeSize");
			mEdgeSize.setAccessible(true);
			int edge = mEdgeSize.getInt(draggerObj);
			mEdgeSize.setInt(draggerObj, edge * 3); // optimal value as for me,
													// you may set any constant
													// in dp
		} catch (Exception e) {
			e.printStackTrace();
		} 
		/* Code to increase drawer sensibility area - end */

		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	private void setActionBar() {
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#021819")));
		int actionBarTitleId = Resources.getSystem().getIdentifier(
				"action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.WHITE);
			}
		}
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	private void initializeDirectories() {
		try {
				File file = new File(Environment.getExternalStorageDirectory()
						+ "/alcheringa/schedule.csv");
				if (!file.exists()){
					SetDirectory(1);
				}

				File file2 = new File(Environment.getExternalStorageDirectory()
						+ "/alcheringa/news.txt");
				if (!file2.exists()){
					SetDirectory(2);
				}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * -- Check to see if the sdCard is mounted and create a directory w/in it
	 * ========================================================================
	 * 
	 * @throws IOException
	 **/
	private void SetDirectory(int x) throws IOException {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {

			String extStorageDirectory = Environment
					.getExternalStorageDirectory().toString();

			File txtDirectory = new File(extStorageDirectory + "/alcheringa/");
			txtDirectory.mkdirs();// Have the object build the directory
			FileIntialise(x);
			// structure, if needed.

		} else if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {

			Toast.makeText(getBaseContext(), "SdCard is missing",
					Toast.LENGTH_SHORT).show();
		}

	}

	private void FileIntialise(int x) throws IOException {
		File nfile = new File(Environment.getExternalStorageDirectory()
				+ "/alcheringa/");
		nfile.mkdir();

		if (x == 1) {
			InputStream inSchedule = getResources().openRawResource(
					R.raw.schedule);
			FileOutputStream outSchedule = new FileOutputStream(
					Environment.getExternalStorageDirectory()
							+ "/alcheringa/schedule.csv");
			byte[] buffSchedule = new byte[1024];
			int readSchedule = 0;
			try {
				while ((readSchedule = inSchedule.read(buffSchedule)) > 0) {
					outSchedule.write(buffSchedule, 0, readSchedule);
				}
			} finally {
				inSchedule.close();

				outSchedule.close();
			}
		}

		if (x == 2) {
			InputStream inNews = getResources().openRawResource(R.raw.news);
			FileOutputStream outNews = new FileOutputStream(
					Environment.getExternalStorageDirectory()
							+ "/alcheringa/news.txt");
			byte[] buffNews = new byte[1024];
			int readNews = 0;
			try {
				while ((readNews = inNews.read(buffNews)) > 0) {
					outNews.write(buffNews, 0, readNews);
				}
			} finally {
				inNews.close();

				outNews.close();
			}
		}

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@SuppressLint("NewApi")
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new OngoingFragment();
			break;
		case 2:
			fragment = new ScheduleFragment();
			break;
		case 3:
			Intent slideactivity = new Intent(MainActivity.this,
					CompetitionActivity.class);
			slideactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Bundle bndlanimation = ActivityOptions.makeCustomAnimation(
					getApplicationContext(), R.anim.animation,
					R.anim.animation2).toBundle();
			startActivity(slideactivity, bndlanimation);
			this.finish();
			// mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 4:
			fragment = new ConcertFragment();
			break;
		case 5:
			fragment = new MapFragment();
			break;
		case 6:
			fragment = new QuestionFragment();
			break;
		case 7:
			fragment = new ContactsFragment();
			break;
		case 8:
			fragment = new SponsorFragment();
			break;
		case 9:
			Intent intent = new Intent(MainActivity.this,
					ThankYouActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Bundle bndlanimation2 = ActivityOptions.makeCustomAnimation(
					getApplicationContext(), R.anim.animation,
					R.anim.animation2).toBundle();
			startActivity(intent, bndlanimation2);
			this.finish();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.setCustomAnimations(R.anim.fragment_animation_fade_in,
					R.anim.fragment_animation_fade_out);
			transaction.replace(R.id.frame_container, fragment);
			transaction.commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			AlertDialog ad = new AlertDialog.Builder(this).create();
			ad.setCancelable(false); // This blocks the 'BACK' button
			ad.setMessage(DEVELOPERS);
			ad.setButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			ad.show();
			return true;
		case R.id.action_notification:
			Intent intent = new Intent(MainActivity.this, Notifications.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@SuppressLint("NewApi")
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void tryRegisterUser() {
		name = "Alcheringa app user";
		email = "";

		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				email = account.name;
				break;
			}
		}

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			return;
		}

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {
			Log.d("GCM", "not registered");
			// Registration is not present, register now with GCM
			GCMRegistrar.register(getApplicationContext(), SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
			} else {
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, MainActivity.name,
								MainActivity.email, regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}

	}
	
	public void openDrawer(){
		mDrawerLayout.openDrawer(mDrawerList);
	}

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		super.onDestroy();
	}

}
