package com.iitg.alcher.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iitg.alcher.R;
import com.iitg.alcher.model.EventObj;

public class MapCustomAdapter extends BaseAdapter {

	private ArrayList<EventObj> _data;
	Context _c;

	public MapCustomAdapter (ArrayList<EventObj> data, Context c){
		_data = data;
		_c = c;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return _data.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return _data.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.list_row_map, null);
		}

		
		TextView tv_id = (TextView) v.findViewById(R.id.VenueIdNo);
		TextView tv_name = (TextView) v.findViewById(R.id.VenueName);
		TextView tv_event= (TextView) v.findViewById(R.id.VenueEvent);

		EventObj event = _data.get(position);
		
		int VenueIdToSet;
		switch(Integer.parseInt(event.getVenueId()))
		{
		case 1 :
			VenueIdToSet = 1;
			break;
		case 2 :
			VenueIdToSet = 3;
			break;
		case 3 :
			VenueIdToSet = 4;
			break;
		case 4 :
			VenueIdToSet = 5;
			break;
		case 5 :
			VenueIdToSet = 6;
			break;
		case 6 :
			VenueIdToSet = 7;
			break;
		case 7 :
			VenueIdToSet = 1;
		case 8: 
			VenueIdToSet = 1;
		case 9: 
			VenueIdToSet = 5;
		case 10: 
			VenueIdToSet = 1;
		case 11: 
			VenueIdToSet = 6;
		case 12: 
			VenueIdToSet = 7;
			break;
		case 13 :
			VenueIdToSet = 2;
			break;
		case 14 :
			VenueIdToSet = 2;
			break;
		case 15 :
			VenueIdToSet = 2;
			break;
		case 16 :
			VenueIdToSet = 2;
			break;
		default:
			VenueIdToSet = 4;
			break;
		}
		                       
		tv_id.setText(Integer.toString(VenueIdToSet));
		tv_name.setText(event.getVenue());
		tv_event.setText(event.getName());

		return v;
	}
}
