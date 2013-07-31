package com.pac.console.ui;

import com.pac.console.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Help_frag extends Fragment {
	
	
	public static Help_frag newInstance(String content) {
		Help_frag fragment = new Help_frag();		
		return fragment;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle oflove) {
		
		View layout = inflater.inflate(R.layout.help_frag_layout, null);

		
		
		return layout;
	}

}
