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
        setContentView(R.layout.activity_add_new_record);

        final String DEFAULT_COUNTRY = "Malaysia";

        /**
         * SPINNER : COUNTRY
         */
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;

        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }

        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        Spinner citizenship = (Spinner) findViewById(R.id.input_country);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        citizenship.setAdapter(adapter);

        int spinner_pos = adapter.getPosition(DEFAULT_COUNTRY);
        citizenship.setSelection(spinner_pos);

        /**
         * SPINNER : HAZARD TYPE
         */
        ArrayList<String> hazardTypes = new ArrayList<String>();
        hazardTypes.add("Landslide");

        Spinner input_hazard = (Spinner) findViewById(R.id.input_hazardType);
        ArrayAdapter<String> hazard_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hazardTypes);
        input_hazard.setAdapter(hazard_adapter);


    }
}
