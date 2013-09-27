package com.pac.console.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pac.console.R;
import com.pac.console.util.LocalTools;
import com.pac.console.util.RemoteTools;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class OTA_frag extends Fragment {

	private OTA_enabler mOTAEnabler;
	TextView update;
	Button download;
	Button flash;
	
	String DlUrl = "";
	String DlMd5 = "";
	String FileName = "";
	String DlVersion = "";
	AlertDialog.Builder alert;
	
	DownLoadComplte mDownload;
	
	// RemoteTools.downloadFile(OTA_frag.this.getActivity(), DlUrl, FileName);

	Handler updateRemote = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			update.setText(msg.getData().getString("version") + "\n"
					+ msg.getData().getString("file"));
			DlUrl = msg.getData().getString("url");
			boolean fileExist = new File(Environment.DIRECTORY_DOWNLOADS+"/PAC/"+msg.getData().getString("file")).exists();
			if (DlUrl != null){
				download.setClickable(!fileExist);
				download.setActivated(!fileExist);
				download.setTextColor(Color.WHITE);

			}
			DlMd5 = msg.getData().getString("md5");
			DlVersion = msg.getData().getString("version");
			FileName = msg.getData().getString("file");
		}

	};

	public static OTA_frag newInstance(String content) {
		OTA_frag fragment = new OTA_frag();
		return fragment;
	}
    
	@Override
	public void onResume() {
		super.onResume();
		mOTAEnabler.resume();
        mDownload = new DownLoadComplte();
        this.getActivity().registerReceiver(mDownload, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));

	}

	@Override
	public void onPause() {
		super.onPause();
		mOTAEnabler.pause();
        this.getActivity().unregisterReceiver(mDownload);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle ofjoy) {

		View layout = inflater.inflate(R.layout.ota_frag_layout, null);
		TextView device = (TextView) layout.findViewById(R.id.ota_device);
		update = (TextView) layout.findViewById(R.id.ota_update);
		download = (Button) layout.findViewById(R.id.down_button);
		flash = (Button) layout.findViewById(R.id.flash_button);

		download.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (DlUrl != null && FileName != null) {
					int con = RemoteTools.checkConnection(OTA_frag.this
							.getActivity());
					if (con == RemoteTools.MOBILE) {
						alert = new AlertDialog.Builder(getActivity());
						alert.setTitle(OTA_frag.this.getActivity().getString(
								R.string.mobile_data));
						alert.setMessage(OTA_frag.this.getActivity().getString(
								R.string.wifi_rec)
								+ "\n"
								+ OTA_frag.this.getActivity().getString(
										R.string.carges));
						alert.setPositiveButton("Yes!",
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										RemoteTools.downloadFile(
												OTA_frag.this.getActivity(),
												DlUrl, FileName);
									}
								});
						alert.setNegativeButton("No!",
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
						alert.show();
					} else if (con == RemoteTools.DISCONNECTED) {
						// POPUP no connection
					} else if (con == RemoteTools.WIFI) {
						RemoteTools.downloadFile(OTA_frag.this.getActivity(),
								DlUrl, FileName);
						
					}
				}
			}

		});
		flash.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (DlUrl != null && FileName != null) {
					// TODO check for file and dl if needed

					// TODO Flash this bad boy in CWM / what ever it is
					
					// TODO add my CWM library

				}
			}

		});
		
		// TODO enable this guy
		flash.setClickable(false);
		flash.setActivated(false);
		flash.setTextColor(Color.GRAY);
		
		// TODO enable this guy
		download.setClickable(false);
		download.setActivated(false);
		download.setTextColor(Color.GRAY);

		String deviceName = LocalTools.getProp("ro.cm.device").equals("")? Build.PRODUCT : LocalTools.getProp("ro.cm.device");
		device.setText(deviceName + " - " + Build.DEVICE + "\n" + (LocalTools.getProp("ro.pacrom.version").equals("")?getString(R.string.ota_non_pac):LocalTools.getProp("ro.pacrom.version")));

		Activity activity = getActivity();
		ActionBar actionbar = activity.getActionBar();

		Switch actionBarSwitch = new Switch(activity);

		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM);
		actionbar.setCustomView(actionBarSwitch, new ActionBar.LayoutParams(
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL
						| Gravity.RIGHT));

		mOTAEnabler = new OTA_enabler(getActivity(), actionBarSwitch);

		// TODO check online for latest zippity zip

		// Compare zippy to current build

		// display options

		// TODO add nightly option.

		// ADD service to catch updates ( only polling 6 hours ATM )

		// TODO Long term push updates direct from dibs
		int con = RemoteTools.checkConnection(OTA_frag.this.getActivity());
		if (con > RemoteTools.DISCONNECTED) {
			AsyncTask checkTast = new CheckRemote();
			String[] dev = { " " };
			dev[0] = (String) deviceName;
			checkTast.execute(dev);
		} else {
			update.setText(this.getActivity().getString(R.string.no_data));
		}

		return layout;
	}

	private class CheckRemote extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String out = RemoteTools.checkRom(arg0[0]);
			return out;
		}

		@Override
		protected void onPostExecute(final String result) {

			Message msg = new Message();
			Bundle data = new Bundle();

			if (result != null) {
				Log.d("REMOTE", "got this: " + result);
				String[] results = result.split(",");
				if (!results[0].contains("#BLAMETYLER")) {
					data.putString("version", results[2]);
					String[] dlurl = results[0].split("/");
					data.putString("file", dlurl[dlurl.length - 1]);
					data.putString("url", results[0]);
					data.putString("md5", results[3]);
				} else {
					// error device not on server records!
					if (results[1].contains("NO_CONFIG_FOUND")) {
						data.putString("version", getString(R.string.error_dev));
					} else if (results[1].contains("NO_PARAMS")) {
						data.putString("version", getString(R.string.error_serv));
					}
					data.putString("file", getString(R.string.error));
				}
			} else {
				data.putString("version", getString(R.string.error_serv));
				data.putString("file", getString(R.string.error));
			}

			msg.setData(data);
			updateRemote.sendMessage(msg);
		}

	}
	
	private class DownLoadComplte extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(context, "Download Complte", Toast.LENGTH_LONG)
                        .show();
            }
        }
}

}
