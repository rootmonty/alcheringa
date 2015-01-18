package com.iitg.alcheringa.fragments;

import static com.iitg.alcheringa.utils.CommonUtilities.AlcheringaDay0_DayOfYear;

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

import com.iitg.alcheringa.R;
import com.iitg.alcheringa.adapter.EventCustomAdapter;
import com.iitg.alcheringa.database.DatabaseHandler;
import com.iitg.alcheringa.dialog.EventDetailDialog;
import com.iitg.alcheringa.model.EventObj;

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

		String timeVar;
		switch(dayYear)
		{
		case AlcheringaDay0_DayOfYear+0:
			timeVar = "C_time0";
			break;

		case AlcheringaDay0_DayOfYear+1:
			timeVar = "C_time1";
			break;

		case AlcheringaDay0_DayOfYear+2:
			timeVar = "C_time2";
			break;

		case AlcheringaDay0_DayOfYear+3:
			timeVar = "C_time3";
			break;
			
		default:
			timeVar = "NULL";
			break;

		}
		
		recordlist = (ListView) rootView.findViewById(R.id.list_data);
		details = new ArrayList<EventObj>();

		
		if(!timeVar.equalsIgnoreCase("NULL")){
			DatabaseHandler db = new DatabaseHandler(getActivity());
			String query = "SELECT  * FROM TableEvents WHERE " + timeVar + " != 'NULL' AND " + "SUBSTR(" + timeVar + ",1,5) <= '" + timeNow + "' AND SUBSTR(" + timeVar + ",7,11) >= '" + timeNow + "';";
			details = db.getresultforquery(query);
		}
		recordlist.setAdapter(new EventCustomAdapter(details,getActivity()));

		recordlist.setOnItemClickListener(new OnItemClickListener() {
			@Override           	
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				EventDetailDialog Custom_Dialog = new EventDetailDialog(getActivity(),details.get(position).getId());
		        Custom_Dialog.show();

			}
		});


		return rootView;
	}
}