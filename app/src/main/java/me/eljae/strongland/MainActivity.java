package me.eljae.strongland;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.maps.*;

public class MainActivity extends AppCompatActivity
{
    private static Button btn_forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btn_forecast = (Button)findViewById(R.id.btn_forecast);

        btn_forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Forecast.class);
                startActivity(i);
            }
        });

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
