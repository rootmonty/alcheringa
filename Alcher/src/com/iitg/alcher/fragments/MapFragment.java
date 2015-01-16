package com.iitg.alcher.fragments;

import static com.iitg.alcher.utils.CommonUtilities.AlcheringaDay0_DayOfYear;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.iitg.alcher.R;
import com.iitg.alcher.adapter.MapCustomAdapter;
import com.iitg.alcher.database.DatabaseHandler;
import com.iitg.alcher.model.EventObj;
import com.iitg.alcher.photoview.PhotoViewAttacher;

@SuppressLint("NewApi")
public class MapFragment extends Fragment {
	ListView recordlist;
	ArrayList<EventObj> details;
	ImageView mImageView;
	PhotoViewAttacher mAttacher;

	public MapFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_map, container,
				false);

		mImageView = (ImageView) rootView.findViewById(R.id.my_image);
		Drawable bitmap;
		try {
			bitmap = getAssetImage(getActivity(), "iitgmap");
			mImageView.setImageDrawable(bitmap);
			mAttacher = new PhotoViewAttacher(mImageView);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Calendar now = Calendar.getInstance();
		int dayYear = now.get(Calendar.DAY_OF_YEAR);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		String currentHour = Integer.toString(hour);
		String currentMinute = Integer.toString(minute);
		if (Integer.parseInt(currentHour) < 10) {
			currentHour = "0" + currentHour;
		}
		if (Integer.parseInt(currentMinute) < 10) {
			currentMinute = "0" + currentMinute;
		}
		String timeNow = currentHour + ":" + currentMinute;

		String timeVar;
		switch (dayYear) {
		case AlcheringaDay0_DayOfYear + 0:
			timeVar = "C_time0";
			break;

		case AlcheringaDay0_DayOfYear + 1:
			timeVar = "C_time1";
			break;

		case AlcheringaDay0_DayOfYear + 2:
			timeVar = "C_time2";
			break;

		case AlcheringaDay0_DayOfYear + 3:
			timeVar = "C_time3";
			break;

		default:
			timeVar = "NULL";
			break;

		}

		recordlist = (ListView) rootView.findViewById(R.id.list_view_map);
		details = new ArrayList<EventObj>();

		if (!timeVar.equalsIgnoreCase("NULL")) {
			DatabaseHandler db = new DatabaseHandler(getActivity());
			String query = "SELECT  * FROM TableEvents WHERE " + timeVar
					+ " != 'NULL' AND " + "SUBSTR(" + timeVar + ",1,5) <= '"
					+ timeNow + "' AND SUBSTR(" + timeVar + ",7,11) >= '"
					+ timeNow + "';";
			details = db.getresultforquery(query);
			db.close();
		}

		recordlist.setAdapter(new MapCustomAdapter(details, getActivity()));

		return rootView;
	}

	public static Drawable getAssetImage(Context context, String filename)
			throws IOException {
		AssetManager assets = context.getResources().getAssets();
		InputStream buffer = new BufferedInputStream((assets.open("drawable/"
				+ filename + ".jpg")));
		Bitmap bitmap = BitmapFactory.decodeStream(buffer);
		return new BitmapDrawable(context.getResources(), bitmap);
	}
}