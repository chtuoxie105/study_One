package com.example.newsclient.slidingmenu.activity;

import com.example.newsclient.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class MoreImgActivity extends Activity {
	private ViewPager viewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_img_viewpager_layout);
	}
}
