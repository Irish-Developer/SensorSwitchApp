package com.project.youcef.sensorswitchapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*************************************************
 * The code for tis class has been provided by my lecturer Dominic Carr.
 *
 * Title: Queue.java
 * Name: Dr Dominic Carr
 * email: Dominic.Carr@ncirl.ie
 *
 *************************************************/

public class MyJSONRequest extends JsonObjectRequest{

    public MyJSONRequest(int method, String url, JSONObject jsonRequest,
                             Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        // here you can write a custom retry policy
        return super.getRetryPolicy();
    }
}
