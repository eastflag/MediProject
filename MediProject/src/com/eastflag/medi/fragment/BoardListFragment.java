package com.eastflag.medi.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.eastflag.medi.BoardWriteActivity;
import com.eastflag.medi.R;

public class BoardListFragment extends Fragment {

	private AQuery mAq;
	private View mView;
	private OnClickListener mClick;

	public BoardListFragment(OnClickListener mClick) {
		this.mClick = mClick;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.boardlist, null);
		mAq = new AQuery(getActivity(), mView);
		
		mView.findViewById(R.id.btnWrite).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), BoardWriteActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		
		
		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
