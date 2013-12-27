/*
 *    PAC ROM Console. Settings and OTA
 *    Copyright (C) 2014  pvyParts (Aaron Kable)
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pac.console.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pac.console.R;
import com.pac.console.adapters.changeItemAdapter.RowType;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * Custom Changelog List header Type
 *
 * @author pvyParts
 *
 */

public class changeHeader implements ListArrayItem {

	private String title;

    public changeHeader(String title) {
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
            view = (View) inflater.inflate(R.layout.header_list_item, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.hli_date);
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String titleText = title;
        try {
			Date result =  df.parse(title);
			((SimpleDateFormat) df).applyPattern("d MMMM yyyy");
			titleText = df.format(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

        text.setText(titleText);
        
        RelativeLayout rlHolder = (RelativeLayout) view.findViewById(R.id.hli_back);
		rlHolder.setBackgroundColor(Color.parseColor("#33B5E5"));
		
        return view;
	}

}
