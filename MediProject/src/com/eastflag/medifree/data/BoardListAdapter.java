package com.eastflag.medi.data;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastflag.medi.R;

public class BoardListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<BoardVO> mBoardList;
	
	public BoardListAdapter(Context context, ArrayList<BoardVO> boardList) {
		mContext = context;
		mBoardList = boardList;
	}
	
	public void setData(ArrayList<BoardVO> boardList) {
		mBoardList = boardList;
	}

	@Override
	public int getCount() {
		return mBoardList.size();
	}

	@Override
	public Object getItem(int position) {
		return mBoardList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = View.inflate(mContext, R.layout.boardlist_row, null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvTitle.setText(mBoardList.get(position).title);
		holder.tvName.setText(mBoardList.get(position).user_name);
		holder.tvDate.setText(mBoardList.get(position).create_date);
		
		return convertView;
	}

	class ViewHolder {
		TextView tvTitle;
		TextView tvName;
		TextView tvDate;
	}
}
