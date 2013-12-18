package com.pac.console;

import java.util.ArrayList;

import com.pac.console.adapters.ListArrayItem;
import com.pac.console.adapters.aospHeader;
import com.pac.console.adapters.drawerItemAdapter;
import com.pac.console.adapters.drawerItemAdapter.RowType;
import com.pac.console.adapters.drawerItemType;
import com.pac.console.ui.About_frag;
import com.pac.console.ui.Changes_frag;
import com.pac.console.ui.Contrib_frag;
import com.pac.console.ui.OTA_frag;
import com.pac.console.ui.text_frag;

import android.media.audiofx.BassBoost.Settings;
import android.net.Uri;
import android.os.Bundle;
import android.pacstats.PACStats;
import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * PAC Console 
 * 
 *  Nav Draw
 *  
 *  Basic last item handing for rotations
 *
 * @author pvyParts
 *
 */
public class PacConsole extends Activity {

    private ArrayList<ListArrayItem> mGameTitles;
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
            		getActionBar().setTitle(mSelectedItem.getTittle());
            	}
            	
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	//TODO Update the actionbar title
            	getActionBar().setTitle(PacConsole.this.getResources().getString(R.string.app_name));

            }
        };
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);
                
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        //make our list for the drawer
        createDrawList();
        
        //TODO FIX THIS!!!!!!!
        mDrawerList.setAdapter(new drawerItemAdapter(this,R.layout.drawer_list_item, mGameTitles));
        
        mDrawerList.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ATTACH req fragment to content view
				if (mGameTitles.get(arg2).getViewType() == RowType.LIST_ITEM.ordinal()){
					attachFrag(arg2);
					mDrawerList.setSelection(arg2);
					poss = arg2;
				}
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
		        if (((drawerItemType) mGameTitles.get(possition)).getFlag().equalsIgnoreCase("ota")){
		        	fragment = new OTA_frag();
		        } else if (((drawerItemType) mGameTitles.get(possition)).getFlag().equalsIgnoreCase("contributors")){
		        	fragment = new Contrib_frag();
		        } else if (((drawerItemType) mGameTitles.get(possition)).getFlag().equalsIgnoreCase("about")){
		        	fragment = new About_frag();
		        } else if (((drawerItemType) mGameTitles.get(possition)).getFlag().equalsIgnoreCase("stats")){
		        	fragment = new PACStats();
		        } else if (((drawerItemType) mGameTitles.get(possition)).getFlag().equalsIgnoreCase("changes")){
		        	fragment = new Changes_frag();
		        }
		        if (fragment==null){
		        	// bad bad bad !!!
		        	fragment = text_frag.newInstance("The Devs Done F**ked Up!!!!\n\nI Blame Tyler!\n\nYou need to add and then attach the Fragment!");
		        	
		        }
	        // Insert the fragment by replacing any existing fragment
	        fragmentManager.beginTransaction()
	                       .replace(R.id.content_frame, fragment, ((drawerItemType) mGameTitles.get(possition)).getFlag())
	                       .commit();
	    }

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(possition, true);
        
        mSelectedItem = (drawerItemType) mGameTitles.get(possition);
        
        mDrawerLayout.closeDrawer(mDrawerList);
		
	}

    private void createDrawList(){
    	
        mGameTitles = new ArrayList<ListArrayItem>();

        // ok here we go!
        
        // UPDATE THIS !!!!
        
        // OTA Frag
        ListArrayItem holder;
        holder = new aospHeader("Updates");
        
        mGameTitles.add(holder);
        
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle(this.getResources().getString(R.string.ota_menu_lbl));
        ((drawerItemType) holder).setCaption(this.getResources().getString(R.string.ota_menu_cap));
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("ota");
       
        mGameTitles.add(holder);
        //Changes
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle(this.getResources().getString(R.string.change_menu_lbl));
        ((drawerItemType) holder).setCaption(this.getResources().getString(R.string.change_menu_cap));
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("changes");

        mGameTitles.add(holder);
        
        holder = new aospHeader("Interface");
        
        mGameTitles.add(holder);
        
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Active Display");
        ((drawerItemType) holder).setCaption("Moto X Active Display");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("activedisplay");

        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Battery");
        ((drawerItemType) holder).setCaption("Battery Icon and Notification Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("battery");

        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Quick Tiles");
        ((drawerItemType) holder).setCaption("Notification Quick Toggle Tile Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("battery");

        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Lock Screen");
        ((drawerItemType) holder).setCaption("Lock Screen Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("battery");

        mGameTitles.add(holder);
        
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Signal");
        ((drawerItemType) holder).setCaption("Signal Icon and Notification Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("signal");

        mGameTitles.add(holder);

        holder = new aospHeader("Hybrid");
        
        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Global");
        ((drawerItemType) holder).setCaption("Set Global Hybrid Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("hyb_global");

        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("App Specific");
        ((drawerItemType) holder).setCaption("Set Per App Hybrid Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("hyb_apps");

        mGameTitles.add(holder);

        holder = new aospHeader("ROM Info");
        
        mGameTitles.add(holder);
        
        //Contributers
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle(this.getResources().getString(R.string.contrib_menu_lbl));
        ((drawerItemType) holder).setCaption(this.getResources().getString(R.string.contrib_menu_cap));
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("contributors");

        mGameTitles.add(holder);

        // About PAC Frag and set as default.
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle(this.getResources().getString(R.string.stat_menu_lbl));
        ((drawerItemType) holder).setCaption(this.getResources().getString(R.string.stat_menu_cap));
        ((drawerItemType) holder).setCaptionDisplay(false);
        ((drawerItemType) holder).setFlag("stats");
       
        mGameTitles.add(holder);
                
        // Help Frag
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle(this.getResources().getString(R.string.help_menu_lbl));
        ((drawerItemType) holder).setCaption(this.getResources().getString(R.string.help_menu_cap));
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag("about");
       
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
