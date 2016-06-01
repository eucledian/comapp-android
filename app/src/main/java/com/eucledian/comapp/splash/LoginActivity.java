package com.eucledian.comapp.splash;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.eucledian.comapp.ApiClientResponse;
import com.eucledian.comapp.App;
import com.eucledian.comapp.Config;
import com.eucledian.comapp.R;
import com.eucledian.comapp.dao.AppUserDataSource;
import com.eucledian.comapp.dao.TokenDataSource;
import com.eucledian.comapp.model.AppUser;
import com.eucledian.comapp.model.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gustavo on 5/20/16.
 */

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @Bean
    protected App app;

    @Bean
    protected TokenDataSource tDao;

    @Bean
    protected AppUserDataSource uDao;

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
        app.initAuthToken(this);
        if(app.hasSession()){
            //app.startOnBoardingActivity(this);
        }
    }

    @Click
    protected void loginBtnClicked(){
        Map<String, String> params = new HashMap<>();
        String mail = loginMailText.getText().toString();
        String password = loginPasswordText.getText().toString();
        params.put("app_user[mail]", mail);
        params.put("app_user[password]", password);
        params.put("app_user[merchant_id]", Integer.toString(1));
        app.post(Config.Server.Urls.Users.LOGIN, params, new ApiClientResponse() {
            @Override
            public void onLoading() {
                loading();
            }

            @Override
            public void onLoaded() {}

            @Override
            public void onSuccess(ObjectNode response) {
                success(response);
            }

            @Override
            public void onError(VolleyError e) {
                error();
            }
        }, this);
    }

    private void success(ObjectNode response){
        if(setSession(response)) {
            app.startRootActivity(this);
        }else{
            app.invalidLogin();
            loaded();
        }
    }

    private boolean setSession(ObjectNode response) {
        JsonNode j_token = response.get("token");
        boolean success = false;
        if(!j_token.isNull()){
            try {
                ObjectNode j_user = (ObjectNode)j_token.get("api_user");
                Token t = tDao.getToken((ObjectNode)j_token, app.getObjectMapper());
                AppUser u = uDao.getAppUser(j_user, app.getObjectMapper());
                tDao.open();
                long tmp = tDao.insertToken(t);
                if(tmp != -1) {
                    uDao.open();
                    tmp = uDao.insertUser(u);
                    if(tmp != -1) {
                        login(t, u);
                        success = true;
                    }
                }
                if(!success) throw new SQLiteException(getString(R.string.database_error));
            }catch (JsonProcessingException e){
                error();
            }catch (SQLiteException e){
                app.databaseError();
                tDao.deleteTokens();
                uDao.deleteUsers();
                error();
            }finally {
                tDao.close();
                uDao.close();
            }
        }
        return success;
    }

    private void login(Token t, AppUser u){
        app.setAuthToken(t);
        app.setAppUser(u);
    }

    private void error(){
        loaded();
        app.serverError();
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
