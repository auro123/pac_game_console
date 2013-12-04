package com.pac.console.adapters;

public class changeItemType {

	private String SHA;
	private String title;
	private String caption;
	private String date;
	private String author;
	private String URL;
	private boolean header = false;
	private boolean newChange = false;
	
	public boolean getNew(){
		return this.newChange;
	}

	public String getSHA(){
		return this.SHA;
	}
	public String getTittle(){
		return this.title;
	}
	public String getCaption(){
		return this.caption;
	}
	public String getDate(){
		return this.date;
	}
	public String getAuthor(){
		return this.author;
	}
	public String getURL(){
		return this.URL;
	}
	public boolean getIsHeader(){
		return this.header;
	}
	
	public void setSHA(String in){
		this.SHA = in;
	}
	public void setTitle(String in){
		this.title = in;
	}
	public void setCaption(String in){
		this.caption = in;
	}
	public void setDate(String in){
		this.date = in;
	}
	public void setAuthor(String in){
		this.author = in;
	}
	public void setURL(String in){
		this.URL = in;
	}
	public void setIsHeader(boolean in){
		this.header = in;
	}
	public void setNewChange(boolean in){
		this.newChange = in;
	}


}
