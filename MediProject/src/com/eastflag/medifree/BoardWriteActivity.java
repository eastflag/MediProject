package com.eastflag.medifree;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import com.eastflag.medifree.R;
import com.eastflag.medifree.view.LoadingDialog;

public class BoardWriteActivity extends Activity {
	public final  int MAX_WIDTH = 600;
	
	private Button btnSubmit;
	private ImageView ivUserImg;
	private EditText etTitle, etContent, etName, etPassword;
	
	private AQuery mAq;
	private String imageDataString ="";
	
	View.OnClickListener mClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.btnImage:
			case R.id.ivUserImg:
				//Intent intent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*"); // 모든 이미지
				intent.putExtra("crop", "true"); // Crop기능 활성화
				intent.putExtra("scale", true);
//				intent.putExtra("outputX", 400);
//				intent.putExtra("outputY", 400);
//				intent.putExtra("aspectX", 1);
//				intent.putExtra("aspectY", 1);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
				intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

				startActivityForResult(intent, 100);
				break;
				
			case R.id.btnSubmit:
				if(TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString()) || 
						TextUtils.isEmpty(etContent.getText().toString()) || TextUtils.isEmpty(etTitle.getText().toString())) {
					Toast.makeText(BoardWriteActivity.this, "입력되지 않는 항목이 있습니다", Toast.LENGTH_SHORT).show();
					return;
				}
				
				LoadingDialog.showLoading(BoardWriteActivity.this);
				
				String url = "http://www.javabrain.kr/api/addBoard";
				JSONObject json = new JSONObject();
				try {
					json.put("title", etTitle.getText().toString());
					json.put("content", etContent.getText().toString());
					json.put("user_name", etName.getText().toString());
					json.put("user_password", etPassword.getText().toString());
					json.put("image", imageDataString);
					Log.d("LDK", json.toString(1));
					
					mAq.post(url, json, JSONObject.class, new AjaxCallback<JSONObject>(){
						@Override
						public void callback(String url, JSONObject object, AjaxStatus status) {
							LoadingDialog.hideLoading();
							if(status.getCode() == 200) {
								Toast.makeText(BoardWriteActivity.this, "글쓰기를 성공하였습니다.", Toast.LENGTH_SHORT).show();
								finish();
							} else {
								Toast.makeText(BoardWriteActivity.this, "글쓰기를 실패하였습니다.", Toast.LENGTH_SHORT).show();
							}
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
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
		
		findViewById(R.id.btnModify).setVisibility(View.GONE);
		
		ivUserImg = (ImageView) findViewById(R.id.ivUserImg);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		
		findViewById(R.id.btnImage).setOnClickListener(mClick);
		ivUserImg.setOnClickListener(mClick);
		btnSubmit.setOnClickListener(mClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.board_write, menu);
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.e("LDK", "onActivityResult requestCode: " + requestCode + "," + resultCode);
		
		/*if(resultCode != Activity.RESULT_OK || data == null) {
			return;
		}*/
		
		switch (requestCode) {
        case 100:
        	try {
	            String filePath = Environment.getExternalStorageDirectory() + "/temp.jpg";
	 
	            System.out.println("path" + filePath); // logCat으로 경로확인.
	 
	            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
	            // temp.jpg파일을 Bitmap으로 디코딩한다.
	    		
	    		int width = selectedImage.getWidth();
	    		int height = selectedImage.getHeight();
	    		
	    		if(selectedImage.getWidth() > MAX_WIDTH) {
	    			width = MAX_WIDTH;
	    			height = (int) (selectedImage.getHeight() * (MAX_WIDTH * 1f /selectedImage.getWidth())); 
	    		}
	    		Log.d("LDK", "w:" + width + "h:" + height);
	            		
	            selectedImage = Bitmap.createScaledBitmap(selectedImage, width, height, true);
	 
	            imageDataString = getEncodeString(selectedImage);
	                    
	            ivUserImg.setImageBitmap(selectedImage);
        	} catch (Exception e) {
        		Log.d("LDK", e.toString());
        	}
            break;
        }
	}

	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	private boolean isSdcardMounted() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;

		return false;
	}

	private File getTempFile() {
		if (isSdcardMounted()) {
			File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
			try {
				f.createNewFile(); // 외장메모리에 temp.jpg 파일 생성
			} catch (IOException e) {
				Log.d("LDK", "createNewFile error : " + e.toString());
			}

			return f;
		} else
			return null;
	}
	
	private String getEncodeString(Bitmap bm){
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 

		bm.compress(Bitmap.CompressFormat.JPEG, 90 , baos);    
		byte[] b = baos.toByteArray(); 
		
		return Base64.encodeToString(b, Base64.DEFAULT);
	}
}
