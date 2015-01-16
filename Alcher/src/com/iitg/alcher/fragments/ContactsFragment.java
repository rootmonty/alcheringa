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
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

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
		
		map.put("Shreya Raina (Over All)","847003");
		map.put("Mayank Sardana (The Big Picture)","9956127");
		map.put("Krishna Choudhury (The Big Picture)","983495");
		map.put("Sai (The Big Picture)","806056");
		map.put("Mayank Sardana (Stroke Of Genius)","9957127");
		map.put("Krishna Choudhury (Stroke Of Genius)","9863495");
		map.put("Mayank Sardana (Ink The Tee)","9956127");
		map.put("Krishna Choudhury (Ink The Tee)","986495");
		map.put("Sai (Comic Con)","8006056");
		map.put("Vasudha Khandelwal (Over All)","7896317");
		map.put("Mayank Sardana (Rock-O-Phonix)","995727");
		map.put("Krishna Choudhury (Rock-O-Phonix)","986495");
		map.put("Mayank Sardana (Mr. & Ms. Alcheringa)","9956127");
		map.put("Krishna Choudhury (Mr. & Ms. Alcheringa)","9864495");
		map.put("Sai (Mr. & Ms. Alcheringa)","8016056");
		map.put("Mayank Sardana (Haute Couture)","9956127");
		map.put("Krishna Choudhury (Haute Couture)","986495");
		map.put("Sai (Haute Couture)","8016056");
		map.put("Mayank Sardana (Crossfade)","9956127");
		map.put("Krishna Choudhury (Crossfade)","986495");
		map.put("Sai (Crossfade)","806056");
		map.put("Mayank Sardana (Roadiez)","996127");
		map.put("Krishna Choudhury (Roadiez)","9863495");
		map.put("Mayank Sardana (Just A Minute)","9996127");
		map.put("Krishna Choudhury (Muse)","9864395");
		map.put("Sai (What's The Good Word ?)","8011056");
		map.put("Mayank Sardana (Crush)","9957127");
		map.put("Krishna Choudhury (Blind Date)","9863495");
		map.put("Sai (Eatopia)","8006056");
		map.put("Mayank Sardana (Bollywood Bug)","9957127");
		map.put("Krishna Choudhury (Hai Dum)","9864495");
		map.put("Mayank Sardana (Lan Gaming)","9956127");
		map.put("Krishna Choudhury (Gaming Arena)","363495");
		map.put("Sai (Wheel of Fortune)","806056");
		map.put("Abid Haque (Over All)","847077");
		map.put("Mayank Sardana (Electric Heels)","995127");
		map.put("Krishna Choudhury (Electric Heels)","963495");
		map.put("Sai (Electric Heels)","806056");
		map.put("Mayank Sardana (Step Up)","996127");
		map.put("Krishna Choudhury (Step Up)","963495");
		map.put("Sai (Step Up)","801056");
		map.put("Mayank Sardana  (Moves Like Jagger)","995727");
		map.put("Krishna Choudhury (Moves Like Jagger)","986495");
		map.put("Sai (Moves Like Jagger)","801156");
		map.put("Mayank Sardana (Navras)","995727");
		map.put("Krishna Choudhury (Navras)","986435");
		map.put("Sai (Navras)","801056");
		map.put("Meenal Mandil (Over All)","789698");
		map.put("Mayank Sardana (Theatrix)","995727");
		map.put("Krishna Choudhury (Theatrix)","986495");
		map.put("Sai (Theatrix)","801156");
		map.put("Mayank Sardana (Halla Bol)","995757");
		map.put("Krishna Choudhury (Halla Bol)","986495");
		map.put("Sai (Halla Bol)","801156");
		map.put("Mayank Sardana (Why So Serious?)","995727");
		map.put("Krishna Choudhury (Why So Serious?)","986495");
		map.put("Sai (Why So Serious?)","801156");
		map.put("Mayank Sardana (Ed Edd N Eddy)","995127");
		map.put("Krishna Choudhury (Ed Edd N Eddy)","986495");
		map.put("Sai (Ed Edd N Eddy)","801056");
		map.put("Aditya Dhawale (Over All)","9085292566");
		map.put("Mayank Sardana (Raga High)","995727");
		map.put("Krishna Choudhury (Raga High)","986435");
		map.put("Mayank Sardana (Voice of Alcheringa)","995127");
		map.put("Krishna Choudhury (Voice of Alcheringa)","986495");
		map.put("Mayank Sardana (Rhapsody)","995127");
		map.put("Krishna Choudhury (Rhapsody)","986495");
		map.put("Shikhar Saxena (Over All)","847358");
		map.put("Mayank Sardana (5 on 5 Football)","995727");
		map.put("Mayank Sardana (Arm Wrestling)","995127");
		map.put("Mayank Sardana","995727");
	
		
		
		expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
