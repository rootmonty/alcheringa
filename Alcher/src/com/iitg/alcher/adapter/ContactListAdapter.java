package com.iitg.alcher.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iitg.alcher.R;

public class ContactListAdapter extends BaseAdapter {

	private ArrayList<String> listData;

	private LayoutInflater layoutInflater;

	public ContactListAdapter(Context context, ArrayList<String> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView unitView;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if (arg1 == null) {
			arg1 = layoutInflater.inflate(R.layout.list_row_contact, arg2, false);
			holder = new ViewHolder();
			holder.unitView = (TextView) arg1.findViewById(R.id.unit);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}

		holder.unitView.setText(listData.get(arg0));

		return arg1;

	}

}