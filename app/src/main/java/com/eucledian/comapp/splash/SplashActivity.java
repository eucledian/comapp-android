package com.eucledian.comapp.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.eucledian.comapp.App;
import com.eucledian.comapp.R;

import org.androidannotations.annotations.AfterViews;
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
    protected Button loginBtn;

    @AfterViews
    protected void init(){
        app.initAuthToken(this);
        if(app.hasSession()){
            app.startRootActivity(this);
        }
        else{
            showLoginBtn();
        }
    }

    private void showLoginBtn(){
        loginBtn.setVisibility(View.VISIBLE);
    }

    @Click
    protected void loginBtnClicked() {
        Intent intent = new Intent(this, LoginActivity_.class);
        startActivity(intent);
    }

}
