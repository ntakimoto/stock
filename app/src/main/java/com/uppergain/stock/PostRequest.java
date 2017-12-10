package com.uppergain.stock;

import android.support.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by pcuser on 2017/11/20.
 */

public class PostRequest extends Request {
    private final Response.Listener mListener;
    private Map<String, String> mParams;

    public PostRequest(int method, String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * リークエストのパラメーターを設定する
     * @param map
     */
    public void setParams(Map<String, String> map) {
        mParams = map;
    }

    @Override
    protected Map<String, String> getParams() {
        return mParams;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        String resp = new String(networkResponse.data);
        JSONObject resultJson;
        try {
            resultJson = new JSONObject(resp);
        } catch (Exception e) {
            return null;
        }
        return Response.success(resultJson, getCacheEntry());
    }

    @Override
    protected void deliverResponse(Object response) {

    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}