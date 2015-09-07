package com.example.newsclient.activity.main;

import com.example.newsclient.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListViewContentFragment extends Fragment {
	private ListView mListViews;
	public ListViewContentFragment newFragment() {

		return new ListViewContentFragment();
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragmentcontent_listview_layout,
				container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mListViews = (ListView) getView().findViewById(
				R.id.news_listview_content);
	}
}
