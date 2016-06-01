package com.eucledian.comapp.root.markers;


import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eucledian.comapp.Config;
import com.eucledian.comapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_app_user_marker_map)
public class AppUserMarkerMapFragment extends Fragment implements OnMapReadyCallback {

    private AppUserMarkerActivity activity;
    private GoogleMap map;

    @FragmentById
    protected MapFragment mapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public AppUserMarkerMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d(Config.LOG_TAG, map.toString());
        /**
        if (activity.location != null) {
            LatLng location = new LatLng(activity.location.getLatitude(), activity.location.getLongitude());
            //map.setMyLocationEnabled(true);
            map.addMarker(new MarkerOptions()
                    .draggable(true)
                    .position(location)
                    .title("Marker"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
        }*/
    }

    @Click
    protected void locationBtn(){

    }
}
