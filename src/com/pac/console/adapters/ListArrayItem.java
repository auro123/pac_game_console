package com.pac.console.adapters;

import android.view.LayoutInflater;
import android.view.View;
/**
 * Basic Multi Type Item interface
 * @author pvyParts
 *
 */
public interface ListArrayItem {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
