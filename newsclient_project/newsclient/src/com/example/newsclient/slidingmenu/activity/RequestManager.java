/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package com.example.newsclient.slidingmenu.activity;

import java.io.File;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class RequestManager {
	private static String DEFAULT_CACHE_DIR_PIC = "pic";
	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	private RequestManager() {
		// no instances
	}

	public static void init(Context context) {
		File cacheDir = null;
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			// cacheDir 缓存路径 /mnt/sdcard/Android/data/<pkg name>/cache/<name>
			// fileDir 缓存路径 /mnt/sdcard/Android/data/<pkg name>/files/<name>
			cacheDir = new File(context.getExternalCacheDir(),DEFAULT_CACHE_DIR_PIC);
		} else {
			// cacheDir 缓存路径 /data/data/<pkg name>/cache/<name>
			cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR_PIC);
		}

		mRequestQueue = Volley.newRequestQueue(cacheDir);

		int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		// Use 1/8th of the available memory for this memory cache.
		int cacheSize = 1024 * 1024 * memClass / 8;
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public static void addRequest(Request<?> request, Object tag) {
		if (tag != null) {
			request.setTag(tag);
		}
		mRequestQueue.add(request);
	}

	public static void cancelAll(Object tag) {
		mRequestQueue.cancelAll(tag);
	}

	/**
	 * Returns instance of ImageLoader initialized with {@see FakeImageCache}
	 * which effectively means that no memory caching is used. This is useful
	 * for images that you know that will be show only once.
	 * 
	 * @return
	 */
	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}
}
