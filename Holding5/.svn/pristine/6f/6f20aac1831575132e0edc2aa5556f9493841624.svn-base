package com.br.holding5.sc008;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;

public class SC008_S11115 extends BaseActivity{
	
	Context context;
	ImageView imageButton_back;
	EditText editText_ment;
	ImageButton imageButton_camera;
	ImageButton imageButton_gallary;
	ImageButton imageButton_audio;
	ImageButton imageButton_video;
	Button button_reg;
	TextView textView_ment;
	
	String userId = "";
	int msgType = 0;
	int kind = 0;
	int religion = 0;
	String writeTime = "";
	String contents = "";
	String photoName1 = "";
	String photoName2 = "";
	String photoName3 = "";
	String photoName4 = "";
	String photoName5 = "";
	String movName = "";
	String audioName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s11115);
			
			getParam();
			init();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		
	}
	
	private void getParam() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		try {
			userId = intent.getExtras().getString("userId");
			msgType = intent.getExtras().getInt("msgType");
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	private void init() {
		try {
			context = this;
			imageButton_back = (ImageView)findViewById(R.id.imageButton_back);
			editText_ment = (EditText)findViewById(R.id.editText_ment);
			imageButton_camera = (ImageButton)findViewById(R.id.imageButton_camera);
			imageButton_gallary = (ImageButton)findViewById(R.id.imageButton_gallary);
			imageButton_audio = (ImageButton)findViewById(R.id.imageButton_audio);
			imageButton_video = (ImageButton)findViewById(R.id.imageButton_video);
			textView_ment = (TextView)findViewById(R.id.textView_ment);
			button_reg = (Button)findViewById(R.id.button_reg);
			
			if(msgType == 1){
				textView_ment.setText(R.string.sc008_happytop_1);
			}else if(msgType == 2){
				textView_ment.setText(R.string.sc008_happytop_2);
			}else if(msgType == 3){
				textView_ment.setText(R.string.sc008_happytop_3);
			}else if(msgType == 4){
				textView_ment.setText(R.string.sc008_happytop_4);
			}else if(msgType == 5){
				textView_ment.setText(R.string.sc008_happytop_5);
			}else if(msgType == 6){
				textView_ment.setText(R.string.sc008_happytop_6);
			}else if(msgType == 7){
				textView_ment.setText(R.string.sc008_happytop_7);
			}else if(msgType == 8){
				textView_ment.setText(R.string.sc008_happytop_8);
			}else{
				textView_ment.setText(R.string.sc008_happytop_1);
			}
			
			
			//listener
			//////////////////////////////////////////////////////////////////////////////
			
			imageButton_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			
			imageButton_camera.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//카메라
					
					
				}
			});
			
			imageButton_gallary.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//갤러리
					
					
				}
			});
			
			imageButton_audio.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//녹음
					
				}
			});
			
			imageButton_video.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//영상
					
				}
			});
			
			button_reg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(editText_ment.getText().toString().equals("")){
						BrUtilManager.getInstance().showToast(context, "등록된 내용이 없습니다");
					}else{
						happyinSend(); 
					}
					
					
							
				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		
	}
	
	
	//희망메세지 등록하기
	private void happyinSend() {
		
		try {
			SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			Date todayDate = cal.getTime();
			writeTime = sdfNow.format(todayDate);
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("msgType", msgType);
			params.put("kind", kind);
			params.put("religion", religion);
			params.put("writeTime", writeTime);
			params.put("contents", editText_ment.getText().toString());
			params.put("photoName1", photoName1);
			params.put("photoName2", photoName2);
			params.put("photoName3", photoName3);
			params.put("photoName4", photoName4);
			params.put("photoName5", photoName5);
			params.put("movName", movName);
			params.put("audioName", audioName);
					
			AQuery aquery = new AQuery(this);
			
			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.HAPPYIN_INSERTMESSAGE, params, JSONObject.class, this, "happyinBaseCallBack");
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}

	public void happyinBaseCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				BrUtilManager.getInstance().showToast(this, message);
				finish();
			}else {
				BrUtilManager.getInstance().showToast(this, message);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
}
