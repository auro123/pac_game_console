package com.pac.console.ui;

import com.pac.console.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Contrib_frag extends Fragment {
	
	
	public static Contrib_frag newInstance(String content) {
		Contrib_frag fragment = new Contrib_frag();		
		return fragment;
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle oflove) {
		
		View layout = inflater.inflate(R.layout.contrib_frag_layout, null);

		
		
		return layout;
	}

}
