package com.iitg.alcher.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitg.alcher.R;
import com.iitg.alcher.model.EventObj;

public class EventCustomAdapter extends BaseAdapter {

	private ArrayList<EventObj> _data;
	Context _c;

	public EventCustomAdapter (ArrayList<EventObj> data, Context c){
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
			v = vi.inflate(R.layout.list_row_event, parent, false);
		}



		TextView tv_title = (TextView) v.findViewById(R.id.title);
		TextView tv_day1time = (TextView) v.findViewById(R.id.day1time);
		TextView tv_day2time = (TextView) v.findViewById(R.id.day2time); 
		TextView tv_day3time = (TextView) v.findViewById(R.id.day3time); 
		TextView tv_desc = (TextView) v.findViewById(R.id.desc);
		ImageView iv_venue = (ImageView) v.findViewById(R.id.iv_Venue);

		EventObj event = _data.get(position);
		int imageToSet;
		switch(Integer.parseInt(event.getVenueId()))
		{
		case 1 :
			imageToSet = R.drawable.auditorium;
			break;
		case 2 :
			imageToSet = R.drawable.nac;
			break;
		case 3 :
			imageToSet = R.drawable.controldesk;
			break;
		case 4 :
			imageToSet = R.drawable.sac;
			break;
		case 5 :
			imageToSet = R.drawable.cricketground;
			break;
		case 6 :
			imageToSet = R.drawable.informalstage;
			break;
		case 7 :
			imageToSet = R.drawable.miniauditorium;
		case 8: 
			imageToSet = R.drawable.auditoriumpark;
		case 9: 
			imageToSet = R.drawable.crossfadearena;
		case 10: 
			imageToSet = R.drawable.conferancehall;
		case 11: 
			imageToSet = R.drawable.rockophonix;
		case 12: 
			imageToSet = R.drawable.gamingarena;
			break;
		case 13 :
			imageToSet = R.drawable.lecturehall1;
			break;
		case 14 :
			imageToSet = R.drawable.lecturehall2;
			break;
		case 15 :
			imageToSet = R.drawable.lecturehall3;
			break;
		case 16 :
			imageToSet = R.drawable.lecturehall4;
			break;
		default:
			imageToSet = R.drawable.ic_pages;
			break;
		}

		tv_title.setText(event.getName());
		tv_day1time.setText(event.getTimeDay1());
		tv_day2time.setText(event.getTimeDay2());
		tv_day3time.setText(event.getTimeDay3());
		tv_desc.setText(event.getDescription());
		iv_venue.setImageResource(imageToSet);


		return v;
	}
}
