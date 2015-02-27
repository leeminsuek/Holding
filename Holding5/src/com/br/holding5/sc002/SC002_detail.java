package com.br.holding5.sc002;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.brainyx.holding5.R;

public class SC002_detail extends Activity{

	private ImageButton mBackBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.sc002_detail);
			
			
			mBackBtn = (ImageButton) findViewById(R.id.detail_back_btn);
			
			Intent resultIntent = getIntent();
			int seq = resultIntent.getIntExtra("seq", 0);
			requestGetDetail(seq);
			mBackBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		
	}
	
	
	private void requestGetDetail(int seq) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", seq+"");
			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_DETAIL, params, JSONObject.class, this, "boardDetailCallBack");			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void boardDetailCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		
		try {
			JSONObject item = jsonObject.getJSONObject("data");
			Log.d("JSON", item.toString());
			
			String contents = item.getString("contents");
			String birthDay = item.getString("birthDay");
			int unknown = item.getInt("unknown");
			int seriousLevel = item.getInt("seriousLevel");
			int deleted = item.getInt("deleted");
			int kind = item.getInt("kind");
			int religion = item.getInt("religion");
			String writeTime = item.getString("writeTime");
			int memLvl = item.getInt("memLvl");
			String nickName = item.getString("nickName");
			int repotCnt = item.getInt("repotCnt");
			String userId = item.getString("userId");
			int sex = item.getInt("sex");
			int likeCnt = item.getInt("likeCnt");
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		
	}
}