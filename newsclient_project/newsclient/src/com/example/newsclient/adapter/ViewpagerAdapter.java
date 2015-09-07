package com.example.newsclient.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewpagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> list = new ArrayList<Fragment>();

	public ViewpagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public void setDataFragmentAdapter(List<Fragment> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public Fragment getItem(int arg0) {

		return list.get(arg0);
	}

	public int getCount() {
		return list.size();
	}

}
