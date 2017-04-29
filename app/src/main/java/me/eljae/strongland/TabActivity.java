package me.eljae.strongland;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.io.IOException;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import me.eljae.strongland.db.DataAdapter;

public class TabActivity extends AppCompatActivity
{

    private int PLACE_PICKER_REQUEST = 1;
    private EditText country, lat, lng, nearestLoc, population;
    Spinner input_landslideTypes, input_landslideSize, input_triggerTypes;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "A8oAAbDaPBwd0x07smGlCydeP";
    private static final String TWITTER_SECRET = "00vCAlrEk5J7skT0drIvtEP6FOcpz63mPOU3zKcrduIY6LRfUJ";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_tab);

        DataAdapter dataAdapter = new DataAdapter(this);
        dataAdapter.createDatabase();




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openPicker(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                nearestLoc = (EditText) findViewById(R.id.input_nearestLoc);
                country = (EditText) findViewById(R.id.input_country);
                lat = (EditText) findViewById(R.id.input_latitude);
                lng = (EditText) findViewById(R.id.input_longitude);
                population = (EditText) findViewById(R.id.input_population);

                Place place = PlacePicker.getPlace(data, this);
                nearestLoc.setText(place.getAddress());
                country.setText(getCountry(place));
                lat.setText(String.valueOf(place.getLatLng().latitude));
                lng.setText(String.valueOf(place.getLatLng().longitude));
            }
        }
    }

    public void submitReport(View view) {

//        nearestLoc = (EditText) findViewById(R.id.input_nearestLoc);
//        country = (EditText) findViewById(R.id.input_country);
//        lat = (EditText) findViewById(R.id.input_latitude);
//        lng = (EditText) findViewById(R.id.input_longitude);
//        population = (EditText) findViewById(R.id.input_population);

        /**
         * SPINNER : LANDSLIDE TYPE
         */
        input_landslideTypes = (Spinner) findViewById(R.id.input_landslideTypes);

        ArrayAdapter<CharSequence> adapter_landslides = ArrayAdapter.createFromResource(this,
                R.array.landslides, android.R.layout.simple_spinner_item);

        adapter_landslides.setDropDownViewResource(android.R.layout.simple_spinner_item);
        input_landslideTypes.setAdapter(adapter_landslides);

        /**
         * SPINNER : LANDSLIDE SIZE
         */
        input_landslideSize = (Spinner) findViewById(R.id.input_landslideSizes);

        ArrayAdapter<CharSequence> adapter_landslideSizes = ArrayAdapter.createFromResource(this,
                R.array.landslide_sizes, android.R.layout.simple_spinner_item);

        adapter_landslideSizes.setDropDownViewResource(android.R.layout.simple_spinner_item);
        input_landslideSize.setAdapter(adapter_landslideSizes);

        /**
         * SPINNER : TRIGGER TYPE
         */
        input_triggerTypes = (Spinner) findViewById(R.id.input_triggerTypes);

        ArrayAdapter<CharSequence> adapter_triggers = ArrayAdapter.createFromResource(this,
                R.array.triggers, android.R.layout.simple_spinner_item);

        adapter_landslides.setDropDownViewResource(android.R.layout.simple_spinner_item);
        input_triggerTypes.setAdapter(adapter_triggers);

        DataAdapter dataAdapter = new DataAdapter(this);

        dataAdapter.open();

        if (allInputFilled(country.getText().toString(), lat.getText().toString(), lng.getText().toString(),
                nearestLoc.getText().toString(), input_triggerTypes.getSelectedItem().toString(),
                input_landslideSize.getSelectedItem().toString(), input_landslideTypes.getSelectedItem().toString(), population.getText().toString())) {
            LatLng latLng = new LatLng(Double.parseDouble(String.valueOf(lat.getText())), Double.parseDouble(String.valueOf(lng.getText())));

            if (dataAdapter.saveCrowdSourcingData(country.getText().toString(), latLng,
                    nearestLoc.getText().toString(), input_triggerTypes.getSelectedItem().toString(),
                    input_landslideSize.getSelectedItem().toString(), input_landslideTypes.getSelectedItem().toString(), population.getText().toString())) {
                Toast.makeText(this, "Report Submitted", Toast.LENGTH_LONG).show();

                nearestLoc.setText("");
                country.setText("");
                lat.setText("");
                lng.setText("");
                population.setText("");

                input_landslideSize.setSelection(0);
                input_landslideTypes.setSelection(0);
                input_triggerTypes.setSelection(0);
            }
        } else {
            Toast.makeText(this, "Please fill all the input",
                    Toast.LENGTH_LONG).show();
        }

        dataAdapter.getTestData();

    }

    public String getCountry(Place place) {
        Geocoder geocoder = new Geocoder(this);
        String country = "Unable to get country";

        try {
            List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
            country = addresses.get(0).getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return country;
    }

    public boolean allInputFilled(String country, String lat, String lng, String nearest,
                                  String ls_trigger, String ls_size, String ls_type, String population) {

        return !(country.isEmpty() || lat.isEmpty() || lng.isEmpty() ||
                nearest.isEmpty() || ls_size.isEmpty() ||
                ls_trigger.isEmpty() || ls_type.isEmpty() || population.isEmpty());

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment()
        {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    Map map = new Map();
                    return map;
                case 1:
                    AddNewRecord record = new AddNewRecord();
                    return record;
                case 2:
                    News news = new News();
                    //return news;
                case 3:
                    Forecast forecast = new Forecast();
                    return forecast;
            }

            return null;
        }

        @Override
        public int getCount()
        {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "Record";
                case 2:
                    return "News";
                case 3:
                    return "Forecast";
            }
            return null;
        }
    }
}
