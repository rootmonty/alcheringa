package com.iitg.alcher.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.iitg.alcher.R;
import com.iitg.alcher.adapter.EventCustomAdapter;
import com.iitg.alcher.database.DatabaseHandler;
import com.iitg.alcher.dialog.EventDetailDialog;
import com.iitg.alcher.model.EventObj;

@SuppressLint("NewApi")
public class OngoingFragment extends Fragment {
	ListView recordlist;
	ArrayList<EventObj> details;
	public OngoingFragment(){}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_ongoing, container, false);
		
		String fontPath = "fonts/c.ttf";
		TextView txtGhost = (TextView) rootView.findViewById(R.id.ghost);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        txtGhost.setTypeface(tf);
		
		new SimpleDateFormat("kk:mm");
		Calendar now = Calendar.getInstance();
		int dayYear = now.get(Calendar.DAY_OF_YEAR);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		String currentHour = Integer.toString(hour);
		String currentMinute = Integer.toString(minute);
		if(Integer.parseInt(currentHour) < 10)
		{
			currentHour = "0" + currentHour;
		}
		if(Integer.parseInt(currentMinute) < 10)
		{
			currentMinute = "0" + currentMinute;
		}
		String timeNow = currentHour +":"+ currentMinute;

		int EventDay;
		String TimeVar;
		switch(dayYear)
		{
		case 30:
			EventDay = 0;
			TimeVar = "C_time0";
			break;

		case 31:
			EventDay = 1;
			TimeVar = "C_time1";
			break;

		case 32:
			EventDay = 2;
			TimeVar = "C_time2";
			break;

		case 33:
			EventDay = 3;
			TimeVar = "C_time3";
			break;
			
		default:
			EventDay = 9;
			TimeVar = "C_time3";
			break;

		}
		
		String query = "SELECT  * FROM TableEvents WHERE C_day LIKE '" + '%' + EventDay + '%' + "' AND " + "SUBSTR(" + TimeVar + ",1,5) <= '" + timeNow + "' AND SUBSTR(" + TimeVar + ",7,11) >= '" + timeNow + "';";
		recordlist = (ListView) rootView.findViewById(R.id.list_data);
		details = new ArrayList<EventObj>();

		DatabaseHandler db = new DatabaseHandler(getActivity());
		details = db.getresultforquery(query);
		recordlist.setAdapter(new EventCustomAdapter(details,getActivity()));

		recordlist.setOnItemClickListener(new OnItemClickListener() {
			@Override           	
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				TextView text = (TextView) view.findViewById(R.id.title);
				String tEXT = text.getText().toString();
				EventDetailDialog Custom_Dialog = new EventDetailDialog(getActivity(),tEXT);
				Custom_Dialog.show();

			}
		});


		return rootView;
	}
}