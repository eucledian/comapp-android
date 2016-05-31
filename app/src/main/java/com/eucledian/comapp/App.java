package com.eucledian.comapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.eucledian.comapp.dao.AppUserDataSource;
import com.eucledian.comapp.dao.TokenDataSource;
import com.eucledian.comapp.model.AppUser;
import com.eucledian.comapp.model.Token;
import com.eucledian.comapp.root.RootActivity_;
import com.eucledian.comapp.splash.LoginActivity_;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Map;

@EBean(scope = EBean.Scope.Singleton)
public class App {

    public static final int DEVICE_TYPE = 4;

    @RootContext
    protected Context context;

    @Bean
    protected ApiClient client;

    @Bean
    protected TokenDataSource dao;

    @Bean
    protected AppUserDataSource uDao;

    private Token authToken;
    private AppUser appUser;
    private ObjectMapper mapper;
    private String notificationToken;

    public void authError(Activity activity) {
        logout(activity);
        String msg = context.getString(R.string.auth_error);
        showToast(context, msg);
    }

    public void initAuthToken(Context context) {
        if(this.context == null) this.context = context;
        try {
            dao.open();
            Token authToken = dao.getToken();
            dao.close();
            if (authToken == null) {
                clearSession();
            } else {
                uDao.open();
                AppUser user = uDao.getUser();
                uDao.close();
                if (user != null) {
                    setAuthToken(authToken);
                    setAppUser(user);
                }
            }
        } catch (SQLException e) {
            clearSession();
            databaseError();
        } finally {
            dao.close();
            uDao.close();
        }
    }

    /**
     * Remove all session related objects from internal db and
     * set all objects tu null.
     */
    public void logout(Activity context) {
        try {
            dao.open();
            uDao.open();
            uDao.deleteUsers();
            dao.deleteTokens();
            uDao.close();
            dao.close();
            clearSession();
            startMainActivity(context);
        } catch (SQLException e) {
            clearSession();
            databaseError();
        } finally {
            dao.close();
            uDao.close();
        }
    }

    private void clearSession() {
        setAppUser(null);
        setAuthToken(null);
    }

    public void get(String path, Map<String, String> params, ApiClientResponse handler, Activity context){
        client.get(path, params, handler, context);
    }

    public void post(String path, Map<String, String> params, ApiClientResponse handler, Activity context) {
        client.post(path, params, handler, context);
    }

    public boolean hasAppPermission(Context context, String permission){
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestAppPermission(Activity context, String permission, int activityResult){
        ActivityCompat.requestPermissions(context, new String[]{permission}, activityResult);
    }

    public ImageLoader imageLoader() {
        return client.getImageLoader();
    }

    public String getImageUrl(String path) {
        return client.getImageUrl(path);
    }

    public String getDeviceId() {
        String uuid;
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        uuid = manager.getDeviceId();
        if (uuid == null || uuid.length() == 0) {
            uuid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return uuid;
    }

    public void hideKeyboard(Activity context){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = context.getCurrentFocus();

        if(view == null){
            view = new View(context);
        }

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
    public void startSessionActivity(Activity ctx) {
        SharedPreferences sharedPreferences = ctx.getPreferences(Context.MODE_PRIVATE);
        String sharedPreferencesKey = ctx.getString(R.string.shared_preferences_on_boarding_key);
        boolean viewedOnBoarding = sharedPreferences.getBoolean(sharedPreferencesKey, false);

        if(viewedOnBoarding){
            startRootActivity(ctx);
        }else{
            sharedPreferences.edit().putBoolean(sharedPreferencesKey, true).apply();
            startOnBoardingActivity(ctx);
        }
    }

    public void setSharedPreference(Activity ctx) {
        SharedPreferences sharedPref = ctx.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(ctx.getString(R.string.shared_preferences_on_boarding_key), true);
        editor.commit();
    }*/

    // Toast messages
    public void toast(String s) {
        showToast(context, s);
    }

    public void invalidLogin() {
        showToast(context, context.getString(R.string.login_invalid));
    }

    public void databaseError() {
        showToast(context, context.getString(R.string.database_error));
    }

    public void serverError() {
        showToast(context, context.getString(R.string.server_error));
    }

    public void databaseSuccess() {
        showToast(context, context.getString(R.string.database_success));
    }

    public static void showLongToast(Context c, CharSequence text) {
        Context ctx = c.getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(ctx, text, duration);
        toast.show();
    }

    public static void showToast(Context c, CharSequence text) {
        Context ctx = c.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(ctx, text, duration);
        toast.show();
    }

    public void startMainActivity(Activity context){
        Intent intent = new Intent(context, LoginActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear back stack
        context.startActivity(intent);
    }


    public void startRootActivity(Activity context){
        Intent intent = new Intent(context, RootActivity_.class);
        context.startActivity(intent);
    }

    public boolean hasSession() {
        return getAuthToken() != null;
    }

    public Token getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(Token authToken) {
        this.authToken = authToken;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser user) {
        this.appUser = user;
    }

    public Context getContext() {
        return context;
    }

    public ObjectMapper getObjectMapper() {
        if (mapper == null) {
            // TODO micro configure mapper
            mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
        return mapper;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }
}
