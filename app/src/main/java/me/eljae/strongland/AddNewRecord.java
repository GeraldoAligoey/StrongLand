package me.eljae.strongland;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class AddNewRecord extends AppCompatActivity {
    private int PLACE_PICKER_REQUEST = 1;
    private EditText country, lat, lng, nearestLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        nearestLoc = (EditText) findViewById(R.id.input_nearestLoc);
        country = (EditText) findViewById(R.id.input_country);
        lat = (EditText) findViewById(R.id.input_latitude);
        lng = (EditText) findViewById(R.id.input_longitude);

        /**
         * SPINNER : LANDSLIDE TYPE
         */
        Spinner input_landslideTypes = (Spinner) findViewById(R.id.input_landslideTypes);

        ArrayAdapter<CharSequence> adapter_landslides = ArrayAdapter.createFromResource(this,
                R.array.landslides, android.R.layout.simple_spinner_item);
        ;

        adapter_landslides.setDropDownViewResource(android.R.layout.simple_spinner_item);
        input_landslideTypes.setAdapter(adapter_landslides);

        /**
         * SPINNER : LANDSLIDE SIZE
         */
        Spinner input_landslideSize = (Spinner) findViewById(R.id.input_landslideSizes);

        ArrayAdapter<CharSequence> adapter_landslideSizes = ArrayAdapter.createFromResource(this,
                R.array.landslide_sizes, android.R.layout.simple_spinner_item);

        adapter_landslideSizes.setDropDownViewResource(android.R.layout.simple_spinner_item);
        input_landslideSize.setAdapter(adapter_landslideSizes);

        /**
         * SPINNER : TRIGGER TYPE
         */
        Spinner input_triggerTypes = (Spinner) findViewById(R.id.input_triggerTypes);

        ArrayAdapter<CharSequence> adapter_triggers = ArrayAdapter.createFromResource(this,
                R.array.triggers, android.R.layout.simple_spinner_item);
        ;

        adapter_landslides.setDropDownViewResource(android.R.layout.simple_spinner_item);
        input_triggerTypes.setAdapter(adapter_triggers);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                nearestLoc.setText(place.getAddress());
                country.setText(getCountry(place));
                lat.setText(String.format(String.valueOf(place.getLatLng().latitude)));
                lng.setText(String.format(String.valueOf(place.getLatLng().longitude)));
            }
        }
    }

    public void openPicker(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
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

    public void submitReport(View view) {
        Calendar calendar = Calendar.getInstance();

        Toast.makeText(this, "Submitted at : " + String.format(String.valueOf(calendar.getTime())),
                Toast.LENGTH_LONG).show();
    }
}
