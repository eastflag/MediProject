package com.eastflag.medifree.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.eastflag.medifree.BoardViewActivity;
import com.eastflag.medifree.BoardWriteActivity;
import com.eastflag.medifree.R;
import com.eastflag.medifree.data.BoardListAdapter;
import com.eastflag.medifree.data.BoardVO;

public class BoardListFragment extends Fragment {

	private AQuery mAq;
	private View mView;
	
	private int pageNo = 1;
	private int mTotalPage;
	private ArrayList<BoardVO> mBoardList = new ArrayList<BoardVO>();
	private BoardListAdapter mAdapter;
	
	private ListView mListBoard;
	private Button btnMore;
	
	private int mBoardMode = 1; //1:최초 로딩, 2: 글보기(수정), 3:글쓰기
	
	private ProgressDialog mProgress;

	public BoardListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.boardlist, null);
		mAq = new AQuery(getActivity(), mView);
		
		mProgress = new ProgressDialog(getActivity());
		mProgress.setMessage("Loading...");
		
		btnMore = (Button) mView.findViewById(R.id.btnMore);
		
		mListBoard = (ListView) mView.findViewById(R.id.listBoard);
		mAdapter = new BoardListAdapter(getActivity(), mBoardList);
		mListBoard.setAdapter(mAdapter);
		
		mView.findViewById(R.id.btnWrite).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mBoardMode =3; //글쓰기 모드
				Intent intent = new Intent(getActivity(), BoardWriteActivity.class);
				getActivity().startActivity(intent);
			}
		});
		
		btnMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				++pageNo;
				getList();
			}
		});
		
		mListBoard.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mBoardMode = 2; //글보기 모드
				Intent intent = new Intent(getActivity(), BoardViewActivity.class);
				intent.putExtra("board_id", mBoardList.get(position).board_id);
				getActivity().startActivity(intent);
			}
		});
		
/*		Menu menu = null;
		getActivity().getMenuInflater().inflate(R.menu.main, menu);
		MenuItem refreshItem = menu.findItem(R.id.menu_board); 
		refreshItem.setTitle("새로고침");*/
		
		return mView;
	}
	

	@Override
	public void onResume() {
		//글쓰기에서 돌아오면 최초 페이지 로딩, 글 보기(수정)에서 돌아오면 현재글 리프레쉬
		switch(mBoardMode) {
		case 1: 
			getList();
			break;
		case 2:
			mBoardList.clear();
			pageNo = 1;
			getList();
			break;
		case 3:
			mBoardList.clear();
			pageNo = 1;
			getList();
			break;
		}
		super.onPause();
	}

	private void getList() {
		String url = "http://www.javabrain.kr/api/getBoardList";
		url += "?pageNo=" + pageNo;
		
		mProgress.show();

		mAq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
				mProgress.dismiss();
				try {
					Log.d("LDK", object.toString(1));
					mTotalPage = object.getInt("total");
					JSONArray array = object.getJSONArray("value");
					for(int i=0; i < array.length() ; ++i) {
						BoardVO board = new BoardVO();
						JSONObject json = array.getJSONObject(i);
						board.board_id = json.getInt("board_id");
						board.title = json.getString("title");
						board.user_name = json.getString("user_name");
						board.create_date = json.getString("create_date");
						mBoardList.add(board);
					}
					mAdapter.setData(mBoardList);
					mAdapter.notifyDataSetChanged();
					
					//더보기 버튼 구현
					if(pageNo < mTotalPage) {
						btnMore.setVisibility(View.VISIBLE);
						btnMore.setText("더보기(" + (pageNo+1) + "/" + mTotalPage + ")"); 
					} else {
						btnMore.setVisibility(View.GONE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
