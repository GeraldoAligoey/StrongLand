package me.eljae.strongland;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        // Main tab
        TabHost.TabSpec spec = tabHost.newTabSpec("Main");
        spec.setContent(R.id.mainTab);
        spec.setIndicator("Main");
        tabHost.addTab(spec);

        // Information tab
        spec = tabHost.newTabSpec("Information");
        spec.setContent(R.id.infoTab);
        spec.setIndicator("Information");
        tabHost.addTab(spec);

        // News tab
        spec = tabHost.newTabSpec("News");
        spec.setContent(R.id.newsTab);
        spec.setIndicator("News");
        tabHost.addTab(spec);

        // Forecast tab
        spec = tabHost.newTabSpec("Forecast");
        spec.setContent(R.id.forecastTab);
        spec.setIndicator("Forecast");
        tabHost.addTab(spec);
    }
}
