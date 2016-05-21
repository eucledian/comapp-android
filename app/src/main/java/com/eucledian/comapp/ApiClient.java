package com.eucledian.comapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.eucledian.comapp.model.Token;
import com.eucledian.comapp.util.net.JacksonRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@EBean(scope = EBean.Scope.Singleton)
public class ApiClient {

    public static final class Header {
        public static final String AuthToken = "Auth-Token";
        public static final String AuthSecret = "Auth-Secret";
    }

    public static final class Params {
        public static final String NOTIFICATION_TOKEN = "notification_token";
        public static final class Device{
            public static final String Identity = "device[identity]";
            public static final String UUID = "device[uuid]";
            public static final String Vendor = "device[vendor_name]";
            public static final String Name = "device[name]";
        }
        //public static final String Lang = "lang";
    }

    private String url;
    private String asset_url;
    private RequestQueue queue;
    private ImageLoader imageLoader;

    @RootContext
    protected Context context;

    @Bean
    protected App app;

    @AfterInject
    protected void init(){
        initRequestQueue();
        initUrl();
    }

    private void initRequestQueue(){
        this.queue = Volley.newRequestQueue(this.context);
    }

    private void initUrl(){
        this.url = Config.Server.ENDPOINT + ':' + Config.Server.PORT + Config.Server.SCOPE + Config.Server.CONTROLLER;
        this.asset_url = Config.Server.ENDPOINT + ':' + Config.Server.PORT + Config.Server.SCOPE + Config.Server.IMAGE;
    }

    public Map<String, String> getHeaders(){
        Map<String, String> headers = new HashMap<>();
        Token token = app.getAuthToken();
        if(token != null){
            headers.put(Header.AuthToken, token.getToken());
            headers.put(Header.AuthSecret, token.getSecret());
        }
        return headers;
    }

    public Map<String, String> getParams(Map<String, String> params) {
        params.put(Params.Device.Identity, String.valueOf(App.DEVICE_TYPE));
        params.put(Params.Device.UUID, getApp().getDeviceId());
        params.put(Params.Device.Vendor, android.os.Build.MANUFACTURER);
        params.put(Params.Device.Name, android.os.Build.MODEL);
        params.put(Params.NOTIFICATION_TOKEN, getApp().getNotificationToken());
        return params;
    }

    public String getUrl(String path){
        return this.url + path;
    }

    public String getImageUrl(String path){
        return this.asset_url + '/' + path;
    }

    public String getUrl(String path, Map<String, String> params){
        String url = getUrl(path);
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();

        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
            iterator.remove(); // avoids a ConcurrentModificationException
        }

        return uriBuilder.build().toString();
    }

    public void get(String path, Map<String, String> params, ApiClientResponse handler, Activity context) {
        params = getParams(params);

        new GetApiHandler(getUrl(path, params), params, handler, false, context);
    }

    private final class GetApiHandler{
        private ApiClientResponse handler;
        private Map<String, String> params;
        private String url;
        private boolean isSecure;
        private Activity context;

        public GetApiHandler(String url, Map<String, String> params, ApiClientResponse handler, boolean isSecure, Activity context){
            this.context = context;
            this.handler = handler;
            this.params = params;
            this.url = url;
            // TODO isSecure
            this.isSecure = isSecure;
            queueRequest();
        }

        public void queueRequest(){
            handler.onLoading();
            JacksonRequest request = JacksonRequest.get(url, getHeaders(), params, new Response.Listener<ObjectNode>() {
                @Override
                public void onResponse(ObjectNode response) {
                    handler.onLoaded();
                    handler.onSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String err_msg = error.getLocalizedMessage();
                    if (err_msg != null) Log.e(Config.LOG_TAG, err_msg);
                    if (error instanceof AuthFailureError)
                        getApp().authError(context);
                    handler.onLoaded();
                    handler.onError(error);
                }
            }, getApp().getObjectMapper());
            getRequestQueue().add(request);
        }
    }

    public void post(String path, Map<String, String> params, ApiClientResponse handler, Activity context){
        new PostApiHandler(getUrl(path), getParams(params), handler, false, context);
    }

    private final class PostApiHandler{
        private ApiClientResponse handler;
        private Map<String, String> params;
        private String url;
        private boolean isSecure;
        private Activity context;

        public PostApiHandler(String url, Map<String, String> params, ApiClientResponse handler, boolean isSecure, Activity context){
            this.context = context;
            this.handler = handler;
            this.params = params;
            this.url = url;
            // TODO isSecure
            this.isSecure = isSecure;
            queueRequest();
        }

        public void queueRequest(){
            handler.onLoading();
            JacksonRequest request = JacksonRequest.post(url, getHeaders(), params, new Response.Listener<ObjectNode>() {
                @Override
                public void onResponse(ObjectNode response) {
                    handler.onLoaded();
                    handler.onSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String err_msg = error.getLocalizedMessage();
                    if (err_msg != null) Log.e(Config.LOG_TAG, err_msg);
                    if (error instanceof AuthFailureError)
                        getApp().authError(context);
                    handler.onLoaded();
                    handler.onError(error);
                }
            }, getApp().getObjectMapper());
            getRequestQueue().add(request);
        }
    }

    public App getApp() {
        return app;
    }

    public RequestQueue getRequestQueue(){ return queue; }


    public ImageLoader getImageLoader(){
        if(imageLoader == null){
            imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
                public void putBitmap(String url, Bitmap bitmap) {
                    mCache.put(url, bitmap);
                }
                public Bitmap getBitmap(String url) {
                    return mCache.get(url);
                }
            });
        }
        return imageLoader;
    }
}
