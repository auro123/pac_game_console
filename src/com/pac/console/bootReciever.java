/*
 *    PAC ROM Console. Settings and OTA
 *    Copyright (C) 2014  pvyParts (Aaron Kable)
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pac.console;

import com.pac.console.util.LocalTools;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
/**
 * Boot reciever for the OTA recivers and shitz
 *
 * @author pvyParts
 *
 */
public class bootReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Boolean onoff = false;
        //Log.d("PACCON", "PROCESSING OTA SERVER");
        String pac = LocalTools.getProp("ro.pacrom.version");
        // TODO fire up service! maybe?
        if (!pac.equals("")){
            try {
                onoff = (1 == Settings.System.getInt(context.getContentResolver(), "OTA_ENABLE"))?true:false;
            } catch (SettingNotFoundException e) {
                onoff = false;
            }
            if (onoff && !isMyServiceRunning(context)){
                Intent i = new Intent("com.pac.console.updateChecker");
                Log.d("PACCON", "STARTING OTA SERVER");
                i.setClass(context, updateChecker.class);
                context.startService(i);
            } else {
                Log.d("PACCON", "NOT STARTING OTA SERVER"); 
            }
        }
    }

    private boolean isMyServiceRunning(Context cont) {
        ActivityManager manager = (ActivityManager) cont.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.pac.console.updateChecker".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
