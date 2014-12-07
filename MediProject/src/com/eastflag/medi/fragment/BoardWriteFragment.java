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

public class BoardWriteFragment extends Fragment {

	private View mView;

	public BoardWriteFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.boardwrite, null);

		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
