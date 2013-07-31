package com.pac.console.ui;

import com.pac.console.R;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OTA_frag extends Fragment {
	
	
	public static OTA_frag newInstance(String content) {
		OTA_frag fragment = new OTA_frag();		
		return fragment;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle ofjoy) {
		
		View layout = inflater.inflate(R.layout.ota_frag_layout, null);
		TextView device = (TextView) layout.findViewById(R.id.ota_device);
		TextView update = (TextView) layout.findViewById(R.id.ota_update);

		device.setText(Build.MODEL+ " - " + Build.DEVICE + "\n"+Build.ID);
		
		return layout;
	}

}
