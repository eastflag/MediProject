package com.lgcns.wd.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgcns.wd.R;

public class _1_2_Fragment extends Fragment {

	private View mView;
	private String mHtmlText = "<body><br /><img src='pin.png'>FiO2(trial and error approach)로 initial setting<br /><br />"
			+ "<img src='pin.png'>FiO2> 90% (24시간 이내유지<br /><br />"
			+ "<img src='pin.png'>FiO2 60-90% (2일-3일이내 유지)<br /><br />"
			+ "<img src='pin.png'>FIO2 0.5 이하면 (수주간 안정하다)<br /><br />"
			+ "<img src='pin.png'>산소독성 :Tracheobronchitis, parenchymal injury(ARDS 와 비슷한 양상)<br /><br />"
			+ "* 주의: 저산소증이 hyperoxia 보다 나쁘다!!!<br /></body>";

	public _1_2_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_11, null);

		TextView tvDisplay = (TextView) mView.findViewById(R.id.tvDisplay);
		tvDisplay.setText(Html.fromHtml(mHtmlText, new ImageGetter(), null));

		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	private class ImageGetter implements Html.ImageGetter {

		public Drawable getDrawable(String source) {
			int id;
			if (source.equals("pin.png")) {
				id = R.drawable.pin;
			} else {
				return null;
			}

			Drawable d = getResources().getDrawable(id);
			d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			return d;
		}
	};

}
