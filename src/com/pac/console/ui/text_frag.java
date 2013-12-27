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
package com.pac.console.ui;

import com.pac.console.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Error ERRRRRROOOOORRRRRR Frag
 * 
 * Shows some text
 * 
 * @author pvyParts
 *
 */
public class text_frag extends Fragment {
	
	public static String mText = "";
	boolean store = false;
	
	public static text_frag newInstance(String text) {
		text_frag fragment = new text_frag();
		mText = text;
		return fragment;
	
	}
	    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle ofLove) {
		
		View layout = inflater.inflate(R.layout.contrib_frag_layout, null);
		((TextView) layout.findViewById(R.id.tv_ota_rom_header)).setText(mText);
		return layout;
	}

}
