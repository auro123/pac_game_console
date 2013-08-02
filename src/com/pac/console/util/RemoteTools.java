package com.pac.console.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.pac.console.config;

public class RemoteTools {
	
	public static void downloadFile(Context context, String url, String fileName){
		Uri URL = Uri.parse(url);
		DownloadManager.Request r = new DownloadManager.Request(URL);

		// This put the download in the same Download dir the browser uses
		r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS+"/PAC/", fileName);

		// When downloading music and videos they will be listed in the player
		// (Seems to be available since Honeycomb only)
		r.allowScanningByMediaScanner();

		// Notify user when download is completed

		r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

		// Start download
		DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		dm.enqueue(r);

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
	
		if ( conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ){
			return MOBILE;
		}
		if ( conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING  ) {
			return WIFI;
		}
		else if ( conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED 
		    ||  conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			return DISCONNECTED;	
		}
		return DISCONNECTED;
	}

	public static String checkRom(String device){
		String URL = config.OTA_SCRIPT;
		URL += "?device=";
		URL += device;
		URL += "&type=check";
		
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
