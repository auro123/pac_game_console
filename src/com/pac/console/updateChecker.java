package com.pac.console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

public class updateChecker extends Service {

	Handler handler = new Handler();

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		// INITIALIZE SCREEN RECEIVER
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		BroadcastReceiver mReceiver = new ScreenReceiver();
		registerReceiver(mReceiver, filter);


		
	}

	class ScreenReceiver extends BroadcastReceiver {
		// THANKS JASON
		public boolean wasScreenOn = true;

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				// DO WHATEVER YOU NEED TO DO HERE
				wasScreenOn = false;
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				// AND DO WHATEVER YOU NEED TO DO HERE
				wasScreenOn = true;
			}
		}
	}

	private Thread checkOTA = new Thread(new Runnable(){

		public void run() {
			// TODO Auto-generated method stub
			// checky McCheck
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
			
			String dateNow = sdf.format(currentDate.getTime());
			Date today = new Date(dateNow);
			Date finalDay = null;
			try {
				finalDay = (Date) sdf.parse(Settings.System.getString(getContentResolver(), "lastUpdate"));
			} catch (ParseException e) {
				try {
					finalDay = (Date) sdf.parse("2010-06-14-12:11");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			int numberOfDays = (int) ((finalDay.getTime() - today.getTime()) / (3600 * 1000));
			
			//TODO 6 hours passed? 
			//check for update on server adn reset time oonce done
			
			//Settings.System.putString(getContentResolver(), "lastUpdate", "hhmmddyymmdd");
			
			//else do nothing
			
		}
		
	});
	
}
