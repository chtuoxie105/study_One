package com.example.newsclient.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.newsclient.R;
import com.example.newsclient.slidingmenu.activity.VideoActivity;

public class SlidingMenuFeagment extends Fragment implements
		OnItemClickListener {
	private ListView mListView;

	public static SlidingMenuFeagment newFragment() {
		return new SlidingMenuFeagment();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.news_sliding_menu_layout, container,
				false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView = (ListView) getView().findViewById(
				R.id.news_sliding_menu_item_listview);
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), getData(),
				R.layout.news_sliding_menu_item_layout, new String[] {
						"LIST_ITEM_IMG", "LIST_ITEM_CONTENT" }, new int[] {
						R.id.slidingmenu_list_item_img,
						R.id.slidingmenu_list_item_text });
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}

	public List<HashMap<String, Object>> getData() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("LIST_ITEM_IMG", R.drawable.sliding_navigation_tab_weather);
		map.put("LIST_ITEM_CONTENT", "天气");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("LIST_ITEM_IMG", R.drawable.sliding_navigation_tab_map_1);
		map.put("LIST_ITEM_CONTENT", "地图");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("LIST_ITEM_IMG", R.drawable.sliding_navigation_tab_video);
		map.put("LIST_ITEM_CONTENT", "视频");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("LIST_ITEM_IMG", R.drawable.sliding_navigation_tab_pics);
		map.put("LIST_ITEM_CONTENT", "图片");
		list.add(map);
		return list;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		@SuppressWarnings("unchecked")
		HashMap<String, String> maps= (HashMap<String, String>) parent.getAdapter().getItem(position);
		
		String name = maps.get("LIST_ITEM_CONTENT");
		if (name.equals("天气")) {

		}
		if (name.equals("地图")) {

		}
		if (name.equals("视频")) {
			startActivity(new Intent(getActivity(), VideoActivity.class));
		}
		if (name.equals("图片")) {

		}

	}
}
