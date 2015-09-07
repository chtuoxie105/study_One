package com.example.newsclient.activity.main;

import com.example.newsclient.slidingmenu.activity.RequestManager;

import android.app.Application;

/**
 * 实例化volley中的RequestQueue和ImageLoader，
 * 设置成单例模式，在androidManifest.xml中声明此类，会最先执行
 */
public class MyApplication extends Application {
	public void onCreate() {
		super.onCreate();
		RequestManager.init(this);
	}
}
