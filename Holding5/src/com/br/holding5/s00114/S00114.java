package com.br.holding5.s00114;

import java.util.HashMap;

import org.json.JSONObject;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.sc014.MemberVO;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData;
import com.brainyx.holding5.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 공지사항 
 * 상세화면
 * @author win
 *
 */
public class S00114 extends Activity{
	private int 			mSeq;
	private String 			mContents;
	private String 			mSendTime;
	private TextView 		mTextview2;
	private ImageButton		mBackBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.s00114);
			
			mTextview2 = (TextView) findViewById(R.id.textView2);
			mBackBtn = (ImageButton) findViewById(R.id.backBtn);
			
			Intent resultIntent = getIntent();
			mSeq = resultIntent.getIntExtra("seq", 0);
//			mContents = resultIntent.getStringExtra("contents");
//			mSendTime = resultIntent.getStringExtra("send_time");
			
			Log.d("detail : ", mSeq + "");
//			Log.d("detail : ", mContents );
//			Log.d("detail : ", mSendTime );

//			mTextview2.setText( mContents );
			
			mBackBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			
			requestNoticeDetail();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	private void setView() {
		try {
			mTextview2.setText(mContents);			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	
	private void requestNoticeDetail() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.NOTICE_DETAIL);
			params.put("seq", mSeq);
			//params.put("파일key", new File("파일경로"));
			HttpUtil.getInstance().requestHttp(this, params,
					new OnAfterParsedData() {
				@Override
				public void onResult(boolean result, String resultData) {
					try {
						if(result){ /// 종료하면 여기로 탑니다
							parsingJson(resultData);
							Log.e("", "");
						}else{}	
					} catch (Exception e) {
					}finally{ /// 끝나면 핸들러로 화면셋팅
						mActionHandler.sendEmptyMessage(1);
					}
				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	private void parsingJson(String json) {
		try {
			JSONObject object;
			object = new JSONObject(json.toString());
			if(object != null){
				mSendTime = object.getString("sendTime");
				mContents = object.getString("contents");
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				setView();
				break;
			}
		}
	};
}
