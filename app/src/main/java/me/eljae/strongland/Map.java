package me.eljae.strongland;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.eljae.strongland.db.DataAdapter;
import me.eljae.strongland.model.CustomData;

public class Map extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    List<CustomData> data;
    List<Marker> markers;
    TextView loc, tstamp, scale, trigger, type, total_data;
    DataAdapter dataAdapter;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);

            loc = (TextView) getView().findViewById(R.id.location);
            tstamp = (TextView) getView().findViewById(R.id.timestamp);
            scale = (TextView) getView().findViewById(R.id.scale);
            trigger = (TextView) getView().findViewById(R.id.trigger);
            type = (TextView) getView().findViewById(R.id.type);
            total_data = (TextView) getView().findViewById(R.id.total_data);

            dataAdapter = new DataAdapter(this.getContext());
            dataAdapter.createDatabase();
            dataAdapter.open();

            data = dataAdapter.getMarkerData();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnMarkerClickListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(3.15787, 101.71206), 7));

        markers = new ArrayList<>();

        for (CustomData i : data) {
            markers.add(createMarker(Double.parseDouble(i.getLat()), Double.parseDouble(i.getLng()), i.getCountry(), i.getAddress()));
        }
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet) {

        return mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));
//                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int i = markers.indexOf(marker);

        data = dataAdapter.getMarkerData();

        loc.setText("Exact location: " + data.get(i).getAddress());
        tstamp.setText("Timestamp: " + data.get(i).getTimestamp());
        scale.setText("Landslide scale: " + data.get(i).getScale());
        trigger.setText("Landslide trigger: " + data.get(i).getTrigger());
        type.setText("Landslide type: " + data.get(i).getType());


        total_data.setText("Current Total Data: " + data.size());

        return true;
    }
}
