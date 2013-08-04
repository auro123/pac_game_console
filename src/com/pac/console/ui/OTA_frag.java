package com.pac.console.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pac.console.R;
import com.pac.console.util.RemoteTools;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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

public class OTA_frag extends Fragment {

	private OTA_enabler mOTAEnabler;
	TextView update;

	String DlUrl = "";
	String DlMd5 = "";
	String FileName = "";
	String DlVersion = "";
	AlertDialog.Builder alert;

	// RemoteTools.downloadFile(OTA_frag.this.getActivity(), DlUrl, FileName);

	Handler updateRemote = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			update.setText(msg.getData().getString("version") + "\n"
					+ msg.getData().getString("file"));
			DlUrl = msg.getData().getString("url");
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
	}

	@Override
	public void onPause() {
		super.onPause();
		mOTAEnabler.pause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle ofjoy) {

		View layout = inflater.inflate(R.layout.ota_frag_layout, null);
		TextView device = (TextView) layout.findViewById(R.id.ota_device);
		update = (TextView) layout.findViewById(R.id.ota_update);
		Button download = (Button) layout.findViewById(R.id.down_button);
		Button flash = (Button) layout.findViewById(R.id.flash_button);

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

				}
			}

		});

		device.setText(getProp("ro.cm.device") + " - " + Build.DEVICE + "\n" + getProp("ro.pacrom.version"));

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
			dev[0] = (String) getProp("ro.cm.device");
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
						data.putString("version", "No Info found!");
					} else if (results[1].contains("NO_PARAMS")) {
						data.putString("version", "Server Is Borked");
					}
					data.putString("file", "Or Tyler Broke Something!");
				}
			} else {
				data.putString("version", "Server Is Borked");
				data.putString("file", "Or Tyler Broke Something!");
			}

			msg.setData(data);
			updateRemote.sendMessage(msg);
		}

	}

	private String getProp(String propKey) {
		Process p = null;
		String propVal = "";
		try {
			p = new ProcessBuilder("/system/bin/getprop", propKey)
					.redirectErrorStream(true).start();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				propVal = line;
			}
			p.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propVal;
	}
}
