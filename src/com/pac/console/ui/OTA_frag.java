package com.pac.console.ui;

import com.pac.console.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

public class OTA_frag extends Fragment {
	
	private OTA_enabler mOTAEnabler;
	
	public static OTA_frag newInstance(String content) {
		OTA_frag fragment = new OTA_frag();		
		return fragment;
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mOTAEnabler.resume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mOTAEnabler.pause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle ofjoy) {
		
		View layout = inflater.inflate(R.layout.ota_frag_layout, null);
		TextView device = (TextView) layout.findViewById(R.id.ota_device);
		TextView update = (TextView) layout.findViewById(R.id.ota_update);

		device.setText(Build.MODEL+ " - " + Build.DEVICE + "\n"+Build.ID);
		
		Activity activity = getActivity();
		ActionBar actionbar = activity.getActionBar();

		Switch actionBarSwitch = new Switch(activity);

		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM);
		actionbar.setCustomView(actionBarSwitch, new ActionBar.LayoutParams(
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL
						| Gravity.RIGHT));

		actionbar.setTitle("Sounds options");

		mOTAEnabler = new OTA_enabler(getActivity(), actionBarSwitch);

		
		// TODO check online for latest zippy
		
		// Compare zippy to current build
		
		// display options
		
		// TODO add nightly option.
		
		// ADD service to catch updates ( only polling 6 hours ATM )
		
		// TODO Long term push updates direct from dibs
		
		return layout;
	}

}
