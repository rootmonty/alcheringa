package com.iitg.alcheringa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.iitg.alcheringa.R;

public class Notifications extends Activity{

	ArrayList<String> textToShow = new ArrayList<String>();
	String data;
	ListView recordlist;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notifications);

		actionBar =  getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.BLACK);
			}
		}

		recordlist = (ListView) findViewById(R.id.list_data);
		try {
			readnotifications();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, textToShow);
		recordlist.setAdapter(adapter);
	}


	private void readnotifications() throws FileNotFoundException {
		File dir = Environment.getExternalStorageDirectory();
		File yourFile = new File(dir, "alcheringa/notifications.txt");
		FileInputStream is = null;	
		is = new FileInputStream(yourFile);

		try {
			Scanner inputstream = new Scanner(is);
			while(inputstream.hasNextLine())
			{
				data = inputstream.nextLine();  // gets a whole line
				textToShow.add(data);

			}
			inputstream.close();

		}
		finally{}

	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	

}
