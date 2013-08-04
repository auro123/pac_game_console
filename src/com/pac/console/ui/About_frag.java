package com.pac.console.ui;

import com.pac.console.R;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class About_frag extends PreferenceFragment {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        addPreferencesFromResource(R.xml.about_pac);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if (preference.getKey().equals("web")){
			launchIntent(getString(R.string.pref_abt_web_link));
		} else if (preference.getKey().equals("irc")){
			launchIntent(getString(R.string.pref_abt_irc_link));
		} else if (preference.getKey().equals("facebook")){
			launchIntent(getString(R.string.pref_abt_facebook_link));
		} else if (preference.getKey().equals("google")){
			launchIntent(getString(R.string.pref_abt_google_link));
		}
		
		return super.onPreferenceTreeClick(preferenceScreen, preference);
		
		
	}
	
    private void launchIntent(String uri){
    	try{
    		Intent launchIntents = new Intent(
					"android.intent.action.VIEW", Uri.parse(uri));
    		launchIntents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(launchIntents);
    		
    	} catch (ActivityNotFoundException e){
    		Toast.makeText(this.getActivity(), "#BLAMETYLER - No App Installed Capable of launching This!" , Toast.LENGTH_SHORT).show();
    	}

    }

	public static About_frag newInstance(String content) {
		About_frag fragment = new About_frag();		
		return fragment;
		
	}
	

}
