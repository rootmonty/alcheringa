package com.iitg.alcher.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.iitg.alcher.R;
import com.iitg.alcher.adapter.ExpandableListAdapter;

@SuppressLint("NewApi")
public class ContactsFragment extends Fragment {
	
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	
	Map<String, String> map = new HashMap<String, String>();
	
	public ContactsFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
		
		
		expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);


		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				final String phnNumb =  map.get(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).toString());
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				            //Yes button clicked
				        	Intent callIntent = new Intent(Intent.ACTION_CALL);
							callIntent.setData(Uri.parse("tel:" + phnNumb));
							callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							startActivity(callIntent);
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				            //No button clicked
				            break;
				        }
				    }
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Are you sure want to call "+ phnNumb).setPositiveButton("Yes", dialogClickListener)
				    .setNegativeButton("No", dialogClickListener).show();
				
				
				return false;
			}
		});
        
		

		return rootView;
	}
	
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("DANCE");
		listDataHeader.add("MUSIC");
		listDataHeader.add("PERFORMING ARTS");
		listDataHeader.add("FINE ARTS");
		listDataHeader.add("SPORTS");
		listDataHeader.add("CLASS APART");
		listDataHeader.add("DIGITAL ARTS");
		listDataHeader.add("ALFAAZ");
		listDataHeader.add("MYSTERY BOX");
		
		List<String> L_Dance = new ArrayList<String>();
		List<String> L_Music = new ArrayList<String>();
		List<String> L_PerformingArts = new ArrayList<String>();
		List<String> L_FineArts = new ArrayList<String>();
		List<String> L_Sports = new ArrayList<String>();
		List<String> L_ClassApart = new ArrayList<String>();
		List<String> L_DigitalArts = new ArrayList<String>();
		List<String> L_Alfaaz = new ArrayList<String>();
		List<String> L_MysteryBox = new ArrayList<String>();

		addContact(L_Dance, "Mounika Mantri", "+919085846384");
		addContact(L_Dance, "Sairamkoti Amgoth", "+919085846508");
		addContact(L_Music, "Mounika Mantri", "+919085846384");
		addContact(L_Music, "Sairamkoti Amgoth", "+919085846508");
		addContact(L_PerformingArts, "Mounika Mantri", "+919085846384");
		addContact(L_PerformingArts, "Sairamkoti Amgoth", "+919085846508");
		addContact(L_FineArts, "Mounika Mantri", "+919085846384");
		addContact(L_FineArts, "Sairamkoti Amgoth", "+919085846508");
		addContact(L_Sports, "Mounika Mantri", "+919085846384");
		addContact(L_Sports, "Sairamkoti Amgoth", "+919085846508");
		addContact(L_ClassApart, "Mounika Mantri", "+919085846384");
		addContact(L_ClassApart, "Sairamkoti Amgoth", "+919085846508");
		addContact(L_DigitalArts, "Mounika Mantri", "+919085846384");
		addContact(L_DigitalArts, "Sairamkoti Amgoth", "+919085846508");
		addContact(L_Alfaaz, "Mounika Mantri", "+919085846384");
		addContact(L_Alfaaz, "Sairamkoti Amgoth", "+919085846508");
		addContact(L_MysteryBox, "Mounika Mantri", "+919085846384");
		addContact(L_MysteryBox, "Sairamkoti Amgoth", "+919085846508");
		
		listDataChild.put(listDataHeader.get(0), L_Dance); // Header, Child data
		listDataChild.put(listDataHeader.get(1), L_Music);
		listDataChild.put(listDataHeader.get(2), L_PerformingArts);
		listDataChild.put(listDataHeader.get(3), L_FineArts);
		listDataChild.put(listDataHeader.get(4), L_Sports);
		listDataChild.put(listDataHeader.get(5), L_ClassApart);
		listDataChild.put(listDataHeader.get(6), L_DigitalArts);
		listDataChild.put(listDataHeader.get(7), L_Alfaaz);
		listDataChild.put(listDataHeader.get(8), L_MysteryBox);
		
	}

	private void addContact(List<String> eventTypeList, String name, String phoneNumber) {
		eventTypeList.add(name);
		map.put(name,phoneNumber);
	}
}
