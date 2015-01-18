package com.iitg.alcheringa.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.iitg.alcheringa.R;

@SuppressLint("NewApi")
public class SponsorFragment extends Fragment {
	public SponsorFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_sponsor, container, false);

		ViewFlipper flipper = (ViewFlipper) rootView.findViewById(R.id.flipper1);
		flipper.startFlipping();
		return rootView;
	}
}