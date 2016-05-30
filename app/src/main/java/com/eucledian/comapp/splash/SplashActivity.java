package com.eucledian.comapp.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

import com.eucledian.comapp.App;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    @Bean
    protected App app;

    @ViewById
    protected LinearLayout splashActions;

    @Click
    protected void loginBtnClicked() {
        Intent intent = new Intent(this, LoginActivity_.class);
        startActivity(intent);
    }

}
