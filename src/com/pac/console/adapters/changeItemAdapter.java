package com.pac.console.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class changeItemAdapter extends ArrayAdapter<ListArrayItem>{
	
	private LayoutInflater mInflater;

    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

	public changeItemAdapter(Context context, int textViewResourceId,
			ArrayList<ListArrayItem> drawList) {
		super(context, textViewResourceId, drawList);
		
        mInflater = LayoutInflater.from(context);
	}

	@Override
    public int getViewTypeCount() {
        return RowType.values().length;

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		// TODO logic for view
		return getItem(position).getView(mInflater, convertView);
	}
}
