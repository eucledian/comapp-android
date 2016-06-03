package com.eucledian.comapp.root.markers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eucledian.comapp.App;
import com.eucledian.comapp.Config;
import com.eucledian.comapp.R;
import com.eucledian.comapp.dao.AppUserMarkerDataSource;
import com.eucledian.comapp.dao.MarkerDataSource;
import com.eucledian.comapp.model.AppUserMarker;
import com.eucledian.comapp.model.Marker;
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
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_app_user_marker_map)
public class AppUserMarkerMapActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private GoogleApiClient client;
    private GoogleMap map;
    private Marker appMarker;
    private com.google.android.gms.maps.model.Marker marker;
    private Location location = null;
    private boolean isConnected = true;

    private final int LOCATION_PERMS = 100;

    @Bean
    protected App app;

    @Bean
    protected MarkerDataSource dao;

    @Bean
    protected AppUserMarkerDataSource aumDao;

    @FragmentById
    protected MapFragment mapFragment;

    @ViewById
    protected LinearLayout noMapView;

    @ViewById
    protected TextView latitudeText;

    @ViewById
    protected TextView longitudeText;

    @ViewById
    protected TextView accuracyText;


    @AfterViews
    protected void init() {
        appMarker = dao.fromArgs(getIntent().getExtras());
        initClient();
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            mapFragment.getMapAsync(this);
        }else{
            client.connect();
            getFragmentManager().beginTransaction().hide(mapFragment).commit();
            noMapView.setVisibility(View.VISIBLE);
        }
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
            if (location != null) setPlace(location);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onStop() {
        client.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setPlace(Location location) {
        this.location = location;
        if(isConnected){
            LatLng place = new LatLng(location.getLatitude(), location.getLongitude());
            marker = map.addMarker(new MarkerOptions()
                    .draggable(true)
                    .position(place));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 14));
        }else{
            latitudeText.setText(String.valueOf(location.getLatitude()));
            longitudeText.setText(String.valueOf(location.getLongitude()));
            accuracyText.setText(String.valueOf(location.getAccuracy()) + 'm');
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        client.connect();
    }

    @Click
    protected void locationBtn(){
        AppUserMarker aum = new AppUserMarker();
        aum.setMarkerId(appMarker.getId());
        if(marker != null){
            LatLng pos = marker.getPosition();
            aum.setLat(pos.latitude);
            aum.setLng(pos.longitude);
        }else if(marker == null && location != null){
            aum.setLat(location.getLatitude());
            aum.setLng(location.getLongitude());
        }
        Bundle args = aumDao.toArgs(aum);
        app.startAppUserMarkerActivity(this, args);
    }
}
