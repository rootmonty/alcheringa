package com.iitg.alcher.fragments;

import static com.iitg.alcher.utils.CommonUtilities.AlcheringaDay0_DayOfYear;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.iitg.alcher.R;
import com.iitg.alcher.adapter.EventCustomAdapter;
import com.iitg.alcher.database.DatabaseHandler;
import com.iitg.alcher.dialog.EventDetailDialog;
import com.iitg.alcher.model.EventObj;

@SuppressLint("NewApi")
public class ScheduleFragment extends Fragment {
	ListView recordlist;
	ArrayList<EventObj> details;

	public ScheduleFragment() {
	}

	public String EventDay;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_schedule, container,
				false);
		recordlist = (ListView) rootView.findViewById(R.id.list_data);
		details = new ArrayList<EventObj>();
		final ImageButton iv_day0 = (ImageButton) rootView
				.findViewById(R.id.imageButton1);
		final ImageButton iv_day1 = (ImageButton) rootView
				.findViewById(R.id.imageButton2);
		final ImageButton iv_day2 = (ImageButton) rootView
				.findViewById(R.id.imageButton3);
		final ImageButton iv_day3 = (ImageButton) rootView
				.findViewById(R.id.imageButton4);

		Calendar now = Calendar.getInstance();
		int dayYear = now.get(Calendar.DAY_OF_YEAR);
		switch (dayYear) {
		case AlcheringaDay0_DayOfYear + 0:
			LoadListView("0");
			iv_day0.setBackgroundColor(Color.parseColor("#000000"));
			iv_day1.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day2.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day3.setBackgroundColor(Color.parseColor("#ffffff"));
			break;

		case AlcheringaDay0_DayOfYear + 1:
			LoadListView("1");
			iv_day0.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day1.setBackgroundColor(Color.parseColor("#000000"));
			iv_day2.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day3.setBackgroundColor(Color.parseColor("#ffffff"));
			break;

		case AlcheringaDay0_DayOfYear + 2:
			LoadListView("2");
			iv_day0.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day1.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day2.setBackgroundColor(Color.parseColor("#000000"));
			iv_day3.setBackgroundColor(Color.parseColor("#ffffff"));
			break;

		case AlcheringaDay0_DayOfYear + 3:
			LoadListView("3");
			iv_day0.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day1.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day2.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day3.setBackgroundColor(Color.parseColor("#000000"));
			break;

		default:
			LoadListView("1");
			iv_day0.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day1.setBackgroundColor(Color.parseColor("#000000"));
			iv_day2.setBackgroundColor(Color.parseColor("#ffffff"));
			iv_day3.setBackgroundColor(Color.parseColor("#ffffff"));
			break;

		}

		iv_day0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoadListView("0");
				iv_day0.setBackgroundColor(Color.parseColor("#000000"));
				iv_day1.setBackgroundColor(Color.parseColor("#ffffff"));
				iv_day2.setBackgroundColor(Color.parseColor("#ffffff"));
				iv_day3.setBackgroundColor(Color.parseColor("#ffffff"));
			}
		});

		iv_day1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoadListView("1");
				iv_day0.setBackgroundColor(Color.parseColor("#ffffff"));
				iv_day1.setBackgroundColor(Color.parseColor("#000000"));
				iv_day2.setBackgroundColor(Color.parseColor("#ffffff"));
				iv_day3.setBackgroundColor(Color.parseColor("#ffffff"));
			}
		});

		iv_day2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoadListView("2");
				iv_day0.setBackgroundColor(Color.parseColor("#ffffff"));
				iv_day1.setBackgroundColor(Color.parseColor("#ffffff"));
				iv_day2.setBackgroundColor(Color.parseColor("#000000"));
				iv_day3.setBackgroundColor(Color.parseColor("#ffffff"));
			}
		});

		iv_day3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoadListView("3");
				iv_day0.setBackgroundColor(Color.parseColor("#ffffff"));
				iv_day1.setBackgroundColor(Color.parseColor("#ffffff"));
				iv_day2.setBackgroundColor(Color.parseColor("#ffffff"));
				iv_day3.setBackgroundColor(Color.parseColor("#000000"));
			}
		});

		recordlist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				TextView text = (TextView) view.findViewById(R.id.title);
				String strText = text.getText().toString();
				EventDetailDialog Custom_Dialog = new EventDetailDialog(
						getActivity(), strText);
				Custom_Dialog.show();

			}
		});

		return rootView;
	}

	void LoadListView(String EventDay) {
		String query = "SELECT  * FROM TableEvents WHERE `C_time" + EventDay
				+ "` != 'NULL';";
		DatabaseHandler db = new DatabaseHandler(getActivity());
		details = db.getresultforquery(query);
		LayoutAnimationController controller = AnimationUtils
				.loadLayoutAnimation(getActivity(),
						R.anim.list_layout_controller);
		recordlist.setLayoutAnimation(controller);
		recordlist.setAdapter(new EventCustomAdapter(details, getActivity()));
	}
}
