package com.pac.console.ui;

import java.util.ArrayList;

import org.json.JSONException;

import com.pac.console.R;
import com.pac.console.adapters.Header;
import com.pac.console.adapters.Item;
import com.pac.console.adapters.changeItemAdapter;
import com.pac.console.adapters.changeItemType;
import com.pac.console.adapters.changeItemAdapter.RowType;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Changes_frag extends Fragment {
	
	TextView totals;
	ListView change;
	ArrayList<Item> changeList;
	changeItemAdapter changeAdapter;
	boolean store = false;
	
	private Animation mSlideSelInAnimation;
	private Animation mSlideSelOutAnimation;

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
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle ofLove) {
		
        if (ofLove!=null){
	        store = ofLove.getBoolean("store");
        }

		View layout = inflater.inflate(R.layout.change_frag_layout, null);
		totals = (TextView) layout.findViewById(R.id.cfl_totals);
		change = (ListView) layout.findViewById(R.id.cfl_list);
		change.setEmptyView(layout.findViewById(R.id.cfl_empty));
		change.setOnScrollListener(new OnScrollListener(){
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState){
				case 0: // idle
					totals.startAnimation(mSlideSelInAnimation);
					break;
				case 1: // touch
					totals.startAnimation(mSlideSelOutAnimation);
					break;
				case 2: // fling
					totals.startAnimation(mSlideSelOutAnimation);
					break;
				}
			}
		});
		initAnim();
		totals.startAnimation(mSlideSelOutAnimation);

		changeList = new ArrayList<Item>();
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
				if (changeList.get(arg2).getViewType() != RowType.HEADER_ITEM.ordinal()){
					String url = "http://github.com/"+((changeItemType) changeList.get(arg2)).getAuthor()+"/"+((changeItemType) changeList.get(arg2)).getTittle()+"/commit/"+((changeItemType) changeList.get(arg2)).getSHA();
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
	    	String changeNote = msg.getData().getString("changes");
	    	if(changeNote != null){
	    		totals.setText(changeNote);
	    		totals.startAnimation(mSlideSelInAnimation);

	    	}
			changeAdapter = new changeItemAdapter(Changes_frag.this.getActivity(),R.layout.drawer_list_item, changeList);
			change.setAdapter(changeAdapter);
			change.postInvalidate();
	    }

	};
	private class CheckRemote extends
	AsyncTask<String, Void, ArrayList<Item>> {

		@Override
		protected ArrayList<Item> doInBackground(String... arg0) {
						
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
		protected void onPostExecute(final ArrayList<Item> result) {
			Message msg = new Message();
			Bundle data = new Bundle();
			if (result != null){
				boolean found = false;
				int behind = 0;
				for (int i = 0 ;i<result.size();i++){
					if(result.get(i).getViewType() != RowType.HEADER_ITEM.ordinal()){
						if (((changeItemType)result.get(i)).getNew()){
							behind++;
						} else {
							break;
						}
					}
					
				}
				data.putString("changes", "Your build is "+behind+" Commits Behind");
				if (Changes_frag.this.getActivity()!=null){
					//Settings.System.putString(Changes_frag.this.getActivity().getContentResolver(), "changes", result);
				}
			} else {
				data.putString("changes", "Server Down\nOr Tyler Broke Something!");
			}
			changeList = result;
			msg.setData(data);
			updateRemote.sendMessage(msg);
		}
	};
	private void initAnim() {
		final AnimationListener makeSelVis = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				totals.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {

			}
		};

		final AnimationListener makeSelHide = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				totals.setVisibility(View.GONE);
			}
		};

		mSlideSelInAnimation = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_SELF, 0f,
				TranslateAnimation.RELATIVE_TO_SELF, 0f,
				TranslateAnimation.RELATIVE_TO_SELF, -1f,
				TranslateAnimation.RELATIVE_TO_SELF, 0f);
		mSlideSelOutAnimation = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_SELF, 0f,
				TranslateAnimation.RELATIVE_TO_SELF, 0f,
				TranslateAnimation.RELATIVE_TO_SELF, 0f,
				TranslateAnimation.RELATIVE_TO_SELF, -1f);
		mSlideSelInAnimation.setDuration(500);
		mSlideSelOutAnimation.setDuration(500);
		mSlideSelInAnimation
				.setInterpolator(new AccelerateDecelerateInterpolator());
		mSlideSelOutAnimation
				.setInterpolator(new AccelerateDecelerateInterpolator());
		mSlideSelInAnimation.setAnimationListener(makeSelVis);
		mSlideSelOutAnimation.setAnimationListener(makeSelHide);
	}
}
