package com.eucledian.comapp.util.net;

import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.Map;

public class JacksonRequest extends Request<ObjectNode> {

    private Map<String, String> headers;
    private Map<String, String> params;
    private Response.Listener<ObjectNode> listener;
    private ObjectMapper mapper;
    private String requestURL;

    public JacksonRequest(int method, String url, Map<String, String> headers, Map<String, String> params, Response.Listener<ObjectNode> listener, Response.ErrorListener errorListener, ObjectMapper mapper){
        super(method, url, errorListener);
        this.headers = headers;
        this.params = params;
        this.listener = listener;
        this.mapper = mapper;
        this.requestURL = url;
        setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public static JacksonRequest post(String url, Map<String, String> headers, Map<String, String> params, Response.Listener<ObjectNode> listener, Response.ErrorListener errorListener, ObjectMapper mapper){
        return new JacksonRequest(Method.POST, url, headers, params, listener, errorListener, mapper);
    }

    public static JacksonRequest get(String url, Map<String, String> headers, Map<String, String> params, Response.Listener<ObjectNode> listener, Response.ErrorListener errorListener, ObjectMapper mapper){
        return new JacksonRequest(Method.GET, url, headers, params, listener, errorListener, mapper);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    public String getUrl() {
        String url = this.requestURL;

        if (getMethod() == Request.Method.GET) {
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            Uri.Builder uriBuilder = Uri.parse(this.requestURL).buildUpon();
            int i = 1;
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
                iterator.remove(); // avoids a ConcurrentModificationException
                i++;
            }
            url = uriBuilder.build().toString();
        }
        return url;
    }

    @Override
    protected void deliverResponse(ObjectNode response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<ObjectNode> parseNetworkResponse(NetworkResponse response){
        try{
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if(response.statusCode == HttpURLConnection.HTTP_OK || response.statusCode == HttpURLConnection.HTTP_NOT_MODIFIED)
                return Response.success((ObjectNode) mapper.readTree(json), HttpHeaderParser.parseCacheHeaders(response));
            else if(response.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED)
                return Response.error(new AuthFailureError(response));
            else
                return Response.error(new ServerError(response));
        }catch (Exception e) {
            return Response.error(new ParseError(response));
        }
    }

}
