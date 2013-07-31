package com.pac.console.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
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
		SharedPreferences prefs;
		Editor editor;

		prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		editor = prefs.edit();

		editor.putBoolean("OTA_ENABLE", isChecked);
		
		editor.commit();
		// TODO fire up service!
	}

	public boolean isSwitchOn() {
		SharedPreferences prefs;
		prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

		return prefs.getBoolean("OTA_ENABLE", true);
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