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
