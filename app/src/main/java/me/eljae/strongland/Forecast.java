package me.eljae.strongland;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DecimalFormat;

import static java.lang.Math.round;

public class Forecast extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static DataPoint historical;
    private static DataPoint current_mte;
    private static GraphView graph;
    private static TextView tv_prediction;

    DataPoint empty1 = new DataPoint(1, 0);
    DataPoint empty2 = new DataPoint(3, 0);

    private void drawGraph(DataPoint historical, DataPoint current_mte){
        graph = (GraphView)findViewById(R.id.graph);
        graph.removeAllSeries();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                historical,current_mte
        });
        graph.addSeries(series);

        series.setSpacing(20);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxX(4);
        graph.getViewport().setMaxY(400);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"", "Historical Precip.", "", "Month-to-End precip.", ""});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        if(historical != empty1 && current_mte != empty2){
            double chance = ((current_mte.getY() - historical.getY()) / historical.getY() * 100);
            String raise_fell = "raised";
            String probability="";
            if (chance < 0){
                probability = "very low";
                raise_fell = "fell";
                chance = -chance;
                tv_prediction.setTextColor(Color.GREEN);
            } else if(chance > 0 && chance <= 20){
                probability = "fairly low";
                tv_prediction.setTextColor(Color.GREEN);
            } else if(chance > 20 && chance < 50){
                probability = "medium";
                tv_prediction.setTextColor(Color.BLACK);
            } else if(chance == 50){
                probability = "50/50";
                tv_prediction.setTextColor(Color.BLACK);
            } else if(chance > 50 && chance < 80){
                probability = "fairly high";
                tv_prediction.setTextColor(Color.RED);
            } else if(chance >= 80){
                probability = "extremely high";
                tv_prediction.setTextColor(Color.RED);
            }
            DecimalFormat df = new DecimalFormat("####0.00");
            String stmt = "Average rainfall " + raise_fell + " by " + df.format(chance) + "% compared to last year.";
            stmt += "\nChance of landslide is " + probability + ".";
            tv_prediction.setText(stmt);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        tv_prediction = (TextView)findViewById(R.id.tv_prediction);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        //create an array adapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array, android.R.layout.simple_spinner_dropdown_item);
        //specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        historical = new DataPoint(1, 0);
        current_mte = new DataPoint(3, 0);

        drawGraph(historical, current_mte);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getItemAtPosition(pos).toString()) {
            case "Kuala Lumpur, Malaysia": {
                DataPoint temp1 = new DataPoint(1, 276.9);
                DataPoint temp2 = new DataPoint(3, 149.6);
                drawGraph(temp1, temp2);
                break;
            }
            case "Kamloops, Canada": {
                DataPoint temp1 = new DataPoint(1, 15.2);
                DataPoint temp2 = new DataPoint(3, 41.4);
                drawGraph(temp1, temp2);
                break;
            }
            case "Osaka, Japan": {
                DataPoint temp1 = new DataPoint(1, 134.6);
                DataPoint temp2 = new DataPoint(3, 222.5);
                drawGraph(temp1, temp2);
                break;
            }
            case "Tokyo, Japan": {
                DataPoint temp1 = new DataPoint(1, 124.5);
                DataPoint temp2 = new DataPoint(3, 147.3);
                drawGraph(temp1, temp2);
                break;
            }
            case "Itanagar, India": {
                DataPoint temp1 = new DataPoint(1, 200);
                DataPoint temp2 = new DataPoint(3, 320.3);
                drawGraph(temp1, temp2);
                break;
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent){

    }
}
