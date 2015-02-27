package com.br.holding5.s00121;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.s11113.S11113;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;

/**
 * 운영자 건의 게시판 
 * 리스트조회
 * @author win
 *
 */
public class S00121 extends BaseActivity {
	//	static final String sURL = "http://192.168.1.159:8080";

	private ImageButton backbtn;
	private ImageButton writebtn;
	private ListView listview;
	private S00121_ListAdapter listAdaper;
	private ArrayList<ProposalVO> listItems;
	private int mPageIndex = 1;
	private boolean mListLock=true;

	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s00121);

			backbtn = (ImageButton) findViewById(R.id.backBtn);
			writebtn = (ImageButton) findViewById(R.id.write_btn); 

			listview =(ListView) findViewById(R.id.s00121_list);
			listItems = new ArrayList<ProposalVO>();

			listAdaper = new S00121_ListAdapter(this, listItems, myApp.imageloder, myApp.options, myApp);
			listview.setAdapter(listAdaper);

			listview.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {

				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {

					int count = totalItemCount - visibleItemCount;

					if(firstVisibleItem >= count && totalItemCount != 0 && mListLock == false) {
						mListLock = true;
						mPageIndex++;
						requestProposalList();
					}

				}
			});

			backbtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});

			writebtn.setOnClickListener( new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(S00121.this, S11113.class);
					startActivityForResult(intent, 1000);

				}
			});
			listItems.clear();
			requestProposalList();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}

	private void requestProposalList() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put( "page", mPageIndex );

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//			dialog.show();	

			aquery.progress(dialog).ajax(Global.PROPOSAL_LIST, params, JSONObject.class, this, "proposalListCallBack");
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void proposalListCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			JSONArray json;
//			listItems.clear();

			Log.d("sss", jsonObject.toString());
			System.out.println(jsonObject.toString() );

			if(!jsonObject.isNull("data")) {
				json = jsonObject.getJSONArray("data");

				for(int i = 0; i < json.length() ; i ++) {
					ProposalVO provo = new ProposalVO();
					JSONObject item = json.getJSONObject(i);

					int seq = item.getInt( "seq");
					String proposalId = item.getString( "proposalId");
					String nickName = item.getString( "nickName");
					String happyPhoto = item.getString( "happyPhoto");
					String contents = item.getString("contents");
					String sendTime = item.getString( "sendTime");
					int sex = item.getInt("sex");
					int memLvl = item.getInt("memLvl");
					String birthDay = item.getString("birthDay");



					provo.setSex(sex);
					provo.setBirthDay(birthDay);
					provo.setMemLvl(memLvl);
					provo.setSeq(seq);
					provo.setProposalId(proposalId);
					provo.setNickName(nickName);
					provo.setHappyPhoto(happyPhoto);
					provo.setSendTime(sendTime);
					provo.setContents(contents);

					listItems.add(provo);
				}

				listAdaper.update();
				if(listItems.size() % 20 == 0) {
					mListLock = false;
				}
				else {
					mListLock = true;
				}
			}
			else {
				BrUtilManager.getInstance().showToast(this, "데이터를 찾을수 없습니다.");
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);
			Log.d("asd", resultCode+"");
			if(requestCode == 1000) {
				if(resultCode == 1) {
					listItems.clear();
					requestProposalList();
				}else{

				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

}
