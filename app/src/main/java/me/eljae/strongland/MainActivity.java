package me.eljae.strongland;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.maps.*;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       /* TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec;
        Intent intent;

        // Main tab
        spec = tabHost.newTabSpec("Map");
        spec.setIndicator("Map");
        intent = new Intent(this, Map.class);
        spec.setContent(intent);
        tabHost.addTab(spec);


        // Information tab
        spec = tabHost.newTabSpec("Information");
        spec.setIndicator("Information");
        intent = new Intent(this, AddNewRecord.class);
        spec.setContent(intent);
        //   tabHost.addTab(spec);

        // News tab
        spec = tabHost.newTabSpec("news");
        spec.setIndicator("News");
        intent = new Intent(this, News.class);
        spec.setContent(intent);
        //   tabHost.addTab(spec);

        // Forecast tab
        spec = tabHost.newTabSpec("Forecast");
        spec.setIndicator("Forecast");
        intent = new Intent(this, Forecast.class);
        spec.setContent(intent);
        //  tabHost.addTab(spec);

        tabHost.setCurrentTab(1);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // display the name of the tab whenever a tab is changed
                Toast.makeText(getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
