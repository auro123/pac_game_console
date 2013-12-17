package com.pac.console.adapters;

import com.pac.console.R;
import com.pac.console.adapters.changeItemAdapter.RowType;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class aospHeader implements ListArrayItem {

	private String title;

    public aospHeader(String title) {
        this.title = title;
    }
    
    // getters and setters
    public String getTitle(){
    	return this.title;
    }
    
    public void setTitle(String input){
    	this.title = input;
    }

	@Override
	public int getViewType() {
		return RowType.HEADER_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.aosp_header, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        ((TextView) view).setText(title);
        
        return view;
	}

}
