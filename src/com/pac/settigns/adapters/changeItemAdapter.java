package com.pac.settigns.adapters;

import java.util.ArrayList;

import com.pac.console.R;
import com.pac.console.R.id;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class changeItemAdapter extends ArrayAdapter<Item>{
	
	private ArrayList<Item> mList;
	private Context mContext;
    private LayoutInflater mInflater;

    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

	public changeItemAdapter(Context context, int textViewResourceId,
			ArrayList<Item> drawList) {
		super(context, textViewResourceId, drawList);
		
        mInflater = LayoutInflater.from(context);

		//get the list for the adapter
		this.mList = drawList;
		
		//get context for launch actions later
		this.mContext = context;
		
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
