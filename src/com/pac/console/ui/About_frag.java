package com.pac.console.ui;

import com.pac.console.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class About_frag extends Fragment {
	
	
	public static About_frag newInstance(String content) {
		About_frag fragment = new About_frag();		
		return fragment;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle ofjoy) {
		
		View layout = inflater.inflate(R.layout.ota_frag_layout, null);

		
		
		return layout;
	}

}
