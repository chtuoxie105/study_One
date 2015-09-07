package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

public class MyRequest<T> extends Request<T> {
	private Listener<T> mListener;
	private Class<T> mClazz;
	private Map maps;

	public MyRequest(int method, String url, Listener<T> listener,
			ErrorListener errorlistener, Class<T> clazz, Map map) {
		super(method, url, errorlistener);
		mListener = listener;
		mClazz = clazz;
		maps = map;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return maps != null ? maps : super.getParams();
	}

//	@Override
//	public RetryPolicy getRetryPolicy() {
//		RetryPolicy retryPolicy = new DefaultRetryPolicy(VolleyConfigs.SOCKET_TIMEOUT,
//				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//		return retryPolicy;
//	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return Response.success(JSON.parseObject(parsed, mClazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

}
