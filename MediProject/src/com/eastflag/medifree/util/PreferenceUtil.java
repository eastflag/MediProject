package com.eastflag.medifree.util;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import android.content.Context;

public class PreferenceUtil extends BasePreferenceUtil {
	private static PreferenceUtil _instance = null;
	

	public static synchronized PreferenceUtil instance(Context context) {
		if (_instance == null)
			_instance = new PreferenceUtil(context);
		return _instance;
	}

	private PreferenceUtil(Context context) {
		super(context);
	}

	public void setItem(String key) {
		put(key, key);
	}
	
	public String getItem(String key) {
		return get(key);
	}

}
