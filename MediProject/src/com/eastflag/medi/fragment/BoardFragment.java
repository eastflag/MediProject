package com.lgcns.wd.fragment;

import java.io.File;
import java.io.IOException;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidquery.AQuery;
import com.lgcns.wd.R;

public class BoardFragment extends Fragment {
	
	private View mView;
	private AQuery mAq;
	
	private Button btnWrite;

	
	public BoardFragment () {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.board, null);
		
		mAq = new AQuery(mView);
		
		btnWrite = (Button) mView.findViewById(R.id.btnWrite);
		btnWrite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK,
	                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	            intent.setType("image/*");              // 모든 이미지
	            intent.putExtra("crop", "true");        // Crop기능 활성화
	            intent.putExtra("scale", true);
	            intent.putExtra("outputX",  200);
	            intent.putExtra("outputY",  200);
	            intent.putExtra("aspectX",  1);
	            intent.putExtra("aspectY",  1);

	            intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // 임시파일 생성
	            intent.putExtra("outputFormat",         // 포맷방식
	                    Bitmap.CompressFormat.JPEG.toString());

	            startActivityForResult(intent, 0);
			}
		});
		
		return mView;
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
            File f = new File(Environment.getExternalStorageDirectory(), // 외장메모리 경로
                    "temp.jpg");
            try {
                f.createNewFile();      // 외장메모리에 temp.jpg 파일 생성
            } catch (IOException e) {
            	Log.d("LDK", "createNewFile error : " + e.toString());
            }
 
            return f;
        } else
            return null;
    }
	
}
