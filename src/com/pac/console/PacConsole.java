package com.pac.console;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PacConsole extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pac_console);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pac_console, menu);
        return true;
    }
    
}
