package com.eastflag.medi.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.eastflag.medi.R;

public class BoardWriteFragment extends Fragment {

	public final  int MAX_WIDTH = 600;
	
	private View mView;
	private Button btnSubmit;
	private ImageView ivUserImg;
	
	private AQuery mAq;
	private String imageDataString ="";

	public BoardWriteFragment() {

	}
	
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
				
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.boardwrite, null);

		
		ivUserImg = (ImageView) mView.findViewById(R.id.ivUserImg);
		btnSubmit = (Button) mView.findViewById(R.id.btnSubmit);
		
		mView.findViewById(R.id.btnImage).setOnClickListener(mClick);
		ivUserImg.setOnClickListener(mClick);
		btnSubmit.setOnClickListener(mClick);

		return mView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.e("LDK", "Fragment onActivityResult requestCode: " + requestCode + "," + resultCode);
		
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

	@Override
	public void onDestroyView() {
		super.onDestroyView();
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
