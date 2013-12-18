package com.pac.console.adapters;

import java.util.ArrayList;

import com.pac.console.R;
import com.pac.console.R.id;
import com.pac.console.adapters.changeItemAdapter.RowType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class drawerItemAdapter extends ArrayAdapter<ListArrayItem>{
	
	private LayoutInflater mInflater;

	public drawerItemAdapter(Context context, int textViewResourceId,
			ArrayList<ListArrayItem> drawList) {
		super(context, textViewResourceId, drawList);

		this.mInflater = LayoutInflater.from(context);
		
	}
	
    public enum RowType {
        LIST_ITEM, HEADER_ITEM
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
