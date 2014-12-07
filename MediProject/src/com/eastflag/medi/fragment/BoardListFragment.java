package com.eastflag.medi.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.eastflag.medi.R;

public class BoardListFragment extends Fragment {

	private View mView;
	private OnClickListener mClick;

	public BoardListFragment(OnClickListener mClick) {
		this.mClick = mClick;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.boardlist, null);
		
		mView.findViewById(R.id.btnWrite).setOnClickListener(mClick);
		
		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
