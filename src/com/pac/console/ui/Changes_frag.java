package com.pac.console.ui;

import java.util.ArrayList;

import org.json.JSONException;

import com.pac.console.R;
import com.pac.console.adapters.changeItemAdapter;
import com.pac.console.adapters.changeItemType;
import com.pac.console.parser.ChangeLogParser;
import com.pac.console.util.RemoteTools;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Changes_frag extends Fragment {
	
	ListView change;
	ArrayList<changeItemType> changeList;
	changeItemAdapter changeAdapter;
	public String changes = "";
	boolean store = false;
	
	public static Changes_frag newInstance(String content) {
		Changes_frag fragment = new Changes_frag();		
		return fragment;
	
	}
	
    @Override
    public void onSaveInstanceState(Bundle ofLove) {
      super.onSaveInstanceState(ofLove);
      // Save UI state changes to the savedInstanceState.
      // This bundle will be passed to onCreate if the process is
      // killed and restarted.
      ofLove.putBoolean("store", true);
      ofLove.putString("changes", changes);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle ofLove) {
		
        if (ofLove!=null){
        	changes = ofLove.getString("changes");
	        store = ofLove.getBoolean("store");
        }

        changes = Settings.System.getString(this.getActivity().getContentResolver(), "changes");
		View layout = inflater.inflate(R.layout.change_frag_layout, null);
		change = (ListView) layout.findViewById(R.id.cfl_list);
		changeList = new ArrayList<changeItemType>();
		changeAdapter = new changeItemAdapter(this.getActivity(),R.layout.drawer_list_item, changeList);
		change.setAdapter(changeAdapter);
		//restore old state if needed
		int con = RemoteTools.checkConnection(Changes_frag.this.getActivity());
		if (con > RemoteTools.DISCONNECTED){
			AsyncTask checkTast = new CheckRemote();
			String[] dev = {" "};
			dev[0] = (String)Build.DEVICE;
			checkTast.execute(dev);
		} else {
		}
		change.setOnItemClickListener(new OnItemClickListener(){
//			     http://github.com/" .$commit['GitUsername']. "/" . $commit['Repository'] . "/commit/" . $commit['SHA'] 

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ATTACH req fragment to content view
				if (!changeList.get(arg2).header){
					String url = "http://github.com/"+changeList.get(arg2).author+"/"+changeList.get(arg2).title+"/commit/"+changeList.get(arg2).SHA;
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(browserIntent);
				}
			}

        	
        });

		return layout;
	}

	Handler updateRemote = new Handler(){

	    @Override
	    public void handleMessage(Message msg){
			changeAdapter = new changeItemAdapter(Changes_frag.this.getActivity(),R.layout.drawer_list_item, changeList);
			change.setAdapter(changeAdapter);
			change.postInvalidate();
	    }

	};
	private class CheckRemote extends
	AsyncTask<String, Void, ArrayList<changeItemType>> {

		@Override
		protected ArrayList<changeItemType> doInBackground(String... arg0) {
			
			// TODO Auto-generated method stub
			if (Changes_frag.this.changes != null){
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("changes", changes);
				msg.setData(data);
				updateRemote.sendMessage(msg);
			}
			
			String out = RemoteTools.getChanges();
			try {
				return ChangeLogParser.ChangeLogParser(out);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(final ArrayList<changeItemType> result) {
			Message msg = new Message();
			Bundle data = new Bundle();
			if (result != null){
				data.putString("changes", "UPDATE");
				if (Changes_frag.this.getActivity()!=null){
					//Settings.System.putString(Changes_frag.this.getActivity().getContentResolver(), "changes", result);
				}
			} else {
				if (Changes_frag.this.changes==null){
					data.putString("changes", "Server Down\nOr Tyler Broke Something!");
				}
			}
			changeList = result;
			msg.setData(data);
			updateRemote.sendMessage(msg);
		}
	};
}