//				Toast.makeText(getActivity().getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Expanded",
//						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
//				Toast.makeText(getActivity().getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Collapsed",
//						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
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

		L_Dance.add("Abid Haque (Over All)");
		L_Dance.add("Mayank Sardana (Electric Heels)");
		L_Dance.add("Krishna Choudhury (Electric Heels)");
		L_Dance.add("Sai (Electric Heels)");
		L_Dance.add("Mayank Sardana (Step Up)");
		L_Dance.add("Krishna Choudhury (Step Up)");
		L_Dance.add("Sai (Step Up)");
		L_Dance.add("Mayank Sardana (Moves Like Jagger)");
		L_Dance.add("Krishna Choudhury (Moves Like Jagger)");
		L_Dance.add("Sai (Moves Like Jagger)");
		L_Dance.add("Mayank Sardana (Navras)");
		L_Dance.add("Krishna Choudhury (Navras)");
		L_Dance.add("Sai (Navras)");
		L_Music.add("Aditya Dhawale (Over All)");
		L_Music.add("Mayank Sardana (Raga High)");
		L_Music.add("Krishna Choudhury (Raga High)");
		L_Music.add("Sai Mohanty (Raga High)");
		L_Music.add("Mayank Sardana (Voice of Alcheringa)");
		L_Music.add("Krishna Choudhury (Voice of Alcheringa)");
		L_Music.add("Sai (Voice of Alcheringa)");
		L_Music.add("Mayank Sardana (Rhapsody)");
		L_Music.add("Krishna Choudhury (Rhapsody)");
		L_PerformingArts.add("Meenal Mandil (Over All)");
		L_PerformingArts.add("Mayank Sardana (Theatrix)");
		L_PerformingArts.add("Krishna Choudhury (Thearix)");
		L_PerformingArts.add("Sai (Theatrix)");
		L_PerformingArts.add("Mayank Sardana (Halla Bol)");
		L_PerformingArts.add("Krishna Choudhury (Halla Bol)");
		L_PerformingArts.add("Sai (Halla Bol)");
		L_PerformingArts.add("Mayank Sardana (Why So Serious?)");
		L_PerformingArts.add("Krishna Choudhury (Why So Serious?)");
		L_PerformingArts.add("Sai (Why So Serious?)");
		L_PerformingArts.add("Mayank Sardana (Ed Edd N Eddy)");
		L_PerformingArts.add("Krishna Choudhury (Ed Edd N Eddy)");
		L_PerformingArts.add("Sai (Ed Edd N Eddy)");
		L_FineArts.add("Shreya Raina (Over All)");
		L_FineArts.add("Mayank Sardana (The Big Picture)");
		L_FineArts.add("Krishna Choudhury (The Big Picture)");
		L_FineArts.add("Sai (The Big Picture)");
		L_FineArts.add("Mayank Sardana (Stroke Of Genius)");
		L_FineArts.add("Krishna Choudhury (Stroke Of Genius)");
		L_FineArts.add("Mayank Sardana (Ink The Tee)");
		L_FineArts.add("Krishna Choudhury (Ink The Tee)");
		L_FineArts.add("Sai (Comic Con)");
		L_Sports.add("Shikhar Saxena (Over All)");
		L_Sports.add("Mayank Sardana (5 on 5 Football)");
		L_Sports.add("Mayank Sardana (Arm Wrestling)");
		L_ClassApart.add("Vasudha Khandelwal (Over All)");
		L_ClassApart.add("Mayank Sardana (Rock-O-Phonix)");
		L_ClassApart.add("Krishna Choudhury (Rock-O-Phonix)");
		L_ClassApart.add("Mayank Sardana (Mr. & Ms. Alcheringa)");
		L_ClassApart.add("Krishna Choudhury (Mr. & Ms. Alcheringa)");
		L_ClassApart.add("Sai (Mr. & Ms. Alcheringa)");
		L_ClassApart.add("Mayank Sardana (Haute Couture)");
		L_ClassApart.add("Krishna Choudhury (Haute Couture)");
		L_ClassApart.add("Sai (Haute Couture)");
		L_ClassApart.add("Mayank Sardana (Crossfade)");
		L_ClassApart.add("Krishna Choudhury (Crossfade)");
		L_ClassApart.add("Sai (Crossfade)");
		L_ClassApart.add("Mayank Sardana (Roadiez)");
		L_ClassApart.add("Krishna Choudhury (Roadiez)");
		L_DigitalArts.add("Mayank Sardana");
		L_Alfaaz.add("Mayank Sardana (Just A Minute)");
		L_Alfaaz.add("Krishna Choudhury (Muse)");
		L_Alfaaz.add("Sai (What's The Good Word ?)");
		L_MysteryBox.add("Mayank Sardana (Crush)");
		L_MysteryBox.add("Krishna Choudhury (Blind Date)");
		L_MysteryBox.add("Sai (Eatopia)");
		L_MysteryBox.add("Mayank Sardana (Bollywood Bug)");
		L_MysteryBox.add("Krishna Choudhury (Hai Dum)");
		L_MysteryBox.add("Mayank Sardana (Lan Gaming)");
		L_MysteryBox.add("Krishna Choudhury (Gaming Arena)");
		L_MysteryBox.add("Sai (Wheel of Fortune)");


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
}
