package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class MyJsonPostRequest extends Request<JSONObject> {
	private Map maps;
	private final Listener<JSONObject> mListener;

	public MyJsonPostRequest(int method, String url, Listener<JSONObject> listener,
			ErrorListener errorListener, Map maps) {
		super(method, url, errorListener);
		mListener = listener;
		this.maps = maps;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return maps != null ? maps : super.getParams();
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
			
			return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
			
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		mListener.onResponse(response);
	}
}
