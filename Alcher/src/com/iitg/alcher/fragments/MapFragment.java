package com.iitg.alcher.fragments;

import static com.iitg.alcher.utils.CommonUtilities.AlcheringaDay0_DayOfYear;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.diegocarloslima.byakugallery.lib.TileBitmapDrawable;
import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.iitg.alcher.R;
import com.iitg.alcher.adapter.MapCustomAdapter;
import com.iitg.alcher.database.DatabaseHandler;
import com.iitg.alcher.model.EventObj;

@SuppressLint("NewApi")
public class MapFragment extends Fragment {
	ListView recordlist;
	ArrayList<EventObj> details;
	public MapFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_map, container, false);
		

		TouchImageView myImage = (TouchImageView) rootView.findViewById(R.id.my_image);
		InputStream is = getActivity().getResources().openRawResource(R.raw.iitgmap);
		TileBitmapDrawable.attachTileBitmapDrawable(myImage, is, null, null);
		
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
		
		

		recordlist = (ListView) rootView.findViewById(R.id.list_view_map);
		details = new ArrayList<EventObj>();
		
		if(!timeVar.equalsIgnoreCase("NULL")){
			DatabaseHandler db = new DatabaseHandler(getActivity());
			String query = "SELECT  * FROM TableEvents WHERE " + timeVar + " != 'NULL' AND " + "SUBSTR(" + timeVar + ",1,5) <= '" + timeNow + "' AND SUBSTR(" + timeVar + ",7,11) >= '" + timeNow + "';";
			details = db.getresultforquery(query);
			db.close();
		}
		
		recordlist.setAdapter(new MapCustomAdapter(details,getActivity()));

		return rootView;
	}
}