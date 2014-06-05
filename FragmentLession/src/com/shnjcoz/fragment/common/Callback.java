package com.shnjcoz.fragment.common;

import java.util.Map;

import org.json.JSONObject;

public interface Callback {

	public static final int SUCCESS = 0;

	public static final int ERROR = -1;

	public void callback(final String responseCode, final String requestCode, final Map<String,JSONObject>resultMap);

}