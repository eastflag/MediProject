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

public class _1_8_Fragment extends Fragment {

	private View mView;
	private String mHtmlText = "<body><br /><img src='pin.png'>호흡피로증후가 보일때<br /><br />"
			+ "<img src='pin.png'>RR이 10회이상 증가하거나 RR>30<br /><br />"
			+ "<img src='pin.png'>sys BP가 20이상 dia.BP가 10이상 변화있을때<br /><br />"
			+ "<img src='pin.png'>PaO2 <60 mmHg PaCO2 >55mmHg<br /><br />"
			+ "<img src='pin.png'>pH < 7.35<br /><br />"
			+ "<img src='pin.png'>Tidal volume <250-300ml or 5ml/kg<br /></body>";

	public _1_8_Fragment() {

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
