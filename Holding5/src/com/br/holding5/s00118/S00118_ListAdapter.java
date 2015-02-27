package com.br.holding5.s00118;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent; 
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.holding5.WriteFileLog;
import com.br.holding5.ms.Common;
import com.br.holding5.s00114.S00114;
import com.brainyx.holding5.R; 

/**
 * 공지사항 리스트 조회( 아이템 )
 * @author win
 *
 */
public class S00118_ListAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<NoticeVO> mItems;
	private LayoutInflater mInflater;

	private SparseArray<WeakReference<View>> mViewArray;

	public S00118_ListAdapter(Context context, ArrayList<NoticeVO> items) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mItems = items;

		this.mViewArray = new SparseArray<WeakReference<View>>(items.size());
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public NoticeVO getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			if(mViewArray != null && mViewArray.get(position) != null) {
				convertView = mViewArray.get(position).get();
				if(convertView != null)
					return convertView;
			}

			try {
				final NoticeVO item = getItem(position);
				String content = item.getContents().toString();
				String sendTime = item.getSendTime().toString();
				
				content = content.replaceAll("\n", " ");
				content = content.trim();
				
				if(content.length() > 40) {
					content = content.substring(0,40);
					content += "[ ... ]";
				}
				
				convertView = mInflater.inflate(R.layout.s00118_item, null);
							
				TextView date = (TextView)convertView.findViewById(R.id.date_text);
				TextView contents = (TextView)convertView.findViewById(R.id.contents_text);
				
				contents.setText(content);
				date.setText(Common.CreateDataWithCheck(sendTime));
				
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext, S00114.class);
						intent.putExtra("seq", item.getSeq());
						intent.putExtra("contents", item.getContents());
						intent.putExtra("send_time", item.getSendTime());
						
						((Activity)mContext).startActivityForResult(intent, 1000);
					}
				});
				
				
			} finally {
				mViewArray.put(position, new WeakReference<View>(convertView));
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		return convertView;
	}
	
	public void update() {
		try {
			mViewArray.clear();
			notifyDataSetChanged();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
}

