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

public class _1_3_Fragment extends Fragment {

	private View mView;
	private String mHtmlText = "<body><table>  <tr>    <th>Month</th>    <th>Savings</th>  </tr>  <tr>    <td>January</td>    <td>$100</td>  </tr>  <tr>    <td>February</td>    <td>$80</td>  </tr></table></body>";

	public _1_3_Fragment() {

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
