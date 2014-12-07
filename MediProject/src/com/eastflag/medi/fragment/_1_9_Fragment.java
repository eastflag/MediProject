package com.lgcns.wd.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.lgcns.wd.R;

public class _1_9_Fragment extends Fragment {

	private View mView;
	private WebView mWebView;

	public _1_9_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_11, null);

		mWebView = (WebView) mView.findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl("");

		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
