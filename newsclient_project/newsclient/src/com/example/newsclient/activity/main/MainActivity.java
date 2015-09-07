package com.example.newsclient.activity.main;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.newsclient.R;
import com.example.newsclient.adapter.ViewpagerAdapter;
import com.example.newsclient.fragment.AllNewsContentFragmentHeadNews;
import com.example.newsclient.fragment.AllNewsContentFragmentMoneyNews;
import com.example.newsclient.fragment.AllNewsContentFragmentRecreationNews;
import com.example.newsclient.fragment.AllNewsContentFragmentSportsNews;
import com.example.newsclient.fragment.SlidingMenuFeagment;
import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements
		OnPageChangeListener, OnCheckedChangeListener, OnClickListener {
	private ViewPager mViewPager;
	private ViewpagerAdapter mViewpagerAdapter;

	private RadioGroup radioGroupBtn;
	private ImageView slidingMenuImg, imgMoreChoose;
	private List<Fragment> mList = new ArrayList<Fragment>();
		
	private AllNewsContentFragmentHeadNews headFragment;
	private AllNewsContentFragmentRecreationNews recreationFragment;
	private AllNewsContentFragmentMoneyNews moneyFragment;
	private AllNewsContentFragmentSportsNews sportsFragment;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_main_layout);

		caseViewRadio();

		setNewsSlidingMenu();

	}

	/** 实例化控件 **/
	public void caseViewRadio() {
		mViewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());

		radioGroupBtn = (RadioGroup) findViewById(R.id.news_rabiogroup);
		slidingMenuImg = (ImageView) findViewById(R.id.news_slidingmenu_more_img);
		imgMoreChoose = (ImageView) findViewById(R.id.news_img_show_more);
		mViewPager = (ViewPager) findViewById(R.id.news_content_viewpager);

		mViewPager.setAdapter(mViewpagerAdapter);
		// 保存4页
		mViewPager.setOffscreenPageLimit(2);
		getDataViewpager();
		
		radioGroupBtn.setOnCheckedChangeListener(this);
		slidingMenuImg.setOnClickListener(this);
		imgMoreChoose.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);
		// 默认选中第一项
		((RadioButton) radioGroupBtn.getChildAt(0)).toggle();
	}

	/**
	 * 实例化Fragment
	 * 
	 * @return
	 */
	public void getDataViewpager() {
		headFragment = AllNewsContentFragmentHeadNews.newFragment();
		recreationFragment = AllNewsContentFragmentRecreationNews.newFragment();
		moneyFragment = AllNewsContentFragmentMoneyNews.newFragment();
		sportsFragment = AllNewsContentFragmentSportsNews.newFragment();
		mList.add(headFragment);
		mList.add(recreationFragment);
		mList.add(moneyFragment);
		mList.add(sportsFragment);
		mViewpagerAdapter.setDataFragmentAdapter(mList);
	}

	/** 侧滑菜单的设置 **/
	public void setNewsSlidingMenu() {
		setBehindContentView(R.layout.slidingmenu_dependon_fragmentlayout);
		getFragmentManager()
				.beginTransaction()
				.add(R.id.my_dependon_slidingmenu,
						SlidingMenuFeagment.newFragment()).commit();
		SlidingMenu sliding = getSlidingMenu();
		// 从左边滑出来
		sliding.setMode(SlidingMenu.LEFT);
		// 触摸模式
		sliding.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// 滑出的距离,距离适中就好
		sliding.setBehindOffsetRes(R.dimen.sliding_menu_space_main);
	}

	// viewpager开始
	public void onPageScrollStateChanged(int arg0) {

	}

	// viewpager正在改变
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// viewpager滑动结束
	public void onPageSelected(int arg0) {
	
//		hideSaveMsgFragment(mTransac);
		switch (arg0) {
		case 0:
			((RadioButton) radioGroupBtn.getChildAt(arg0)).toggle();
			break;
		case 1:
			((RadioButton) radioGroupBtn.getChildAt(arg0)).toggle();
			break;
		case 2:
			((RadioButton) radioGroupBtn.getChildAt(arg0)).toggle();
			break;
		case 3:
			((RadioButton) radioGroupBtn.getChildAt(arg0)).toggle();
			break;
		}
	}

	/** 两个图片按钮的监听 **/
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.news_slidingmenu_more_img:
			toggle();
			break;
		case R.id.news_img_show_more:
			break;
		}
	}

	/*** radioGroup的监听 **/
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.news_headline_rabiobtn:
			mViewPager.setCurrentItem(0);
			break;
		case R.id.news_recreation_radiobtn:
			mViewPager.setCurrentItem(1);

			break;
		case R.id.news_hotspot_radiobtn:
			mViewPager.setCurrentItem(2);
			break;
		case R.id.news_direct_radiobtn:
			mViewPager.setCurrentItem(3);
			break;
		}
	}

	public void hideSaveMsgFragment(FragmentTransaction transa) {
		if (headFragment != null) {
			transa.hide(headFragment);
		}
		if (recreationFragment != null) {
			transa.hide(recreationFragment);
		}
		if (moneyFragment != null) {
			transa.hide(moneyFragment);
		}
		if (sportsFragment != null) {
			transa.hide(sportsFragment);
		}
	}

}
