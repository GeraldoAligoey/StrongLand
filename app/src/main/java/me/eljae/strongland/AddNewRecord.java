package me.eljae.strongland;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import me.eljae.strongland.db.DataAdapter;

public class AddNewRecord extends AppCompatActivity {
    private int PLACE_PICKER_REQUEST = 1;
    private EditText country, lat, lng, nearestLoc, population;
    Spinner input_landslideTypes, input_landslideSize, input_triggerTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        DataAdapter dataAdapter = new DataAdapter(this.getApplicationContext());
        dataAdapter.createDatabase();

        nearestLoc = (EditText) findViewById(R.id.input_nearestLoc);
        country = (EditText) findViewById(R.id.input_country);
        lat = (EditText) findViewById(R.id.input_latitude);
        lng = (EditText) findViewById(R.id.input_longitude);
        population = (EditText) findViewById(R.id.input_population);

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
        DataAdapter dataAdapter = new DataAdapter(getApplicationContext());

        dataAdapter.open();


        if (allInputFilled(country.getText().toString(), lat.getText().toString(), lng.getText().toString(),
                nearestLoc.getText().toString(), input_triggerTypes.getSelectedItem().toString(),
                input_landslideSize.getSelectedItem().toString(), input_landslideTypes.getSelectedItem().toString(), population.getText().toString())) {
            LatLng latLng = new LatLng(Double.parseDouble(String.valueOf(lat.getText())), Double.parseDouble(String.valueOf(lng.getText())));

            if (dataAdapter.saveCrowdSourcingData(country.getText().toString(), latLng,
                    nearestLoc.getText().toString(), input_triggerTypes.getSelectedItem().toString(),
                    input_landslideSize.getSelectedItem().toString(), input_landslideTypes.getSelectedItem().toString(), population.getText().toString())) {
                Toast.makeText(this, "Report Submitted",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Please fill all the input",
                    Toast.LENGTH_LONG).show();
        }

        dataAdapter.getTestData();

    }

    public boolean allInputFilled(String country, String lat, String lng, String nearest,
                                  String ls_trigger, String ls_size, String ls_type, String population) {

        if (country.isEmpty() || lat.isEmpty() || lng.isEmpty() ||
                nearest.isEmpty() || ls_size.isEmpty() ||
                ls_trigger.isEmpty() || ls_type.isEmpty() || population.isEmpty()) {
            return false;
        }

        return true;
    }
}
