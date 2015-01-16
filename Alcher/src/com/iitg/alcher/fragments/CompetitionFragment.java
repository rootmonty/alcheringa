package com.iitg.alcher.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.iitg.alcher.R;
import com.iitg.alcher.adapter.EventCustomAdapter;
import com.iitg.alcher.database.DatabaseHandler;
import com.iitg.alcher.dialog.EventDetailDialog;
import com.iitg.alcher.model.EventObj;

public class CompetitionFragment extends Fragment{

	ListView recordlist;
	ArrayList<EventObj> details;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_events, container, false);

		String EventType =getArguments().getString("TYPE");
		String query = "SELECT  * FROM TableEvents WHERE C_type='" + EventType + "';";
		
		recordlist = (ListView) rootView.findViewById(R.id.list_data);
		details = new ArrayList<EventObj>();

		DatabaseHandler db = new DatabaseHandler(getActivity());
		details = db.getresultforquery(query);
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