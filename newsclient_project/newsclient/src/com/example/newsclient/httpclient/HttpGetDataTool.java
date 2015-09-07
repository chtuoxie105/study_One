package com.example.newsclient.httpclient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

/***
 * 网络取数据的，只需使用的2个方法： 第一个：使用此方法实例化自己：setIntanceCaseHttp();
 * 第二个：此方法去开启异步取数据：createThreadGetData();
 * 
 * @author 内含的回调接口:BackMsgByAsynctask,用于返回正常的数据和异常的捕捉
 * 
 * @author1 Administrator:
 *          sharedPrefrenceJSON()用于保存最新的新闻数据，不必调用，当捕捉到网络异常时就会返回保存的JSON数据
 */
public class HttpGetDataTool {
	private BackMsgByAsynctask mBackMsgByAsynctask;
	public static HttpGetDataTool mHttpGetDataTool;
	private Context mContext;
	private SharedPreferences mShared;

	public static HttpGetDataTool setIntanceCaseHttp() {
		if (mHttpGetDataTool == null) {
			mHttpGetDataTool = new HttpGetDataTool();
		}
		return mHttpGetDataTool;
	}

	/** 开启异步线程去网络取数据，防止堵塞主线程:第一个是网络地址，第二个是回调接口的实例 */
	public void createThreadGetData(Context context, String url,
			BackMsgByAsynctask backMsgByAsynctask) {
		mContext = context;
		mBackMsgByAsynctask = backMsgByAsynctask;
		new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... params) {
				String line = "";
				try {
					line = httpGetByJson(params[0]);

				} catch (MsgException e) {
					e.printStackTrace();
					mBackMsgByAsynctask.exceptionMsg(e.getMessage());
				}
				return line;
			}

			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				mBackMsgByAsynctask.normalMsg(result);
			}
		}.execute(url);
	}

	/** 次接口用回调，提供2种方法，分别用于返回正常数据{normalMsg()}和捕捉的错误请求异常{exceptionMsg()} **/
	public interface BackMsgByAsynctask {
		/** 返回正常数据信息,进行解析处理 **/
		public void normalMsg(String normalMsg);

		/** 返回捕捉到的错误异常信息 **/
		public void exceptionMsg(String normalMsg);
	}

	/** 发起GET请求去连网取JSON数据 **/
	public String httpGetByJson(String url) throws MsgException {
		String contentString = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			// 设置请求超时15秒钟
			HttpConnectionParams.setConnectionTimeout(new BasicHttpParams(),
					15000);

			// 设置等待数据超时时间10秒钟
			HttpConnectionParams.setSoTimeout(new BasicHttpParams(), 10000);
			HttpResponse responese = httpClient.execute(httpGet);
			int n = responese.getStatusLine().getStatusCode();
			if (n == 200) {
				contentString = EntityUtils.toString(responese.getEntity());
				setSharedPrefrenceJSON(url, contentString);
				return contentString;
			} else {
				contentString = "服务器连接异常";
			}
		} catch (IOException e) {
			throw new MsgException("网络连接异常,请检查你的网络!");
		}
		return contentString;
	}

	/** 保存JSON取到的数据，只保存一次保存到SharedPrefrences中，更新一次覆盖一次 **/
	public void setSharedPrefrenceJSON(String nameKey, String dataString) {
		String fileName = "";
		mShared = mContext
				.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = mShared.edit();
		editor.putString(nameKey, dataString);
		editor.commit();
	}
	/** 获取JSON取到的数据，获取上一次保存在SharedPrefrences里的数据 **/
	public String getSharedPrefrences(String nameKey){
		return mShared.getString(nameKey, "");
	}
}
