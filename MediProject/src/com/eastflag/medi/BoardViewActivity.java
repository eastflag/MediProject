package com.eastflag.medi;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class BoardViewActivity extends Activity {
	
	private Button btnSubmit;
	private ImageView ivUserImg;
	private EditText etTitle, etContent, etName, etPassword;
	
	private AQuery mAq;
	
	View.OnClickListener mClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boardwrite);
		
		mAq = new AQuery(this);
		
		etTitle = (EditText) findViewById(R.id.etTitle);
		etContent = (EditText) findViewById(R.id.etContent);
		etName = (EditText) findViewById(R.id.etName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etTitle.setEnabled(false);
		etContent.setEnabled(false);
		etName.setEnabled(false);
		etPassword.setEnabled(false);
		
		ivUserImg = (ImageView) findViewById(R.id.ivUserImg);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		
		findViewById(R.id.btnImage).setOnClickListener(mClick);
		ivUserImg.setOnClickListener(mClick);
		btnSubmit.setOnClickListener(mClick);
		
		int board_id = getIntent().getExtras().getInt("board_id");
		
		String url = "http://www.javabrain.kr/api/getBoard";
		JSONObject json = new JSONObject();
		try {
			json.put("board_id", board_id);
			mAq.post(url, json, JSONObject.class, new AjaxCallback<JSONObject>(){
				@Override
				public void callback(String url, JSONObject object, AjaxStatus status) {
					try {
						Log.d("LDK", object.toString());
						JSONObject jsonBoard = object.getJSONObject("value");
						etTitle.setText(jsonBoard.getString("title"));
						etContent.setText(jsonBoard.getString("content"));
						etName.setText(jsonBoard.getString("user_name"));
						
						byte[] decodedString = Base64.decode(jsonBoard.getString("image"), Base64.DEFAULT);
						Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
						ivUserImg.setImageBitmap(decodedByte);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.board_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
