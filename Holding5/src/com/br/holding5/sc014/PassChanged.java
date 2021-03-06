package com.br.holding5.sc014;

import java.util.HashMap;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.SharedManager;

public class PassChanged extends BaseActivity{

	EditText input_pass1,input_pass2,input_pass3;
	
	
	String mOldPass = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
			layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
			layoutParams.dimAmount = 0.7f;
			//layoutParams.softInputMode =  WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
			getWindow().setAttributes(layoutParams);
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 다이얼로그
			setContentView(R.layout.passchanged);
			
			init();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	private void init(){
		
		try {
			findViewById(R.id.close).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			findViewById(R.id.changebtn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Send();
				}
			});
			
			input_pass1 = (EditText)findViewById(R.id.input_pass1);
			input_pass2 = (EditText)findViewById(R.id.input_pass2);
			input_pass3 = (EditText)findViewById(R.id.input_pass3);
			
			Intent intent  = getIntent();
			mOldPass = intent.getStringExtra("passwd");
//			input_pass1.setText(passwd);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	private void Send(){
		try {
			if(!BrUtilManager.getInstance().getEditTextNullCheck(input_pass1)){
				BrUtilManager.getInstance().showToast(this, "기존 비밀번호를 입력하세요");
				return;
			}
			
			if(!mOldPass.equals(input_pass1.getText().toString().trim())) {
				BrUtilManager.getInstance().showToast(this, "기존 비밀번호를 확인하세요");
				return;
			}
			
			if(!BrUtilManager.getInstance().getEditTextNullCheck(input_pass2)){
				BrUtilManager.getInstance().showToast(this, "새 비밀번호를 입력하세요");
				return;
			}
			
			if(!BrUtilManager.getInstance().getEditTextNullCheck(input_pass3)){
				BrUtilManager.getInstance().showToast(this, "새 비밀번호를 한번더 입력하세요");
				return;
			}
			
			/*if(input_pass1.getText().toString().trim().equals("")){
				
			}*/
			
			if(!input_pass2.getText().toString().trim().equals(input_pass3.getText().toString().trim())){
				BrUtilManager.getInstance().showToast(this, "비밀번호를 확인하세요");
				return;
			}
			
			if (input_pass2.getText().length() < 6 || input_pass3.getText().length() < 6)
			{
				BrUtilManager.getInstance().showToast(this, "비밀번호는 6자리 이상 입력해 주세요");
				return;
			}
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.URL_CHANGEPW);
			params.put("userId", myApp.getUserInfo().getUserId());
			params.put("oldPw",input_pass1.getText().toString().trim());
			params.put("newPw",input_pass3.getText().toString().trim());
			
			//params.put("파일key", new File("파일경로"));
			HttpUtil.getInstance().requestHttp(this, params,new OnAfterParsedData() {
						@Override
						public void onResult(boolean result, String resultData) {
							try {
								if(result){ /// 종료하면 여기로 탑니다
									mActionHandler.sendEmptyMessage(1);	
								}else{
									mActionHandler.sendEmptyMessage(2);
								}	
							} catch (Exception e) {
							}finally{ /// 끝나면 핸들러로 화면셋팅
								
							}
						}
					});
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	
	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				
				BrUtilManager.getInstance().showToast(PassChanged.this, "비밀번호가 변경되었습니다.");
				Intent intent = new Intent();
				intent.putExtra("passwd", input_pass3.getText().toString().trim());
				setResult(RESULT_OK, intent);
				finish();
				break;
			case 2:
				BrUtilManager.getInstance().showToast(PassChanged.this, "비밀번호가 변경되었습니다.");
				break;
			}
		}
	};
	
	
	
}
