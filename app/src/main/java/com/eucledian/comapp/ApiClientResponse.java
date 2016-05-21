package com.eucledian.comapp;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ApiClientResponse {

    void onLoading();
    void onLoaded();
    void onSuccess(ObjectNode response);
    void onError(VolleyError e);

}
