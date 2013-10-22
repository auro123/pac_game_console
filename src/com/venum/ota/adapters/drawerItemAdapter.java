package com.venum.ota.adapters;

import java.util.ArrayList;

import com.pac.console.R;
import com.pac.console.R.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class drawerItemAdapter extends ArrayAdapter<drawerItemType>{
	
	private ArrayList<drawerItemType> mList;
	private Context mContext;
	
	public drawerItemAdapter(Context context, int textViewResourceId,
			ArrayList<drawerItemType> drawList) {
		super(context, textViewResourceId, drawList);
		// TODO Auto-generated constructor stub
		
		//get the list for the adapter
		this.mList = drawList;
		
		//get context for launch actions later
		this.mContext = context;
		
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		// TODO logic for view
		
		//no cahced view then make one
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.drawer_list_item, null);
        }
        
        drawerItemType mItem = mList.get(position);
        
		/** 
		 * Only show the required parts of the view as long as there is a item to show
		 * default is just a title
		 **/

		if (mItem != null){
			// set the title
			TextView tvTit = (TextView) view.findViewById(id.dli_title);
			tvTit.setText(mItem.title);
			// show the caption
			if (mItem.caption_display){
				TextView tvCap = (TextView) view.findViewById(id.dli_caption);
				tvCap.setText(mItem.caption);
			} else {
				TextView tvCap = (TextView) view.findViewById(id.dli_caption);
				tvCap.setVisibility(View.GONE);
			}
			// show the Image
			if (false){
				ImageView tvCap = (ImageView) view.findViewById(id.dli_caption);
				tvCap.setBackgroundResource(R.drawable.pacman_header);
			} else {
				//TextView tvCap = (TextView) view.findViewById(id.dli_caption);
				//tvCap.setVisibility(View.GONE);
			}
			//show the toggler
			if (mItem.toggle_display){
				Switch swTog = (Switch) view.findViewById(id.dli_toggle);
				// TODO COMMENT OUT WHILE TESTING
				//swTog.setOnCheckedChangeListener(mItem.ontoggle);
			} else {
				Switch swTog = (Switch) view.findViewById(id.dli_toggle);
				swTog.setVisibility(View.GONE);
			}
			
			// are we doing a external launch?
			if (mItem.package_launch){
				// TODO do a package intent
			} else {
				// TODO do notihng?
			}
			
			// are we doing a web launch?
			if (mItem.URL_launch){
				// TODO do a url intent
			} else {
				// TODO do notihng?
			}
		}
		
		return view;
	}
}
