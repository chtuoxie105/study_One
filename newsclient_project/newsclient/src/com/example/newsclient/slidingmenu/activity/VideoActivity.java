package com.example.newsclient.slidingmenu.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsclient.R;
import com.example.newsclient.adapter.VideoListAdapter;
import com.example.newsclient.bean.VideoBean;

public class VideoActivity extends Activity implements IXListViewListener,
		OnItemClickListener {
	private XListView videoXListView;
	private VideoListAdapter videoAdapter;
	private RequestQueue queue;
	private DestroyMediaplayer mDestroyMediaplayer;
	int pageNo = 0; // 页号 ，表示第几页,第一页从0开始
	int pageSize = 10; // 页大小，显示每页多少条数据
	String video_type_id = "V9LG4B3A0"; // 视频类型标识, 此处表示热点视频
	private String url = "http://c.3g.163.com/nc/video/list/" + video_type_id
			+ "/n/" + pageNo * pageSize + "-" + pageSize + ".html";
	private List<VideoBean> mList = new ArrayList<VideoBean>();
	private VideoListAdapter mVideoListAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_video_main_layout);

		videoXListView = (XListView) findViewById(R.id.sliding_video_xlistview);

		View view = findViewById(R.id.listview_refresh__videolayout);
		videoXListView.setEmptyView(view);

		mVideoListAdapter = new VideoListAdapter(this);

		videoAdapter = mVideoListAdapter;
		videoXListView.setAdapter(videoAdapter);

		mDestroyMediaplayer = (DestroyMediaplayer) mVideoListAdapter;
		queue = Volley.newRequestQueue(this);
		RefreshData(url);

		videoXListView.setPullRefreshEnable(true);
		videoXListView.setPullLoadEnable(true);
		videoXListView.setOnItemClickListener(this);
	}

	public void RefreshData(String urls) {
		JsonObjectRequest request = new JsonObjectRequest(urls, null,
				new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						JSONData(response);
					}
				}, new Response.ErrorListener() {
					@SuppressLint("ShowToast")
					public void onErrorResponse(VolleyError error) {
						onLoad();
						Toast.makeText(
								VideoActivity.this,
								VolleyErrorHelper.getMessage(error,
										VideoActivity.this), 0).show();
					}
				});
		queue.add(request);
	}

	public void JSONData(JSONObject response) {
		try {
			JSONArray array = response.getJSONArray("V9LG4B3A0");
			int n = array.length();
			for (int i = 0; i < n; i++) {
				JSONObject obj = array.getJSONObject(i);
				VideoBean bean = new VideoBean();
				bean.setCover(obj.getString("cover"));
				bean.setMp4_url(obj.getString("mp4_url"));
				bean.setTitle(obj.getString("title"));
				bean.setDescription(obj.getString("description"));
				bean.setLength(obj.getInt("length"));
				bean.setPlayCount(obj.getString("playCount"));
				bean.setReplyCount(obj.getString("replyCount"));
				mList.add(bean);
			}
			if (pageNo == 0) {
				videoAdapter.setData(mList);
			} else {
				videoAdapter.addData(mList);
			}
			if (pageNo == 20) {
				videoXListView.setPullLoadEnable(false);
			}
			onLoad();

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void onRefresh() {
		pageNo = 0;
		url = "http://c.3g.163.com/nc/video/list/" + video_type_id + "/n/"
				+ pageNo * pageSize + "-" + pageSize + ".html";
		RefreshData(url);
		videoXListView.setPullLoadEnable(true);
	}

	public void onLoadMore() {
		pageNo = pageNo + 10;
		if (pageNo <= 20) {
			url = "http://c.3g.163.com/nc/video/list/" + video_type_id + "/n/"
					+ pageNo * pageSize + "-" + pageSize + ".html";
			RefreshData(url);
		}
	}

	public void onLoad() {
		videoXListView.stopRefresh();
		videoXListView.stopLoadMore();
		videoXListView.setRefreshTime(currentTime());// 时间

	}

	@SuppressLint("SimpleDateFormat")
	public String currentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public interface DestroyMediaplayer {
		public void destroyMediaplayer();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDestroyMediaplayer.destroyMediaplayer();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		VideoBean bean = (VideoBean) parent.getAdapter().getItem(position);
		bean.getMp4_url();
	}

}
