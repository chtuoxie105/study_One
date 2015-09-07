package com.example.newsclient.httpclient;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;

/***
 * 网络取图片的，只需使用的2个方法： 第一个：使用此方法实例化自己：setIntanceCaseBitmap();
 * 第二个：此方法去开启异步取图片：creatThreadByBitmap();
 *
 * @author 内含的回调接口:BitmapNotifi,用于返回内存缓存的图片或网络获取的图片
 * @author1 Administrator
 *
 */
public class HttpGetBitmapTool {
	private BitmapNotifi mBitmapNotifi;
	private LruCache<String, Bitmap> mLruCache;
	private Executor executor;

	public static HttpGetBitmapTool mHttpGetBitmapTool;

	/** 采用单例模式 */
	public static HttpGetBitmapTool setIntanceCaseBitmap() {
		if (mHttpGetBitmapTool == null) {
			mHttpGetBitmapTool = new HttpGetBitmapTool();
		}
		return mHttpGetBitmapTool;
	}

	/** 实例化LruCache,并给定缓存的大小 */
	public void lrucacheCase() {
		int maxCache = (int) Runtime.getRuntime().maxMemory();
		int useCache = maxCache / 8;
		mLruCache = new LruCache<String, Bitmap>(useCache) {
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	/** 向我们分配的内存缓存添加图片 **/
	public void setBitmapToCache(String bitmapUrl, Bitmap bitmap) {
		if (getBitmapByCache(bitmapUrl) == null) {
			mLruCache.put(bitmapUrl, bitmap);
		}
	}

	/** 向我们分配的内存缓存取出图片 **/
	public Bitmap getBitmapByCache(String mapUrl) {
		return mLruCache.get(mapUrl);
	}

	/**
	 * 发起GET请求去连网取图片数据
	 * 
	 * @throws MsgException
	 **/
	public Bitmap getBitmapByhttpclient(String bitmapUrls) throws MsgException {

		Bitmap bitmap = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(bitmapUrls);
			HttpResponse respone = httpClient.execute(httpGet);
			int n = respone.getStatusLine().getStatusCode();
			if (n == 200) {

				bitmap = BitmapFactory.decodeStream(respone.getEntity()
						.getContent());

				return bitmap;
			}
		} catch (java.lang.OutOfMemoryError e) {
			throw new MsgException("网络连接异常,请检查你的网络!");
		} catch (IOException e) {
			throw new MsgException("网络连接异常,请检查你的网络!");
		} catch (java.lang.IllegalArgumentException e) {
			throw new MsgException("获取图片失败!");
		}
		return bitmap;
	}

	/** 开一个异步线程去网络取图片，防止堵塞主线程,并实例LRUCache **/
	public void creatThreadByBitmap(final String urls, BitmapNotifi bitmapNotifi) {
		mBitmapNotifi = bitmapNotifi;
		executor = new ThreadPoolExecutor(10, 105, 10, TimeUnit.SECONDS,
				new LinkedBlockingDeque<Runnable>());
		lrucacheCase();
		new AsyncTask<String, Void, Bitmap>() {
			protected void onPreExecute() {
				super.onPreExecute();
				Bitmap bitmap = mLruCache.get(urls);
				if (bitmap != null) {
					mBitmapNotifi.getBitmapByhttp(bitmap);
					return;
				}
			}

			protected Bitmap doInBackground(String... params) {
				Bitmap bit = null;
				try {
					bit = getBitmapByhttpclient(params[0]);
				} catch (MsgException e) {
					e.printStackTrace();
					mBitmapNotifi.getBitmapFail(e.getMessage());
				}
				return bit;
			}

			protected void onPostExecute(Bitmap result) {
				super.onPostExecute(result);
				if (result != null) {
					mBitmapNotifi.getBitmapByhttp(result);
				}
			}
		}.executeOnExecutor(executor, urls);
	}

	/** 回调的接口用于通知返回的图片 */
	public interface BitmapNotifi {
		/** 将网络或内存取到的图片返回主程序 */
		public void getBitmapByhttp(Bitmap bitmap);

		public void getBitmapFail(String resfial);
	}
}
