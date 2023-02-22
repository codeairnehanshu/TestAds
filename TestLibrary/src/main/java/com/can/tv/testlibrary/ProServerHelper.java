package com.can.tv.testlibrary;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ProServerHelper extends AsyncTask<String, Void, String> {

    public static final String TAG = "ProServerHelper";
    ResponseListener listener;
    Context context;
    Map<String, String> data;
    int requestMethod;
    byte[] img;

    public ProServerHelper(Context context, int requestMethod, ResponseListener listener, Map<String, String> data) {
        this.context = context;
        this.requestMethod = requestMethod;
        this.listener = listener;
        this.data = data;
    }

    @Override
    protected String doInBackground(String... strings) {
        RequestQueue queue = Volley.newRequestQueue(context);
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(requestMethod, strings[0], new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                    String resultResponse = new String(response.data);
                    onPostExecute(resultResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String res = "{\"status\": false,\"message\": \"Forbidden\"}";

                Log.e(TAG,"Error Found : "+error.getMessage());
                onPostExecute(res);

                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res1 = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res1);
                        Log.e(TAG, "onErrorResponse: " + res1);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        Log.e(TAG, "onErrorResponse: " + e2.getLocalizedMessage() );
                        e2.printStackTrace();
                    }
                }

                Log.e(TAG, "onErrorResponse: " + error.getCause());
//                Log.e(TAG, "Error:" + networkResponse.data.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                return data;
            }


//            @Override
//            protected Map<String, DataPart> getByteData() {
//                Map<String, DataPart> params = new HashMap<>();
//                // file name could found file base or direct access from real path
//                // for now just get bitmap data from ImageView
//
//                if (img != null) {
//                    params.put(Constant.PROFILE_PHOTO, new DataPart("post.jpg", img, "image/jpeg"));
//                }
//                return params;
//            }
        };
        multipartRequest.setShouldCache(false);
        queue.add(multipartRequest);
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            listener.processFinish(result);
        }
    }
}
