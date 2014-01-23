/*
 * Copyright 2013 Ognyan Bankov
 * 
 * Source from: https://github.com/ogrebgr/android_volley_examples
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.DispatcherListener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T> extends Request<T> {
    protected final Gson mGson;
    private final Class<T> mClazz;
    private final Listener<T> mListener;


    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
//        super(method, url, errorListener);
    	super(method, url, errorListener, listener, null);
        this.mClazz = clazz;
        this.mListener = listener;
        mGson = new Gson();
    }
    
    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener, DispatcherListener dispatcherListener) {
//      super(method, url, errorListener);
    	super(method, url, errorListener, listener, dispatcherListener);
    	this.mClazz = clazz;
    	this.mListener = listener;
    	mGson = new Gson();
  }

    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener, Gson gson) {
//      super(method, url, errorListener, null);
    	super(method, url, errorListener, listener, null);
    	this.mClazz = clazz;
    	this.mListener = listener;
    	mGson = gson;
  }

    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener, DispatcherListener dispatcherListener, Gson gson) {
//        super(method, url, errorListener, null);
    	super(method, url, errorListener, listener, dispatcherListener);
        this.mClazz = clazz;
        this.mListener = listener;
        mGson = gson;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {    	
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

}
