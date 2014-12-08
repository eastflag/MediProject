package com.eastflag.medi.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.eastflag.medi.BoardViewActivity;
import com.eastflag.medi.BoardWriteActivity;
import com.eastflag.medi.R;
import com.eastflag.medi.data.BoardListAdapter;
import com.eastflag.medi.data.BoardVO;

public class BoardListFragment extends Fragment {

	private AQuery mAq;
	private View mView;
	
	private int pageNo = 1;
	private int mTotalPage;
	private ArrayList<BoardVO> mBoardList = new ArrayList<BoardVO>();
	private BoardListAdapter mAdapter;
	
	private ListView mListBoard;
	private Button btnMore;

	public BoardListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.boardlist, null);
		mAq = new AQuery(getActivity(), mView);
		
		btnMore = (Button) mView.findViewById(R.id.btnMore);
		
		mListBoard = (ListView) mView.findViewById(R.id.listBoard);
		mAdapter = new BoardListAdapter(getActivity(), mBoardList);
		mListBoard.setAdapter(mAdapter);
		
		mView.findViewById(R.id.btnWrite).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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
				Intent intent = new Intent(getActivity(), BoardViewActivity.class);
				intent.putExtra("board_id", mBoardList.get(position).board_id);
				getActivity().startActivity(intent);
			}
		});
		
		getList();
		
		return mView;
	}
	
	private void getList() {
		String url = "http://www.javabrain.kr/api/getBoardList";
		url += "?pageNo=" + pageNo;

		mAq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>(){
			@Override
			public void callback(String url, JSONObject object, AjaxStatus status) {
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
