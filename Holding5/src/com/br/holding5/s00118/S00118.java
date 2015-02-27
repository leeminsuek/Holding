package com.br.holding5.s00118;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;

/**
 * 공지사항
 * 리스트 조회
 * @author win
 *
 */
public class S00118 extends BaseActivity {
	private ImageButton backbtn;
	private ListView listview;
	private S00118_ListAdapter listAdaper;
	private ArrayList<NoticeVO> listItems;
	
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s00118);

			backbtn = (ImageButton) findViewById(R.id.backBtn);
			listview =(ListView) findViewById(R.id.s00118_list);
			
			listItems = new ArrayList<NoticeVO>();
			
			listAdaper = new S00118_ListAdapter(this, listItems);
			listview.setAdapter(listAdaper);
			
			requestNoticeList(1);
			
			backbtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	private void requestNoticeList( int page) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put( "page", page );
			
			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//			dialog.show();	

			aquery.progress(dialog).ajax(Global.NOTICE_LIST, params, JSONObject.class, this, "noticeListCallBack");		
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	
	}
	
	public void noticeListCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			JSONArray json;
			
			System.out.println(jsonObject.toString() );
			
			if(!jsonObject.isNull("data")) {
				json = jsonObject.getJSONArray("data");

				for(int i = 0; i < json.length() ; i ++) {
					NoticeVO noticevo = new NoticeVO();
					
					JSONObject item = json.getJSONObject(i);
					
					int seq = item.getInt( "seq");
					String contents = item.getString("contents");
					String sendTime = item.getString( "sendTime");
				
					noticevo.setSeq(seq);
					noticevo.setContents(contents);
					noticevo.setSendTime(sendTime);
					
					listItems.add(noticevo);
				}
				
				listAdaper.notifyDataSetChanged();
			}
			else {
				BrUtilManager.getInstance().showToast(this, "데이터를 찾을수 없습니다.");
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		
	}

	
	
}
