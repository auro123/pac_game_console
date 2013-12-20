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

import android.os.Bundle;
import android.pacstats.PACStats;
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

	// some variables...
    private ArrayList<ListArrayItem> mGameTitles;
    private ListView mDrawerList;
    private drawerItemType mSelectedItem;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int poss = 0;
    private boolean state = false;
    Fragment mContent = null;
    
    // *** setup the Flags for the Frags
    private final int OTA_FLAG = 0;
    private final int CHANGE_FLAG = 1;
    private final int CONTRIB_FLAG = 2;
    private final int ABOUT_FLAG = 3;
    private final int STATS_FLAG = 4;
    
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
        // are we new or old?
        if (ofLove!=null){
	        poss = ofLove.getInt("flag");
	        state = ofLove.getBoolean("store");
        } else {
        	Intent intent = getIntent();
	        poss = intent.getIntExtra("flag", 0);
	        state = intent.getBooleanExtra("store", false);
        }
        
        // load the main XML
        setContentView(R.layout.pac_console);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        // draw toggler listeners
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
        
        // Setup the Menu List
        createDrawList();
        
        mDrawerList.setAdapter(new drawerItemAdapter(this,R.layout.drawer_list_item, mGameTitles));
        
        mDrawerList.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				// handle clicks on the drawer
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
        
        // LOAD State
        if (!state){
        	mDrawerLayout.openDrawer(mDrawerList);
        } else {
        	attachFrag(poss);
        }

    }
    
    /**
     * Attach the right Fragment
     * @param possition
     */
	private void attachFrag(int possition) {
		// TODO swap fragment out.
		
		/**
		 * use tag to select the frag needed.
		 */
        Fragment fragment = null;
        android.app.FragmentManager fragmentManager = getFragmentManager();
        
        if (mGameTitles.get(possition).getViewType() != RowType.HEADER_ITEM.ordinal() && fragment == null){
        	switch (((drawerItemType) mGameTitles.get(possition)).getFlag()){
        	case OTA_FLAG:
        		fragment = new OTA_frag();
        		break;
        	case CHANGE_FLAG:
        		fragment = new Changes_frag();
        		break;
        	case CONTRIB_FLAG:
        		fragment = new Contrib_frag();
        		break;
        	case ABOUT_FLAG:
        		fragment = new About_frag();
        		break;
        	case STATS_FLAG:
        		fragment = new PACStats();
        		break; 
        	default:
        		fragment = text_frag.newInstance("\n\n\nThe Devs Done F**ked Up!!!!\n\nI Blame Tyler!\n\nYou need to add and then attach the Fragment!");
        	}
        	// TODO tag is miffed
        	// TODO find out if thats a problem or not...
	        // Insert the fragment by replacing any existing fragment
	        fragmentManager.beginTransaction()
	                       .replace(R.id.content_frame, fragment, ""+((drawerItemType) mGameTitles.get(possition)).getFlag())
	                       .commit();
        }

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(possition, true);
        
        mSelectedItem = (drawerItemType) mGameTitles.get(possition);
        
        mDrawerLayout.closeDrawer(mDrawerList);
		
	}
	/**
	 * Creates the Draw List
	 * 
	 * TODO maybe add fragment handling here and drop the switch case fragment thingo
	 * 
	 */
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
        ((drawerItemType) holder).setFlag(OTA_FLAG);
       
        mGameTitles.add(holder);
        //Changes
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle(this.getResources().getString(R.string.change_menu_lbl));
        ((drawerItemType) holder).setCaption(this.getResources().getString(R.string.change_menu_cap));
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(CHANGE_FLAG);

        mGameTitles.add(holder);
        
        holder = new aospHeader("Interface");
        
        mGameTitles.add(holder);
        
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Active Display");
        ((drawerItemType) holder).setCaption("Moto X Active Display");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(-1);

        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Battery");
        ((drawerItemType) holder).setCaption("Battery Icon and Notification Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(-1);

        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Quick Tiles");
        ((drawerItemType) holder).setCaption("Notification Quick Toggle Tile Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(-1);

        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Lock Screen");
        ((drawerItemType) holder).setCaption("Lock Screen Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(-1);

        mGameTitles.add(holder);
        
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Signal");
        ((drawerItemType) holder).setCaption("Signal Icon and Notification Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(-1);

        mGameTitles.add(holder);

        holder = new aospHeader("Hybrid");
        
        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("Global");
        ((drawerItemType) holder).setCaption("Set Global Hybrid Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(-1);

        mGameTitles.add(holder);

        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle("App Specific");
        ((drawerItemType) holder).setCaption("Set Per App Hybrid Options");
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(-1);

        mGameTitles.add(holder);

        holder = new aospHeader("ROM Info");
        
        mGameTitles.add(holder);
        
        //Contributers
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle(this.getResources().getString(R.string.contrib_menu_lbl));
        ((drawerItemType) holder).setCaption(this.getResources().getString(R.string.contrib_menu_cap));
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(CONTRIB_FLAG);

        mGameTitles.add(holder);

        // About PAC Frag and set as default.
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle(this.getResources().getString(R.string.stat_menu_lbl));
        ((drawerItemType) holder).setCaption(this.getResources().getString(R.string.stat_menu_cap));
        ((drawerItemType) holder).setCaptionDisplay(false);
        ((drawerItemType) holder).setFlag(STATS_FLAG);
       
        mGameTitles.add(holder);
                
        // Help Frag
        holder = new drawerItemType();
        ((drawerItemType) holder).setTittle(this.getResources().getString(R.string.help_menu_lbl));
        ((drawerItemType) holder).setCaption(this.getResources().getString(R.string.help_menu_cap));
        ((drawerItemType) holder).setCaptionDisplay(true);
        ((drawerItemType) holder).setFlag(ABOUT_FLAG);
       
        mGameTitles.add(holder);


    }
    
    // as of now there is no menu this will change
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
