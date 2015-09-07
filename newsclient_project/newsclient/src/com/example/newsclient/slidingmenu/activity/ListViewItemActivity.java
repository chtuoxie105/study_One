package com.example.newsclient.slidingmenu.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.newsclient.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewItemActivity extends Activity {
	private TextView titleTxt, originTxt;
	
	private NetworkImageView mImgBg;
	private WebView mWebView;
	private String docid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_item__webviewlayout);
		loadView();
		getDocid();
		creatVolley();
	}

	/** 实例化控件 **/
	public void loadView() {
		mImgBg = (NetworkImageView) findViewById(R.id.item_listview_image);
		mWebView = (WebView) findViewById(R.id.item_listview_webview);
		titleTxt = (TextView) findViewById(R.id.item_listview_title);
		originTxt = (TextView) findViewById(R.id.item_listview_origin);

	}
	public String getDocid() {
		Intent intent = getIntent();
		String docid = null;
		if (intent != null) {
			Bundle bund = intent.getBundleExtra("BUND_ITEM");
			docid = bund.getString("ITEM_DOCID");
			String title = bund.getString("ITEM_TITLE");
			String source = bund.getString("ITEM_SOURCE");//内容提供的网站
			String time = bund.getString("ITEM_TIME");//内容提供的网站
			String imgUrl = bund.getString("ITEM_IMGURL");
			getImg(imgUrl);
			titleTxt.setText(title);
			originTxt.setText(source+"   "+time);
			return docid;
		}
		return docid;
	}
	public void getImg(String respone){
		mImgBg.setDefaultImageResId(R.drawable.item_img_bg);
		mImgBg.setErrorImageResId(R.drawable.item_img_fail);
		mImgBg.setImageUrl(respone,RequestManager.getImageLoader());
	}
	
	public void creatVolley() {
		docid = getDocid();
		String url = "http://c.m.163.com/nc/article/" + docid + "/" + "full.html";
		WebSettings webSetting = mWebView.getSettings();
		webSetting.setJavaScriptEnabled(true);
		
		StringRequest request = new StringRequest(url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						JSONData(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(
								ListViewItemActivity.this,
								VolleyErrorHelper.getMessage(error,
										ListViewItemActivity.this),0)
								.show();
					}
				});
		RequestManager.getRequestQueue().add(request);
	}
	
	public void JSONData(String response){
		try {
			JSONObject jsonObj = new JSONObject(response);
			JSONObject jsonContent = jsonObj.getJSONObject(docid);
			String body = jsonContent.getString("body");
			mWebView.loadDataWithBaseURL(null, body,"text/html","UTF-8",null);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
}
