package com.example.newsclient.activity.main;

import java.util.List;

import me.maxwin.view.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.newsclient.R;
import com.example.newsclient.adapter.ListContentHeadAdapter;
import com.example.newsclient.bean.HttpGetDataBeanOne;
import com.example.newsclient.bean.HttpGetDataMain;
import com.example.newsclient.httpclient.HttpGetDataTool;
import com.example.newsclient.httpclient.HttpGetDataTool.BackMsgByAsynctask;
import com.google.gson.Gson;

public class TestGsonctivity extends Activity {
	private Button button;
	private XListView xListView;
	private ListContentHeadAdapter adapter;
	private HttpGetDataTool mHttpGetDataTool;
	String url = "http://c.m.163.com/nc/article/headline/T1348647909107/0-10.html";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_news_gson_layout);
		mHttpGetDataTool = HttpGetDataTool.setIntanceCaseHttp();
		button = (Button) findViewById(R.id.test_gson_news_btn);
		xListView = (XListView) findViewById(R.id.test_news_xlistview_one);
		
		adapter = new ListContentHeadAdapter(this);
		xListView.setAdapter(adapter);		
		
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mHttpGetDataTool.createThreadGetData(TestGsonctivity.this, url,
						new BackMsgByAsynctask() {
							public void normalMsg(String normalMsg) {
//								onetest(normalMsg);
								twotest(normalMsg);
							}

							public void exceptionMsg(String normalMsg) {
							}
						});
			}
		});
	}
	
	public void  twotest(String ss){
		Gson gson = new Gson();
		HttpGetDataMain mHttpGetDataMain = gson.fromJson(ss,HttpGetDataMain.class);
		List<HttpGetDataBeanOne>list = mHttpGetDataMain.getT1348647909107();
		for(int i=0;i<list.size();i++){
			HttpGetDataBeanOne bean = list.get(i);
			Log.i("11","title"+bean.getTitle());
		}
//		adapter.setData(list);
	}
	public void onetest(String ss){
		try {
			
			JSONObject obj = new JSONObject(ss);
			JSONArray list = obj.getJSONArray("T1348647909107");
			
			for(int i=0;i<list.length();i++){
				
				JSONObject objson = list.getJSONObject(i);
				HttpGetDataBeanOne bean = new HttpGetDataBeanOne();
				bean.getTitle();
				Log.i("11","title"+objson.getString("title"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
