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
		
		int venueIdToSet;
		switch(Integer.parseInt(event.getVenueId()))
		{
		case 1 :
			venueIdToSet = 1;
			break;
		case 2 :
			venueIdToSet = 3;
			break;
		case 3 :
			venueIdToSet = 4;
			break;
		case 4 :
			venueIdToSet = 5;
			break;
		case 5 :
			venueIdToSet = 6;
			break;
		case 6 :
			venueIdToSet = 7;
			break;
		case 7 :
			venueIdToSet = 1;
		case 8: 
			venueIdToSet = 1;
		case 9: 
			venueIdToSet = 5;
		case 10: 
			venueIdToSet = 1;
		case 11: 
			venueIdToSet = 6;
		case 12: 
			venueIdToSet = 7;
			break;
		case 13 :
			venueIdToSet = 2;
			break;
		case 14 :
			venueIdToSet = 2;
			break;
		case 15 :
			venueIdToSet = 2;
			break;
		case 16 :
			venueIdToSet = 2;
			break;
		case 17 :
			venueIdToSet = 7;
			break;
		case 18 :
			venueIdToSet = 7;
			break;
		case 19 :
			venueIdToSet = 5;
			break;
		case 20 :
			venueIdToSet = 1;
			break;
		case 21 :
			venueIdToSet = 6;
			break;
		case 22 :
			venueIdToSet = 5;
			break;
		case 23 :
			venueIdToSet = 6;
			break;
		default:
			venueIdToSet = 4;
			break;
		}
		                       
		tv_id.setText(Integer.toString(venueIdToSet));
		tv_name.setText(event.getVenue());
		tv_event.setText(event.getName());

		return v;
	}
}
