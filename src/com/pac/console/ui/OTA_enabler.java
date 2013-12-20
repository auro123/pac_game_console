package com.pac.console.ui;

import com.pac.console.R;
import com.pac.console.updateChecker;
import com.pac.console.util.LocalTools;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;
/**
 * switch for the actionbar with interface 
 * 
 * TODO make a proper interface
 * 
 * @author pvyParts
 *
 */
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
		String pac = LocalTools.getProp("ro.pacrom.version");
		// TODO fire up service! maybe?
		if (!pac.equals("")){
			if (isChecked && !isMyServiceRunning()){
				Intent i = new Intent("com.pac.console.updateChecker");
				Log.d("PACCON", "STARTING OTA SERVER");
				i.setClass(mContext, updateChecker.class);
				mContext.startService(i);
			} else if (!isChecked && isMyServiceRunning()){
				Log.d("PACCON", "ENDING OTA SERVER");
	
				mContext.stopService(new Intent(mContext, com.pac.console.updateChecker.class));
			}
		} else {
			Toast.makeText(mContext, mContext.getString(R.string.ota_non_pac), Toast.LENGTH_LONG).show();
		}
	}
	
    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.pac.console.updateChecker".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
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