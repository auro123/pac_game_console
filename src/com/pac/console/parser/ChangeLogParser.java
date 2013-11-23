package com.pac.console.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pac.console.adapters.changeItemType;

public class ChangeLogParser {
	public static ArrayList<changeItemType> ChangeLogParser(String JsonData)
			throws JSONException {
		JSONArray jsonArray = new JSONArray(JsonData);
		ArrayList<changeItemType> JSONArray = new ArrayList<changeItemType>();
		for (int i = 0; i < jsonArray.length(); i++) {
			changeItemType hold = new changeItemType();
			JSONObject json_data = jsonArray.getJSONObject(i);
			hold.SHA = json_data.getString("SHA");
			hold.title = json_data.getString("Repository");
			hold.caption = json_data.getString("Message");
			hold.date = json_data.getString("CommitDate");
			JSONArray.add(hold);
		}
		ArrayList<changeItemType> formatedArray = new ArrayList<changeItemType>();
		String str_date = "";
		for (int i = 0; i < JSONArray.size(); i++){
			if (!str_date.equals(JSONArray.get(i).date.substring(0, 10))){
				str_date = JSONArray.get(i).date.substring(0, 10);
				changeItemType hold = new changeItemType();
				hold.date = JSONArray.get(i).date.substring(0, 10);
				hold.header = true;
				formatedArray.add(hold);				
			}
			changeItemType hold2 = new changeItemType();
			hold2.SHA = JSONArray.get(i).SHA;
			hold2.title = JSONArray.get(i).title;
			hold2.caption = JSONArray.get(i).caption;
			hold2.date = JSONArray.get(i).date;
			formatedArray.add(hold2);
		}
		return formatedArray;

	}
}
