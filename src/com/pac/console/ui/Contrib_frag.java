package com.pac.console.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.uncod.android.bypass.Bypass;

import com.pac.console.R;
import com.pac.console.util.RemoteTools;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Contrib_frag extends Fragment {
	
	TextView contrib;
	public String contribs = "";
	boolean store = false;
	
	public static Contrib_frag newInstance(String content) {
		Contrib_frag fragment = new Contrib_frag();		
		return fragment;
	
	}
	
    @Override
    public void onSaveInstanceState(Bundle ofLove) {
      super.onSaveInstanceState(ofLove);
      // Save UI state changes to the savedInstanceState.
      // This bundle will be passed to onCreate if the process is
      // killed and restarted.
      ofLove.putBoolean("store", true);
      ofLove.putString("contribs", contribs);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle ofLove) {
		
        if (ofLove!=null){
        	contribs = ofLove.getString("contribs");
	        store = ofLove.getBoolean("store");
        }
        
        contribs = Settings.System.getString(this.getActivity().getContentResolver(), "contribs");
        
		View layout = inflater.inflate(R.layout.contrib_frag_layout, null);

		contrib = (TextView) layout.findViewById(R.id.textView1);

		//restore old state if needed
			int con = RemoteTools.checkConnection(Contrib_frag.this.getActivity());
			if (con > RemoteTools.DISCONNECTED){
				AsyncTask checkTast = new CheckRemote();
				String[] dev = {" "};
				dev[0] = (String)Build.DEVICE;
				checkTast.execute(dev);
			} else {
				if (contribs==null){
					contrib.setText(Contrib_frag.this.getActivity().getString(R.string.no_data));
				}
			}
		return layout;
	}
	
	Handler updateRemote = new Handler(){
	    @Override
	    public void handleMessage(Message msg){
	    	//msg.getData().getString("file");
			Bypass bypass = new Bypass();
			String markdownString = msg.getData().getString("contribs");
			contribs = markdownString;
			String[] formater = markdownString.split("\n");
			markdownString = "";
			Pattern pattern = Pattern.compile("[^\\S\\r\\n]{2,}");

			for (int i = 0; i< formater.length; i++){
				Matcher matcher = pattern.matcher(formater[i]);
					//formater[i].replaceAll("\\s{2,}+||\\t", " ");
				String str = matcher.replaceAll(" ");
				markdownString+=str+"\n";	
			}
			CharSequence string = bypass.markdownToSpannable(markdownString);
			Log.d("MARKUP", ""+string);
			contrib.setText(string);
			//contrib.setMovementMethod(LinkMovementMethod.getInstance());

	    }

	};
	private class CheckRemote extends
	AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			if (Contrib_frag.this.contribs != null){
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("contribs", contribs);
				msg.setData(data);
				updateRemote.sendMessage(msg);
			}
			String out = RemoteTools.getContrib();
			return out;
		}
		@Override
		protected void onPostExecute(final String result) {
			Message msg = new Message();
			Bundle data = new Bundle();
			if (result != null){
				data.putString("contribs", result);
				if (Contrib_frag.this.getActivity()!=null){
					Settings.System.putString(Contrib_frag.this.getActivity().getContentResolver(), "contribs", result);
				}
			} else {
				data.putString("contribs", "Or Tyler Broke Something!");
			}
			msg.setData(data);
			updateRemote.sendMessage(msg);

		}
	};
}
