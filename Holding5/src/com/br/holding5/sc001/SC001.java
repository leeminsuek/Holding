package com.br.holding5.sc001;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.sc002.S00013;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData2;
import com.brainyx.holding5.GCMPushProc;
import com.brainyx.holding5.PushVO;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.dialogclick;
import com.brainyxlib.SharedManager;
import com.google.android.gcm.GCMRegistrar;

public class SC001 extends BaseActivity implements OnClickListener{

	private final String GCM_SEND_ID = "95280102841";
	private EditText et_id;
	private EditText et_pwd;
	
	private final String TAG = "Duks";
	
	private int mMemLvl;
	private String mName;
	private boolean mPushYn = false;
	private PushVO mPushVO;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			
			super.onCreate(savedInstanceState);
			
			GCMPushProc.count = 1;
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.setContentView(R.layout.s00002);
			mPushYn = getIntent().getBooleanExtra("pushYn", false);
			if(mPushYn) {
				mPushVO = (PushVO) getIntent().getSerializableExtra("push");
			}
			
			String startTime = SharedManager.getInstance().getString(this, Global.Shared_sttime);
			String endTime = SharedManager.getInstance().getString(this, Global.Shared_endtime);
			boolean alarmCheck = SharedManager.getInstance().getBoolean(this, Global.Shared_nopush);
			boolean sound =  SharedManager.getInstance().getBoolean(this, Global.Shared_Sound);
			boolean maner = SharedManager.getInstance().getBoolean(this, Global.Shared_manner);
			boolean vib = SharedManager.getInstance().getBoolean(this, Global.Shared_vib);
			boolean noSound = SharedManager.getInstance().getBoolean(this, Global.Shared_nosound);
			
			if(startTime.equals("")) {
				SharedManager.getInstance().setString(this, Global.Shared_sttime, "1_12_00");
			}
			if(endTime.equals("")) {
				SharedManager.getInstance().setString(this, Global.Shared_endtime, "1_06_00");
			}
//			if(alarmCheck.equals("")) {
//				SharedManager.getInstance().setBoolean(this, Global.Shared_nopush, false);
//			}
//			if(sound.equals("")) {
//				SharedManager.getInstance().setBoolean(this, Global.Shared_Sound, false);
//			}
//			if(maner.equals("")) {
//				SharedManager.getInstance().setBoolean(this, Global.Shared_manner, false);
//			}
//			if(vib.equals("")) {
//				SharedManager.getInstance().setBoolean(this, Global.Shared_vib, false);
//			}
//			if(noSound.equals("")) {
//				SharedManager.getInstance().setBoolean(this, Global.Shared_nosound, false);
//			}
			
		//TODO 폰번호 확인 테스트폰에 번호가 없는 관계로 여기부분 주석처리해주셔야 합니다!!! TASK에 폰번호 광니, 핸드폰번호 이 두가지 부분 주석해주세용
			String phonenumber = BrUtilManager.getInstance().getPhoneNumber(this);
			if (TextUtils.isEmpty(phonenumber))
			{
				
				BrUtilManager.getInstance().ShowDialog1btn(this, getResources().getString(R.string.app_name), "본호가 없는 기기에서 홀딩5를 사용할 수 없습니다.", new dialogclick() {
					@Override
					public void setondialogokclick() {
						finish();
					}
					
					@Override
					public void setondialocancelkclick() {
						finish();
					}
				});
				return;
//				AlertDialog.Builder d = new AlertDialog.Builder(this);
//				d.setMessage("번호가 없는 기기에서 홀딩5를 사용할 수 없습니다.").setPositiveButton("확인",
//						new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						finish();
//					}
//				}).setOnCancelListener(new OnCancelListener(){
//					@Override
//					public void onCancel(DialogInterface arg0) {
//						finish();				}
//				});
//				
//				AlertDialog alert = d.create();
//				alert.show();
//				return;
			}
//			//TODO 폰번호 확인END
			
			et_id = (EditText) findViewById(R.id.et_id);
			et_id.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			
			et_pwd = (EditText) findViewById(R.id.et_pwd);
			
			findViewById(R.id.iv_login).setOnClickListener(this);
			findViewById(R.id.iv_join).setOnClickListener(this);
			
