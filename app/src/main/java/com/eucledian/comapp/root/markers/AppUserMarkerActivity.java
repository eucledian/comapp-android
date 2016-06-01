package com.eucledian.comapp.root.markers;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eucledian.comapp.Config;
import com.eucledian.comapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

@EActivity(R.layout.activity_app_user_marker)
public class AppUserMarkerActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private GoogleApiClient client;
    private GoogleMap map;

    private final int LOCATION_PERMS = 100;

    @FragmentById
    protected MapFragment mapFragment;

    @AfterViews
    protected void init() {
        initClient();
        mapFragment.getMapAsync(this);
    }

    private void initClient() {
        if (client == null) {
            client = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void initFragment() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, new AppUserMarkerMapFragment_()).commit();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMS);
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(client);
        if (location != null) {
            setPlace(location);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_PERMS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            @SuppressWarnings("MissingPermission") Location location = LocationServices.FusedLocationApi.getLastLocation(client);
            Log.d(Config.LOG_TAG, "Pig!!!" + location.toString());
            if (location != null) setPlace(location);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onStart() {
        //client.connect();
        super.onStart();
    }

    protected void onStop() {
        client.disconnect();
        super.onStop();
    }


    public void replaceFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setPlace(Location location) {
        LatLng place = new LatLng(location.getLatitude(), location.getLongitude());
        map.addMarker(new MarkerOptions()
                .draggable(true)
                .position(place));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 14));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        client.connect();
    }
}
