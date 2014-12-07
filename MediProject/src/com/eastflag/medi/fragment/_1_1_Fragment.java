package com.eastflag.medi.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.eastflag.medi.R;

public class _1_1_Fragment extends Fragment {

	private View mView;
	private WebView mWebView;

	public _1_1_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_11, null);

		mWebView = (WebView) mView.findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl("file:///android_asset/11.htm");

		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
