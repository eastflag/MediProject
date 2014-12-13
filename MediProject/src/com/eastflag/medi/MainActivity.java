package com.eastflag.medi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.eastflag.medi.fragment.BoardListFragment;

public class MainActivity extends Activity {
	
	public WebView mWebView;
	private ProgressBar mProgressBar;
	private ImageView logo;
	
	private FragmentManager mFm;
	private Fragment mFragment;
	
	private View main;
	private FrameLayout frame;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case 0:
				Animation fadeout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
			    logo.startAnimation(fadeout);
			    fadeout.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
						logo.setVisibility(View.GONE);
					}
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					@Override
					public void onAnimationEnd(Animation animation) {
					}
				});
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		//html5 localStorage 를 사용하게하기
		mWebView.getSettings().setDomStorageEnabled(true); 
		mWebView.getSettings().setDatabaseEnabled(true);
		//mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "mAndroid");
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			mWebView.getSettings().setDatabasePath("/data/data/" + mWebView.getContext().getPackageName() + "/databases/");
		} else {
			String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath(); 
			mWebView.getSettings().setDatabasePath(databasePath);
		}
		
		mWebView.setWebViewClient(new MyWebViewClient());
		
		mWebView.loadUrl("file:///android_asset/main.html");
		
		logo = (ImageView) findViewById(R.id.logo);
		
		mFm = getFragmentManager();
		
		main = findViewById(R.id.main);
		frame = (FrameLayout) findViewById(R.id.frame);
		
		mHandler.sendEmptyMessageDelayed(0, 1000);
	}
	

	// show sub menu
	private void showSubmenu() {
		main.setVisibility(View.GONE);
		frame.setVisibility(View.VISIBLE);
	}
	
	private void hideSubmenu() {
		main.setVisibility(View.VISIBLE);
		frame.setVisibility(View.GONE);
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
			mFragment = new BoardListFragment();
			mFm.beginTransaction().replace(R.id.frame, mFragment).commit();
			showSubmenu();
			return true;
		} else if (id == R.id.menu_email) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/html");
			String[] mailto = { "vangsang@naver.com" };
			intent.putExtra(Intent.EXTRA_EMAIL, mailto);
			intent.putExtra(Intent.EXTRA_SUBJECT, "개발자에게 문의");
			//intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

			startActivity(Intent.createChooser(intent, "Send Email"));
			return true;
		} 
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if(mFragment == null) {
			builder.setTitle(getString(R.string.app_name))
				.setMessage("종료하시겠습니까?")
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int arg1) {
								dialog.dismiss();
								finish();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
		} else {
			mFm.beginTransaction().remove(mFragment);
			mFragment = null;
			hideSubmenu();
		}
	}
	
	class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			//mProgressBar.setVisibility(View.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			//mProgressBar.setVisibility(View.INVISIBLE);
			super.onPageFinished(view, url);
		}
	}
	
/*	class MyJavaScriptInterface {
	    MyJavaScriptInterface() {
	 
	    }
	 
	    public void setItem(String key) {
	    	PreferenceUtil.instance(MainActivity.this).setItem(key);
	    }
	    
	    public String getItem(String key) {
	    	return PreferenceUtil.instance(MainActivity.this).getItem(key);
	    }
	}  */ 
}