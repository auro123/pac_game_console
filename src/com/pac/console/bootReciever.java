package com.pac.console;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class bootReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		Intent i = new Intent("com.pac.console.updateChecker");
        i.setClass(context, updateChecker.class);
        context.startService(i);
	}

}
