package com.pac.console.adapters;

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

public class changeItemAdapter extends ArrayAdapter<changeItemType>{
	
	private ArrayList<changeItemType> mList;
	private Context mContext;
	
	public changeItemAdapter(Context context, int textViewResourceId,
			ArrayList<changeItemType> drawList) {
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
            view = vi.inflate(R.layout.changes_list_item, null);
        }
        
        changeItemType mItem = mList.get(position);
        
		/** 
		 * Only show the required parts of the view as long as there is a item to show
		 * default is just a title
		 **/
//     http://github.com/" .$commit['GitUsername']. "/" . $commit['Repository'] . "/commit/" . $commit['SHA'] 
		if (mItem != null){
			if (mItem.getIsHeader()){
				LinearLayout llHolder = (LinearLayout) view.findViewById(id.cli_data_holder);
				llHolder.setVisibility(View.GONE);

				LinearLayout lldHolder = (LinearLayout) view.findViewById(id.cli_date_holder);
				lldHolder.setVisibility(View.VISIBLE);
				RelativeLayout rlHolder = (RelativeLayout) view.findViewById(id.cli_back);
				rlHolder.setBackgroundColor(Color.parseColor("#33B5E5"));
				TextView tvDate = (TextView) view.findViewById(id.cli_date);
				tvDate.setText("Changes From\n"+mItem.getDate());
			} else {
				LinearLayout llHolder = (LinearLayout) view.findViewById(id.cli_data_holder);
				llHolder.setVisibility(View.VISIBLE);
				LinearLayout lldHolder = (LinearLayout) view.findViewById(id.cli_date_holder);
				lldHolder.setVisibility(View.GONE);
				RelativeLayout rlHolder = (RelativeLayout) view.findViewById(id.cli_back);
				rlHolder.setBackgroundColor(Color.TRANSPARENT);

				// set the title
				TextView tvTit = (TextView) view.findViewById(id.cli_title);
				tvTit.setText(mItem.getCaption());
				// show the caption
				TextView tvCap = (TextView) view.findViewById(id.cli_caption);
				tvCap.setText(mItem.getTittle()+" - "+mItem.getAuthor());
			}
		}
		
		return view;
	}
}