			TextView tv_find_pwd = (TextView) findViewById(R.id.tv_find_pwd);
			tv_find_pwd.setText(Html.fromHtml(getString(R.string.sc001_find_pwd_desc)));
			tv_find_pwd.setOnClickListener(this);
			
			//GCM
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);

			final String regId = GCMRegistrar.getRegistrationId(this);
			if(TextUtils.isEmpty(regId)){
				GCMRegistrar.register(this, GCM_SEND_ID);
			}else{
				
			}
			
			if (SharedManager.getInstance().getBoolean(this, Global.Shared_IsLogin))
			{
				//자동로그인
				String id = SharedManager.getInstance().getString(this, Global.Shared_UserId);
				String pwd = SharedManager.getInstance().getString(this, Global.Shared_UserPwd);
				String pushkey = SharedManager.getInstance().getString(this, Global.Shared_UserPushKey);
				sendLogin(id, pwd, pushkey);
			}
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	private void sendLogin(final String id, final String pwd, final String pushkey)
	{
		try {
			final Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "member/login");
			params.put( "userId", id );
			params.put( "pswd", pwd );
			params.put( "pushKey", pushkey );
			
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {

						@Override
						public void onResult2(JSONObject jsonObject)
								throws JSONException {
							
							Log.d(TAG, jsonObject.toString());
							
							if(jsonObject.optBoolean("result"))
							{
								JSONObject json = jsonObject.optJSONObject("data");
								if (json != null)
								{
									String delYn = json.optString("delYn");
									if ("Y".equals(delYn))
									{
										runOnUiThread(new Runnable() {
											public void run() {
												dialog.dismiss();
												
												Toast.makeText(SC001.this, "이미 탈퇴한 회원입니다.", Toast.LENGTH_LONG).show();
											}
										});
										return;
									}
										
									final String seq = json.optString("seq");
									String phone = json.optString("phone");
									String push = json.optString("pushKey");
									String birthDay = json.optString("birthDay");
									int sex = json.optInt("sex");
									int memLvl = json.optInt("memLvl");
									String school = json.optString("school");
									String location = json.optString("location");
									String name = json.optString("name");
									
									
									myApp.getUserInfo().setUserId(id);
									myApp.getUserInfo().setBirthDay(birthDay);
									myApp.getUserInfo().setLocation(location);
									myApp.getUserInfo().setMemLvl(memLvl);
									myApp.getUserInfo().setName(name);
									myApp.getUserInfo().setSchool(school);
									myApp.getUserInfo().setSex(sex);
									
//////									//TODO 핸드폰번호
									String phonenumber = BrUtilManager.getInstance().getPhoneNumber(SC001.this);
//									if (!phonenumber.equals(phone) || !pushkey.equals(push))
									if (!phonenumber.equals(phone) && !pushkey.equals(push))
									{
										//TODO
										runOnUiThread(new Runnable() {
											public void run() {
												dialog.dismiss();
												
												new OtherLoginDialog(SC001.this, new OtherLoginDialogListener(){

													@Override
													protected void onOK() {
														updatePushkey(seq);
													}
													
												}).show();
											}
										});
										return;
									}
//									//TODO 핸드폰번호 확인 END
									SharedManager.getInstance().setString(SC001.this,  Global.Shared_UserId, id);
									SharedManager.getInstance().setString(SC001.this,  Global.Shared_UserPwd, pwd);
									SharedManager.getInstance().setString(SC001.this,  Global.Shared_UserPushKey, pushkey);
									SharedManager.getInstance().setBoolean(SC001.this, Global.Shared_IsLogin, true);
									
									
									
									
									mMemLvl = json.optInt("memLvl");
									mName = json.optString("name");
								}
								runOnUiThread(new Runnable() {
									public void run() {
										dialog.dismiss();
										
										afterLogin();
									}
								});
							}
							else
							{
								final String result_text = jsonObject.optString("result_text");
								runOnUiThread(new Runnable() {
									public void run() {
										if (!TextUtils.isEmpty(result_text))
											Toast.makeText(SC001.this, result_text, Toast.LENGTH_LONG).show();
										else
											Toast.makeText(SC001.this, "로그인 실패.", Toast.LENGTH_SHORT).show();
										dialog.dismiss();
									}
								});
							}

							
						}

						@Override
						public void onResult3() {
							
						}
				
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put( "userId", id );
//		params.put( "pswd", pwd );
//		params.put( "pushKey", pushkey );
//		
//		AQuery aquery = new AQuery(this);
//
//		Dialog dialog = new Dialog(this, R.style.TransDialog);
//		dialog.setContentView(new ProgressBar(this));
//		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//		dialog.show();	
//
//		aquery.progress(dialog).ajax(Global.host + "member/login", params, JSONObject.class, this, "loginCallBack");
	}
	
	private void updatePushkey(String seq)
	{
		try {
			final String id = et_id.getText().toString();
			final String pwd = et_pwd.getText().toString();
			final String pushKey = GCMRegistrar.getRegistrationId(this);
			final String phone = BrUtilManager.getInstance().getPhoneNumber(SC001.this);
			
			final Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "member/updatePushKey");
			params.put( "seq", seq );
			params.put( "pswd", pwd );
			params.put( "pushKey", pushKey );
			params.put( "phone", phone );
			
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {

						@Override
						public void onResult2(JSONObject jsonObject)
								throws JSONException {
							
							Log.d(TAG, jsonObject.toString());
							
							if(jsonObject.optBoolean("result"))
							{
								SharedManager.getInstance().setString(SC001.this,  Global.Shared_UserId, id);
								SharedManager.getInstance().setString(SC001.this,  Global.Shared_UserPwd, pwd);
								SharedManager.getInstance().setString(SC001.this,  Global.Shared_UserPushKey, pushKey);
								SharedManager.getInstance().setBoolean(SC001.this, Global.Shared_IsLogin, true);
								
								//로그인 결과
								runOnUiThread(new Runnable() {
									public void run() {
										
										dialog.dismiss();
										afterLogin();
									}
								});
							}
							else
							{
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(SC001.this, "로그인 실패.", Toast.LENGTH_SHORT).show();
										dialog.dismiss();
										
										
										myApp.getUserInfo().setUserId("");
										myApp.getUserInfo().setBirthDay("");
										myApp.getUserInfo().setLocation("");
										myApp.getUserInfo().setMemLvl(0);
										myApp.getUserInfo().setName("");
										myApp.getUserInfo().setSchool("");
										myApp.getUserInfo().setSex(0);
									}
								});
							}

							
						}

						@Override
						public void onResult3() {
							
						}
				
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	private void afterLogin()
	{
		try {	
			if(mPushYn) {
				startActivity(new Intent(this, S00013.class)
				.putExtra("push", mPushVO)
				.putExtra("pushYn", mPushYn)
				.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));	
				finish();
				return;
			}
			
			if (mMemLvl == 2 || mMemLvl == 3 || mMemLvl == 4)
			{
				new HappyinDialog(SC001.this, mName).show();
			}
			else
			{
//				Toast.makeText(this, "로그인 완료", Toast.LENGTH_SHORT).show();
				
				
//				if(mPushYn) {
//					startActivity(new Intent(this, S00013.class).putExtra("push", mPushVO));	
//				}
//				else {
					startActivity(new Intent(this, S00013.class));	
//				}
				finish();
//				setResult(RESULT_OK);
//				finish();
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.iv_login:  //로그인
				{
					String id = et_id.getText().toString();
					String pwd = et_pwd.getText().toString();
					
					if (TextUtils.isEmpty(id) || !Patterns.EMAIL_ADDRESS.matcher(id).matches())
					{
						Toast.makeText(this, "이메일 주소가 잘못 되었습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
						return;
					}
					
					if (TextUtils.isEmpty(pwd))
					{
						Toast.makeText(this, "비밀번호가 잘못 되었습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
						return;
					}
					
					String pushkey = GCMRegistrar.getRegistrationId(this);
					sendLogin(id, pwd, pushkey);
					
				}
				break;
			case R.id.iv_join:  //신규가입
				{	
					Intent i = new Intent(this, S00011.class);
					startActivityForResult(i, 1111);
				}
				break;
			case R.id.tv_find_pwd:  //비밀번호 찾기
				{
					Intent i = new Intent(this, S00003.class);
					startActivity(i);
				}
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (requestCode == 1111)
			{
				if (resultCode == RESULT_OK)
				{
					String id = data.getStringExtra("id");
					if (!TextUtils.isEmpty(id)) {
						et_id.setText(id);
						et_id.setSelection(et_id.getText().length());
					}

					
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	
}