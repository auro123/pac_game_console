package com.pac.console.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class OTA_enabler implements OnCheckedChangeListener {

	protected final Context mContext;
	private Switch mSwitch;

	public OTA_enabler(Context context, Switch swtch) {
		mContext = context;
		setSwitch(swtch);
	}

	public void setSwitch(Switch swtch) {
		if (mSwitch == swtch)
			return;

		if (mSwitch != null)
			mSwitch.setOnCheckedChangeListener(null);
		mSwitch = swtch;
		mSwitch.setOnCheckedChangeListener(this);

		mSwitch.setChecked(isSwitchOn());
	}

	public void onCheckedChanged(CompoundButton view, boolean isChecked) {

		Settings.System.putInt(mContext.getContentResolver(), "OTA_ENABLE", (isChecked) ? 1 : 0);
		
		// TODO fire up service! maybe?
	}

	public boolean isSwitchOn() {
		Boolean onoff = false;
		try {
			onoff = (1 == Settings.System.getInt(mContext.getContentResolver(), "OTA_ENABLE"))?true:false;
		} catch (SettingNotFoundException e) {
			onoff = false;
		}
		return onoff;
	}

	public void resume() {
		mSwitch.setOnCheckedChangeListener(this);
		mSwitch.setChecked(isSwitchOn());
		mSwitch.setVisibility(View.VISIBLE);
		
	}

	public void pause() {
		mSwitch.setOnCheckedChangeListener(null);
		mSwitch.setVisibility(View.GONE);

	}
}