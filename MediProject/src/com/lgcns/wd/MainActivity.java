package com.lgcns.wd;

import java.util.ArrayList;

import com.lgcns.wd.fragment._2_1_Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int FRAGMENT_AUTO = 1;
	private static final int FRAGMENT_21 = 21;

	private FragmentManager mFm;
	private Fragment mFragment;

	private LinearLayout mMainMenu;
	private TextView mSubTab;
	
	private ArrayList<String> mGroupList = null;
	private ArrayList<ArrayList<String>> mChildList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMainMenu = (LinearLayout) findViewById(R.id.main_menu);
		mSubTab = (TextView) findViewById(R.id.tv_tab);
		mFm = getFragmentManager();
		
		mListView = (ExpandableListView) findViewById(R.id.elv_list);

		mGroupList = new ArrayList<String>();
		mChildList = new ArrayList<ArrayList<String>>();

		mGroupList.add("1. 응급처방(ICU)");
		mGroupList.add("2. 소화기");
		mGroupList.add("3. 순환기");
		mGroupList.add("4. 호흡기");
		mGroupList.add("5. 신장");
		mGroupList.add("6. ");
		mGroupList.add("7. 감염(항생제)");
		mGroupList.add("8. 내분비");
		mGroupList.add("9. 기타");
		
		ArrayList<String> mChildListContent1 = new ArrayList<String>();
		mChildListContent1.add("1. 인공호흡기 초기세팅");
		mChildListContent1.add("2. 노에피네프린 세팅법");
		mChildList.add(mChildListContent1);
		
		ArrayList<String> mChildListContent2 = new ArrayList<String>();
		mChildListContent2.add("1. 간경화 사망률 구하기");
		mChildList.add(mChildListContent2);
		
		ArrayList<String> mChildListContent3 = new ArrayList<String>();
		mChildListContent3.add("1. ");
		mChildList.add(mChildListContent3);
		
		ArrayList<String> mChildListContent4 = new ArrayList<String>();
		mChildListContent4.add("1. ");
		mChildList.add(mChildListContent4);
		
		ArrayList<String> mChildListContent5 = new ArrayList<String>();
		mChildListContent5.add("1. ");
		mChildList.add(mChildListContent5);
		
		ArrayList<String> mChildListContent6 = new ArrayList<String>();
		mChildListContent6.add("1. ");
		mChildList.add(mChildListContent6);
		
		ArrayList<String> mChildListContent7 = new ArrayList<String>();
		mChildListContent7.add("1. ");
		mChildList.add(mChildListContent7);
		
		ArrayList<String> mChildListContent8 = new ArrayList<String>();
		mChildListContent8.add("1. ");
		mChildList.add(mChildListContent8);
		
		ArrayList<String> mChildListContent9 = new ArrayList<String>();
		mChildListContent9.add("1. ");
		mChildList.add(mChildListContent9);

		mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList, mChildList));

		// 그룹 클릭 했을 경우 이벤트
/*		mListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				Toast.makeText(getApplicationContext(), "g click = " + groupPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		});*/

		// 차일드 클릭 했을 경우 이벤트
		mListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
				Log.d("LDK", "g:" + groupPosition + "c:" + childPosition);
				switch(groupPosition) {
				case 0:
					break;
				case 1:
					switch(childPosition) {
					case 0:
						showSubmenu(FRAGMENT_21);
						break;
					}
				}
				return false;
			}
		});

/*		// 그룹이 닫힐 경우 이벤트
		mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						"g Collapse = " + groupPosition, Toast.LENGTH_SHORT)
						.show();
			}
		});

		// 그룹이 열릴 경우 이벤트
		mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						"g Expand = " + groupPosition, Toast.LENGTH_SHORT)
						.show();
			}
		});*/
	}

	/*
	 * Layout
	 */
	private ExpandableListView mListView;

	private void setLayout() {
		mListView = (ExpandableListView) findViewById(R.id.elv_list);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	public void onBackPressed() {
		if (mFragment == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
			mFm.beginTransaction().remove(mFragment).commit();
			mFragment = null;
			hideSubmenu();
		}
	}

	// show sub menu
	private void showSubmenu(int tab) {
		mMainMenu.setVisibility(View.GONE);
		mSubTab.setVisibility(View.VISIBLE);

		switch (tab) {
		case FRAGMENT_21:
			mFragment = new _2_1_Fragment();
			mFm.beginTransaction().replace(R.id.container, mFragment, String.valueOf(FRAGMENT_21)).commit();
			mSubTab.setText(getString(R.string.menu_21));
			break;
		}
	}

	private void hideSubmenu() {
		mMainMenu.setVisibility(View.VISIBLE);
		mSubTab.setVisibility(View.GONE);
	}
}
