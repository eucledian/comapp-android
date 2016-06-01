package com.eucledian.comapp.root.markers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.eucledian.comapp.App;
import com.eucledian.comapp.R;
import com.eucledian.comapp.dao.AppUserMarkerDataSource;
import com.eucledian.comapp.dao.ZoneDataSource;
import com.eucledian.comapp.model.AppUserMarker;
import com.eucledian.comapp.model.Zone;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.util.ArrayList;

@EActivity(R.layout.activity_app_user_marker)
public class AppUserMarkerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private double lat;
    private double lng;

    private Zone zone;

    @Bean
    protected App app;

    @Bean
    protected AppUserMarkerDataSource dao;

    @Bean
    protected ZoneDataSource zDao;

    @ViewById
    protected Spinner spinner;

    @ViewById
    protected TextView latitudeText;

    @ViewById
    protected TextView longitudeText;

    private AppUserMarker marker;

    @AfterViews
    protected void init(){
        Bundle args = getIntent().getExtras();
        marker = dao.fromArgs(args);
        initText();
        initSpinner();
    }

    private void initText(){
        latitudeText.setText(Double.toString(marker.getLat()));
        longitudeText.setText(Double.toString(marker.getLng()));
    }

    private void initSpinner(){
        zDao.open();
        ArrayList<Zone> list = zDao.getElements();
        zDao.close();
        ArrayAdapter<Zone> adapter = new ArrayAdapter<Zone>(this, android.R.layout.simple_list_item_1, list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        zone = (Zone) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        zone = null;
    }

    @Click
    protected void submitBtn(){
        if(zone != null){
            marker.setZoneId(zone.getId());
            dao.open();
            dao.insertElement(marker);
            dao.close();
            app.toast(getString(R.string.database_success));
            app.startRootActivity(this);
        }
    }
}
