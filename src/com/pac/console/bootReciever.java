package com.pac.console;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

public class bootReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		Boolean onoff = false;
		try {
			onoff = (1 == Settings.System.getInt(context.getContentResolver(), "OTA_ENABLE"))?true:false;
		} catch (SettingNotFoundException e) {
			onoff = false;
		}
		if (onoff){
			Intent i = new Intent("com.pac.console.updateChecker");
			i.setClass(context, updateChecker.class);
			context.startService(i);
		}
	}

}
