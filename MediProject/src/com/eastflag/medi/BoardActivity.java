package com.lgcns.wd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.androidquery.AQuery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class BoardActivity extends Activity {

	public final  int MAX_WIDTH = 600;
	
	private AQuery mAq;

	private Button btnWrite;
	String imageDataString ="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);

		mAq = new AQuery(this);

		btnWrite = (Button) findViewById(R.id.btnWrite);
		btnWrite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*"); // 모든 이미지
				intent.putExtra("crop", "true"); // Crop기능 활성화
				intent.putExtra("scale", true);
//				intent.putExtra("outputX", 400);
//				intent.putExtra("outputY", 400);
//				intent.putExtra("aspectX", 1);
//				intent.putExtra("aspectY", 1);

				intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri()); // 임시파일
																		// 생성
				intent.putExtra("outputFormat", // 포맷방식
						Bitmap.CompressFormat.JPEG.toString());

				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_board) {

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onActivityResult(int requestCode, int resultCode,
	            Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
 
        switch (requestCode) {
        case 0:
            if (resultCode == RESULT_OK) {
                if (imageData != null) {
                    String filePath = Environment.getExternalStorageDirectory()
                            + "/temp.jpg";
 
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
                    
                    ImageView _image = (ImageView) findViewById(R.id.ivUserImg);
                    //iv_user_image.setImageBitmap(selectedImage); 
                    _image.setImageBitmap(selectedImage);
                    //temp.jpg파일을 이미지뷰에 씌운다.
                }
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
			File f = new File(Environment.getExternalStorageDirectory(), // 외장메모리
																			// 경로
					"temp.jpg");
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
