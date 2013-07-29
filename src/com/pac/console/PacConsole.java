package com.pac.console;

import java.util.ArrayList;

import com.pac.console.adapters.drawerItemAdapter;
import com.pac.console.adapters.drawerItemType;
import com.pac.console.ui.About_frag;
import com.pac.console.ui.OTA_frag;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
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

public class PacConsole extends Activity {

    private ArrayList<drawerItemType> mGameTitles;
    private ListView mDrawerList;
    
    private drawerItemType mSelectedItem;
    
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            	getActionBar().setTitle("PAC Console");

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
				//mDrawerList.setSelection(arg2);
			}

        	
        });
        // setup the drawer tab
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.openDrawer(mDrawerList);
        getActionBar().setTitle("PAC Console");
        
        //args.putInt(DragFrag.ARG_PORT_NUMBER, mTrackTitles.get(position).port);
        //fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        

    }
    
	private void attachFrag(int possition) {
		// TODO swap fragment out.
		
		/**
		 * use tag to select the frag needed.
		 */
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new OTA_frag();
        Bundle args = new Bundle();
        //args.putInt(DragFrag.ARG_PORT_NUMBER, mTrackTitles.get(position).port);
        //fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.content_frame, fragment)
                       .commit();

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
         * PA Stuff		|
         * CM Stuff		|-- All grouped by type 
         * AOKP Stuff	|		eg. all display options together, all sound options together.
         * PAC Stuff	|
         * 
         * About us
         * Contributors
         * Help
         */
        // OTA Frag
        drawerItemType holder = new drawerItemType();
        holder.title = "Update Rom";
        holder.caption = "Toggle OTA Update Options";
        holder.caption_display = true;
        holder.FLAG = "ota";
       
        mGameTitles.add(holder);
        
        //Contributers
        holder = new drawerItemType();
        holder.title = "PAC Contributers";
        holder.caption = "Who makes this possible?";
        holder.caption_display = true;
        holder.FLAG = "contributors";
       
        mGameTitles.add(holder);

        // About PAC Frag and set as default.
        holder = new drawerItemType();
        holder.title = "About PAC";
        holder.FLAG = "about";
       
        mGameTitles.add(holder);
        
        Fragment fragment = new About_frag();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.content_frame, fragment)
                       .commit();

        mDrawerList.setItemChecked(mGameTitles.size()-1, true);       
        mSelectedItem = mGameTitles.get(mGameTitles.size()-1);
        
        // Help Frag
        holder = new drawerItemType();
        holder.title = "Help";
        holder.caption = "Contact Devs to get Help!";
        holder.caption_display = true;
        holder.FLAG = "help";
       
        mGameTitles.add(holder);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pac_console, menu);
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
