package com.eastflag.medifree;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.eastflag.medifree.view.LoadingDialog;

public class BoardViewActivity extends Activity {
	
	private ImageView ivUserImg;
	private EditText etTitle, etContent, etName, etPassword;
	private Button btnModify, btnImage, btnSubmit, btnDelete;
	
	private String user_password;
	private int board_id;
	
	private AQuery mAq;
	private String imageDataString ="";
	
	View.OnClickListener mClick = new View.OnClickListener() {
		@Override
		public void onClick(final View v) {
			switch(v.getId()) {
			case R.id.btnDelete:
			case R.id.btnModify: //fall-through
				View view = View.inflate(BoardViewActivity.this, R.layout.confirm_password, null);
				final EditText etPw = (EditText) view.findViewById(R.id.etPassword);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(BoardViewActivity.this);
				builder.setTitle("패스워드를 입력하세요")
					.setView(view);
				final AlertDialog dialog = builder.create();
				dialog.show();
				
				Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
				btnConfirm.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View dv) { //2015-06-01 삭제 안되는 오류 수정
						if(user_password.equals(etPw.getText().toString())) {
							if(v.getId() == R.id.btnDelete) {
								removeBoard();
							} else {
								dialog.dismiss();
								changeToModifyBoardUI();
							}
						} else {
							Toast.makeText(BoardViewActivity.this, "패스워드가 맞지 않습니다", Toast.LENGTH_SHORT).show();
						}
					}
				});
				break;

			case R.id.btnSubmit:
				modifyBoard();
				break;
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
		ivUserImg = (ImageView) findViewById(R.id.ivUserImg);
		btnModify = (Button) findViewById(R.id.btnModify);
		btnImage = (Button) findViewById(R.id.btnImage);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		
		etTitle.setEnabled(false);
		etContent.setEnabled(false);
		etName.setEnabled(false);
		etPassword.setEnabled(false);
		btnModify.setVisibility(View.VISIBLE);
		btnImage.setVisibility(View.GONE);
		btnSubmit.setVisibility(View.GONE);
		btnDelete.setVisibility(View.VISIBLE);
		findViewById(R.id.rootPassword).setVisibility(View.GONE);
		
		btnModify.setOnClickListener(mClick);
		ivUserImg.setOnClickListener(mClick);
		btnImage.setOnClickListener(mClick);
		btnSubmit.setOnClickListener(mClick);
		btnDelete.setOnClickListener(mClick);
		
		board_id = getIntent().getExtras().getInt("board_id");
		
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
						user_password = jsonBoard.getString("user_password");
						
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
	
	private void removeBoard() {
		String url = "http://www.javabrain.kr/api/removeBoard";
		JSONObject json = new JSONObject();
		Log.d("LDK", "removeBoard:" + board_id);
		
		try {
			json.put("board_id", board_id);
			mAq.post(url, json, JSONObject.class, new AjaxCallback<JSONObject>(){
				@Override
				public void callback(String url, JSONObject object, AjaxStatus status) {
					try {
						Log.d("LDK", object.toString());
						if(object.getInt("result") == 0) {
							Toast.makeText(BoardViewActivity.this, "삭제하였습니다", Toast.LENGTH_SHORT).show();
							finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	} 
	
	private void modifyBoard() {
		String url = "http://www.javabrain.kr/api/modifyBoard";
		JSONObject json = new JSONObject();
		try {
			json.put("board_id", board_id);
			json.put("title", etTitle.getText().toString());
			json.put("content", etContent.getText().toString());
			json.put("user_name", etName.getText().toString());
			json.put("user_password", etPassword.getText().toString());
			json.put("image", imageDataString);
			Log.d("LDK", json.toString(1));
			
			mAq.post(url, json, JSONObject.class, new AjaxCallback<JSONObject>(){
				@Override
				public void callback(String url, JSONObject object, AjaxStatus status) {
					Log.e("LDK", object.toString());
					LoadingDialog.hideLoading();
					if(status.getCode() == 200) {
						Toast.makeText(BoardViewActivity.this, "수정에 성공하였습니다.", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(BoardViewActivity.this, "수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void changeToModifyBoardUI() {
		etTitle.setEnabled(true);
		etContent.setEnabled(true);
		etName.setEnabled(true);
		etPassword.setEnabled(true);
		btnImage.setVisibility(View.VISIBLE);
		btnSubmit.setVisibility(View.VISIBLE);
		btnDelete.setVisibility(View.INVISIBLE);
		btnModify.setVisibility(View.INVISIBLE);
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
