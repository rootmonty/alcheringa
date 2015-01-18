package com.iitg.alcheringa.dialog;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.iitg.alcheringa.R;

/**
 * This is a custom dialog class that will hold a tab view with 2 tabs.
 * Tab 1 will be a list view. Tab 2 will be a list view.
 * 
 */
public class EventDetailDialog extends Dialog
{
	ArrayList<String> ArrayAbout = new ArrayList<String>();
	String data;
	String eventId;
	String[] valuespa;
	Map<String, String> map = new HashMap<String, String>();
	Map<String, String> mapContactList = new HashMap<String, String>();
	ArrayList<String> textToShow = new ArrayList<String>();

	public EventDetailDialog(final Context context, String value)
	{
		super(context);
		// get this window's layout parameters so we can change the position
		WindowManager.LayoutParams params = getWindow().getAttributes(); 
		params.x = 0;
		params.y = 150;
		this.getWindow().setAttributes(params); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.eventdetail_dialog_layout);

		// get our tabHost from the xml
		TabHost tabs = (TabHost)findViewById(R.id.TabHost01);
		tabs.setup();

		// create tab 1
		TabHost.TabSpec tab1 = tabs.newTabSpec("tab1");
		tab1.setContent(R.id.tv_about);
		tab1.setIndicator("About");
		tabs.addTab(tab1);
		// create tab 2
		TabHost.TabSpec tab2 = tabs.newTabSpec("tab2");
		tab2.setContent(R.id.listdata);
		tab2.setIndicator("Contact");
		tabs.addTab(tab2);

		eventId = value;


		TextView tv_about = (TextView)findViewById(R.id.tv_about);
		try {
			readAboutEvent(context);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		tv_about.setText(map.get(eventId));


		final ListView recordlist = (ListView)findViewById(R.id.listdata);
		try {
			readContacts(context);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(context, android.R.layout.simple_list_item_1, textToShow);
		recordlist.setAdapter(adapter);
		recordlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id)
			{

				String title = recordlist.getItemAtPosition(position).toString();
				final String phnNumb = mapContactList.get(title);
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+phnNumb));
				callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				context.startActivity(callIntent);
				dismiss();
			}           
		});

	}

	private void readAboutEvent(Context context) throws FileNotFoundException {
		InputStream is = context.getResources().openRawResource(R.raw.aboutevents);
		try {
			Scanner inputstream = new Scanner(is);
			while(inputstream.hasNextLine())
			{
				data = inputstream.nextLine();  // gets a whole line
				valuespa = data.split(",");
				map.put(valuespa[0],valuespa[1]);
			}
			inputstream.close();

		}
		finally{}

	}



	private void readContacts(Context context) throws FileNotFoundException {
		InputStream is = context.getResources().openRawResource(R.raw.aboutevents);

		try {
			Scanner inputstream = new Scanner(is);
			while(inputstream.hasNextLine())
			{
				data = inputstream.nextLine();  // gets a whole line
				valuespa = data.split(",");
				if(valuespa[0].equals(eventId))
				{
					for(int i = 2;i<valuespa.length;i=i+2)
					{
						textToShow.add(valuespa[i]);
						mapContactList.put(valuespa[i], valuespa[i+1]);
					}
				}


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
