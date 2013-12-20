package com.pac.console.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.pac.console.config;

/**
 * all the remote grabbers / downloaders and what not
 * 
 * @author pvyParts
 *
 */
public class RemoteTools {
	
	/** DownLoadComplte mDownload;


	private class DownLoadComplte extends BroadcastReceiver {

	        @Override
	        public void onReceive(Context context, Intent intent) {
	            if (intent.getAction().equalsIgnoreCase(
	                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
	                Toast.makeText(context, "Download Complte", Toast.LENGTH_LONG)
	                        .show();
	            }
	        }
	}	**/
	    
	public static void downloadFile(Context context, String url, String fileName){
		Uri URL = Uri.parse(url);
		if(!fileName.contains("zip")){
			fileName += ".zip";
		}
		DownloadManager.Request r = new DownloadManager.Request(URL);

		// This put the download in the same Download dir the browser uses
		r.setDestinationInExternalPublicDir("/Download/PAC/", fileName);

		// When downloading music and videos they will be listed in the player
		// (Seems to be available since Honeycomb only)
		r.allowScanningByMediaScanner();

		// Notify user when download is completed

		r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

		//TODO Notification click goes to download manager.
		
		// Start download
		DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		long idDownload = dm.enqueue(r);
		
		// save id
		SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor=prefs.edit();
		editor.putLong("DLID", idDownload);
		editor.commit();
		
		//TODO attach a listener to get updates in app
	}
	public static String getContrib(){
		String URL = config.PAC_CONTRIB;
		
		DefaultHttpClient mClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(URL);
		try {
			HttpResponse getResponse = mClient.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.e("OTA_TOOLS", "#BLAMETYLER " + statusCode
						+ " for URL " + URL);
				return null;
			}
			Log.d("OTA_TOOLS", "Got Connection " + URL);
			HttpEntity getResponseEntity = getResponse.getEntity();

			if (getResponseEntity != null) {
				return EntityUtils.toString(getResponseEntity);
			}

		} catch (IOException e) {
			getRequest.abort();
			Log.e("OTA_TOOLS", "#BLAMETYLER Error for URL " + URL, e);
		}

		return null;
		
	}
	
	public static final int DISCONNECTED = 0;
	public static final int MOBILE = 1;
	public static final int WIFI = 2;

	public static int checkConnection(Context context){
		ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileCon = conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiCon = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if ( mobileCon != null){
			if ( mobileCon.getState() ==  NetworkInfo.State.CONNECTED){
				return MOBILE;
			}
		}
		if ( wifiCon != null){
			if ( wifiCon.getState() == NetworkInfo.State.CONNECTED  ) {
				return WIFI;
			}
		}
		return DISCONNECTED;
	}

	public static String checkRom(String device, String Type){
		String URL = config.OTA_SCRIPT;
		URL += "?device=";
		URL += device;
		URL += "&type=";
		URL += Type;

		DefaultHttpClient mClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(URL);
		try {
			HttpResponse getResponse = mClient.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.e("OTA_TOOLS", "#BLAMETYLER " + statusCode
						+ " for URL " + URL);
				return null;
			}
			Log.d("OTA_TOOLS", "Got Connection " + URL);
			HttpEntity getResponseEntity = getResponse.getEntity();

			if (getResponseEntity != null) {
				return EntityUtils.toString(getResponseEntity);
			}

		} catch (IOException e) {
			getRequest.abort();
			Log.e("OTA_TOOLS", "#BLAMETYLER Error for URL " + URL, e);
		}

		
		return null;
		
	}
	public static String getChanges() {
		String URL = config.PAC_CHANGES;
		String dev = "&device="+ (LocalTools.getProp("ro.cm.device").equals("")? Build.PRODUCT : LocalTools.getProp("ro.cm.device"));
		URL += dev;
		
		DefaultHttpClient mClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(URL);
		try {
			HttpResponse getResponse = mClient.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.e("OTA_TOOLS", "#BLAMETYLER " + statusCode
						+ " for URL " + URL);
				return null;
			}
			Log.d("OTA_TOOLS", "Got Connection " + URL);
			HttpEntity getResponseEntity = getResponse.getEntity();

			if (getResponseEntity != null) {
				return EntityUtils.toString(getResponseEntity);
			}

		} catch (IOException e) {
			getRequest.abort();
			Log.e("OTA_TOOLS", "#BLAMETYLER Error for URL " + URL, e);
		}

		return null;
	}
}
