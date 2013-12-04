package com.pac.console.adapters;

import android.widget.CompoundButton.OnCheckedChangeListener;

public class drawerItemType {

	private String FLAG;
	private String title;
	
	private boolean caption_display = false;
	private String caption;
	
	private boolean URL_launch = false;
	private String URL;
	
	private boolean package_launch = false;
	private String package_name;
	private String package_path;
	
	private boolean toggle_display = false;
	private OnCheckedChangeListener ontoggle;
	
	public String getFlag(){
		return this.FLAG;
	}
	public String getTittle(){
		return this.title;
	}
	public boolean getCaptionDisplay(){
		return this.caption_display;
	}
	public String getCaption(){
		return this.caption;
	}
	public boolean getURLLaunch(){
		return this.URL_launch;
	}
	public String getURL(){
		return this.URL;
	}
	public String getPackageName(){
		return this.package_name;
	}
	public boolean getPackageLaunch(){
		return this.package_launch;
	}
	public String getPackagePath(){
		return this.package_path;
	}
	public boolean getToggleDisplay(){
		return this.toggle_display;
	}
	public OnCheckedChangeListener getOnToggleListener(){
		return this.ontoggle;
	}

	public void setFlag(String in){
		this.FLAG = in;
	}
	public void setTittle(String in){
		this.title = in;
	}
	public void setCaptionDisplay(boolean in){
		this.caption_display = in;
	}
	public void setCaption(String in){
		this.caption = in;
	}
	public void setURLLaunch(boolean in){
		this.URL_launch = in;
	}
	public void setURL(String in){
		this.URL = in;
	}
	public void setPackageName(String in){
		this.package_name = in;
	}
	public void setPackageLaunch(boolean in){
		this.package_launch = in;
	}
	public void setPackagePath(String in){
		this.package_path = in;
	}
	public void setToggleDisplay(boolean in){
		this.toggle_display = in;
	}
	public void setOnToggleListener(OnCheckedChangeListener in){
		this.ontoggle = in;
	}

}
