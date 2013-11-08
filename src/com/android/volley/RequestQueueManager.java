/*
 * Copyright (C) 2013 Mateusz Mlodawski
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

package com.android.volley;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.android.volley.Request.Priority;
import com.android.volley.toolbox.Volley;

/**
 * Simple singleton for accessing {@link RequestQueue} 
 *
 */
public final class RequestQueueManager {
	private static RequestQueueManager mInstance;
	
	private static RequestQueue mRequestQueue;
	
	private static List<String> mCriticalRequests;
	
	private RequestQueueManager(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		mCriticalRequests = new ArrayList<String>();
	}
	
	
	/**
     * Creates a default instance of this singleton
     * 
     * @param context A {@link Context} to use for creating the cache dir.
     */
	public static synchronized void initialize(Context context) {
		if (mInstance == null) {
			mInstance = new RequestQueueManager(context);
		}
	}
	
	/**
	 * @return {@link RequestQueueManager} instance.
	 */
	public static RequestQueueManager getInstance() {
		if (mInstance == null) {
			throw new IllegalStateException("getInstance called before initialize");
		}
		
		return mInstance;
	}

	/**
	 * @return {@link RequestQueue} 
	 */
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}


	/**
	 * @return List of CRITICAL {@link Priority} requests.
	 */
	public List<String> getCriticalRequests() {
		return mCriticalRequests;
	}

}
