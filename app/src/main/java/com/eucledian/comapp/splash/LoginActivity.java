package com.eucledian.comapp.splash;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eucledian.comapp.App;
import com.eucledian.comapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by gustavo on 5/20/16.
 */

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @Bean
    protected App app;

    @ViewById
    protected ProgressBar loginLoadingView;

    @ViewById
    protected ScrollView loginView;

    @ViewById
    protected TextView loginMailText;

    @ViewById
    protected TextView loginPasswordText;

    @AfterViews
    protected void init(){
        // TODO initialize
    }

    @Click
    protected void loginBtnClicked(){
        // TODO
    }

    private void loading(){
        loginView.setVisibility(View.GONE);
        loginLoadingView.setVisibility(View.VISIBLE);
    }

    private void loaded(){
        loginView.setVisibility(View.VISIBLE);
        loginLoadingView.setVisibility(View.GONE);
    }

}
