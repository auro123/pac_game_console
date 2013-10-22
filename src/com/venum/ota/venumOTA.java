package com.venum.ota;

import java.util.ArrayList;

import com.pac.console.R;
import com.venum.ota.adapters.drawerItemAdapter;
import com.venum.ota.adapters.drawerItemType;
import com.venum.ota.ui.OTA_frag;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

//4.3 branch
public class venumOTA extends Activity {

    private ArrayList<drawerItemType> mGameTitles;
    private ListView mDrawerList;
    
    private drawerItemType mSelectedItem;
    
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int poss = 0;
    private boolean state = false;
    Fragment mContent = null;
    @Override
    public void onSaveInstanceState(Bundle ofLove) {
      super.onSaveInstanceState(ofLove);
      // Save UI state changes to the savedInstanceState.
      // This bundle will be passed to onCreate if the process is
      // killed and restarted.
      ofLove.putInt("flag", poss);
      ofLove.putBoolean("store", true);

    }
    
    @Override
    public void onRestoreInstanceState(Bundle ofLove) {
      super.onRestoreInstanceState(ofLove);
      // Restore UI state from the savedInstanceState.
      // This bundle has also been passed to onCreate. 
      poss = ofLove.getInt("flag");
      state = ofLove.getBoolean("store");

    }

    @Override
    protected void onCreate(Bundle ofLove) {
        super.onCreate(ofLove);
        
        if (ofLove!=null){
	        poss = ofLove.getInt("flag");
	        state = ofLove.getBoolean("store");
        } else {
        	Intent intent = getIntent();
	        poss = intent.getIntExtra("flag", 0);
	        state = intent.getBooleanExtra("store", false);
        }
        
        
        setContentView(R.layout.pac_console);
       
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  
                mDrawerLayout,         
                R.drawable.ic_drawer, 
                R.string.app_name, 
                R.string.app_name  
                ) {
        	
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            	//TODO Update the actionbar title
            	if(mSelectedItem != null){
            		getActionBar().setTitle(mSelectedItem.title);
            	}
            	
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	//TODO Update the actionbar title
            	getActionBar().setTitle(venumOTA.this.getResources().getString(R.string.app_name));

            }
        };
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);
                
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        //make our list for the drawer
        createDrawList();
        
        mDrawerList.setAdapter(new drawerItemAdapter(this,R.layout.drawer_list_item, mGameTitles));
        
        mDrawerList.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ATTACH req fragment to content view
				attachFrag(arg2);
				mDrawerList.setSelection(arg2);
				poss = arg2;
			}

        	
        });
        
        // setup the drawer tab
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        if (!state){
        	mDrawerLayout.openDrawer(mDrawerList);
        } else {
        	attachFrag(poss);
        }

    }
	private void attachFrag(int possition) {
		// TODO swap fragment out.
		
		/**
		 * use tag to select the frag needed.
		 */
	        Fragment fragment = null;
	        android.app.FragmentManager fragmentManager = getFragmentManager();
	        if (fragment == null){
		        if (mGameTitles.get(possition).FLAG.equalsIgnoreCase("ota")){
		        	fragment = new OTA_frag();
		        } 

	        // Insert the fragment by replacing any existing fragment
	        fragmentManager.beginTransaction()
	                       .replace(R.id.content_frame, fragment, mGameTitles.get(possition).FLAG)
	                       .commit();
	    }

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(possition, true);
        
        mSelectedItem = mGameTitles.get(possition);
        
        mDrawerLayout.closeDrawer(mDrawerList);
		
	}

    private void createDrawList(){
    	
        mGameTitles = new ArrayList<drawerItemType>();

        // ok here we go!
        
        /**
         * list is as follows
         * 
         * Update ROM
         */
        // OTA Frag
        drawerItemType holder = new drawerItemType();
        holder.title = this.getResources().getString(R.string.ota_menu_lbl);
        holder.caption = this.getResources().getString(R.string.ota_menu_cap);
        holder.caption_display = true;
        holder.FLAG = "ota";
       
        mGameTitles.add(holder);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.pac_console, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    	if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // TODO Handle your other itmes...

        return super.onOptionsItemSelected(item);
    }

    
}
