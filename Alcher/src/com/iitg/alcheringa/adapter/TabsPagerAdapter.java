package com.iitg.alcheringa.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.iitg.alcheringa.fragments.CompetitionFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		Bundle bundle=new Bundle();
		CompetitionFragment frag=new CompetitionFragment();
		switch (index) {
		case 0:
			bundle.putString("TYPE", "Dance");
			frag.setArguments(bundle);
			return frag;
		case 1:
			bundle.putString("TYPE", "Music");
			frag.setArguments(bundle);
			return frag;
		case 2:
			bundle.putString("TYPE", "PerfArts");
			frag.setArguments(bundle);
			return frag;
		case 3:
			bundle.putString("TYPE", "FineArts");
			frag.setArguments(bundle);
			return frag;
		case 4:
			bundle.putString("TYPE", "Sports");
			frag.setArguments(bundle);
			return frag;
		case 5:
			bundle.putString("TYPE", "ClsApart");
			frag.setArguments(bundle);
			return frag;
		case 6:
			bundle.putString("TYPE", "Alfaaz");
			frag.setArguments(bundle);
			return frag;
			
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 7;
	}

}