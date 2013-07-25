package com.pac.console.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class drawerItemAdapter extends ArrayAdapter<drawerItemType>{

	public drawerItemAdapter(Context context, int textViewResourceId,
			List<drawerItemType> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		
		return parent;
	}
}
