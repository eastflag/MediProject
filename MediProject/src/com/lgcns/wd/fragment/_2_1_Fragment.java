package com.lgcns.wd.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.lgcns.wd.R;

public class _2_1_Fragment extends Fragment {
	
	private View mView;
	private AQuery mAq;

	private EditText et1, et2, et3;
	private Spinner spinner;
	private Button btnSubmit;
	private TextView tvResult;
	
	private int D;
	
	String[] items = new String[] {"원인을 선택하세요", "바이러스성"};
	
	public _2_1_Fragment () {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_21, null);
		
		mAq = new AQuery(mView);
		
		et1 = (EditText) mView.findViewById(R.id.et1);
		et2 = (EditText) mView.findViewById(R.id.et2);
		et3 = (EditText) mView.findViewById(R.id.et3);
		spinner = (Spinner) mView.findViewById(R.id.spinner1);
		btnSubmit = (Button) mView.findViewById(R.id.btnSubmit);
		tvResult = (TextView) mView.findViewById(R.id.tvResult);
		
		/*final ArrayAdapter<CharSequence> adspin = ArrayAdapter.createFromResource(getActivity(), R.array.spin_21, android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);*/
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		            android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?>  parent, View view, int position, long id) {
            	Log.d("LDK", "position:" + position + " , id:" + id);
            	items[0] = "알콜성";
            	D = position;	
            }
            public void onNothingSelected(AdapterView<?>  parent) {
            }
        });
        
        btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int A = 0;
				int B = 0;
				int C = 0;
				try {
					A = Integer.parseInt(et1.getText().toString());
					B = Integer.parseInt(et1.getText().toString());
					C = Integer.parseInt(et1.getText().toString());
				} catch (NumberFormatException e) {
					
				}
				
				float sum = A * 3 + B * 9 + C*11 + D * 6.43f;
				String result = "";
				if(sum<9) {
					result = "사망률 4%";
				} else if(sum >=10 && sum <=19) {
					result = "사망률 27%";
				} else {
					result = "사망률 76%";
				}
				
				tvResult.setText("수치:" + sum + "\r\n" + result);
			}
		});
		
		return mView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
}
