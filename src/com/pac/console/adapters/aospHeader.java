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

import com.pac.console.R;
import com.pac.console.adapters.changeItemAdapter.RowType;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * AOSP'esc list catagory header type
 *
 * @author pvyParts
 *
 */
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
