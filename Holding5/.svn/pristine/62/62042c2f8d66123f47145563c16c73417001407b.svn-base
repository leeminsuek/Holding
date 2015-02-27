package com.br.holding5.sc014;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.ms.Common;
import com.br.holding5.sc001.SC001;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData;
import com.brainyx.holding5.R;
import com.brainyxlib.BrDateManager;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.SharedManager;

public class SC00117 extends BaseActivity implements OnClickListener {

	private int[] mCheckbox = { R.id.soundcheck, R.id.vibcheck,
			R.id.nopushcheck, R.id.mannercheck , R.id.nosoundcheck};
	TextView textversion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.s00117);

		init();
	}

	private void init() {
		try {
			for (int i = 0; i < mCheckbox.length; i++) {
				View v = findViewById(mCheckbox[i]);
				v.setTag(i);
				v.setOnClickListener(this);
				
			}

			findViewById(R.id.header_back_button).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			findViewById(R.id.time1).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(((CheckBox)findViewById(mCheckbox[3])).isChecked()){
						BrDateManager.getInstance().CallTimePicker(SC00117.this, new BrDateManager.onTimeSelected() {
							@Override
							public void onTimeSelectedListner(String ampm, int hour, int minute) {
								if(ampm.equals("오전")){
									SharedManager.getInstance().setString(SC00117.this, Global.Shared_sttime, "1_" + String.valueOf(hour)+"_" + String.valueOf(minute));
								}else{
									hour = hour - 12;
									SharedManager.getInstance().setString(SC00117.this, Global.Shared_sttime, "2_" + String.valueOf(hour)+"_" + String.valueOf(minute));
								}
								((TextView)findViewById(R.id.time1)).setText("시작시간  " + ampm+BrDateManager.getInstance().ReturnZero(hour) + ":" +BrDateManager.getInstance().ReturnZero(minute));
								Log.e("", "");
							}
						});
					}
				}
			});
			findViewById(R.id.time2).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(((CheckBox)findViewById(mCheckbox[3])).isChecked()){
						BrDateManager.getInstance().CallTimePicker(SC00117.this, new BrDateManager.onTimeSelected() {
							@Override
							public void onTimeSelectedListner(String ampm, int hour, int minute) {

								if(ampm.equals("오전")){
									SharedManager.getInstance().setString(SC00117.this, Global.Shared_endtime, "1_" + String.valueOf(hour)+"_" + String.valueOf(minute));
								}else{
									hour = hour - 12;
									SharedManager.getInstance().setString(SC00117.this, Global.Shared_endtime, "2_" + String.valueOf(hour)+"_" + String.valueOf(minute));
								}
								((TextView)findViewById(R.id.time2)).setText("종료시간  " + ampm+BrDateManager.getInstance().ReturnZero(hour) + ":" +BrDateManager.getInstance().ReturnZero(minute));
								Log.e("", "");
							}
						});
					}
				}
			});
			findViewById(R.id.layout8).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					BrUtilManager.getInstance().ShowDialog2btn(SC00117.this, getString(R.string.app_name), "로그아웃 하시겠습니까?", new BrUtilManager.dialogclick() {
						@Override
						public void setondialogokclick() {
							//아이디와 비번 삭제후 로그인화면으로이동
							SendOut(true);
						}

						@Override
						public void setondialocancelkclick() {}
					});
				}
			});

			findViewById(R.id.layout9).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							BrUtilManager.getInstance().ShowDialog2btn(SC00117.this, getString(R.string.app_name), "계정을 삭제하시겠습니까?", new BrUtilManager.dialogclick() {
								@Override
								public void setondialogokclick() {
									//아이디와 비번 삭제후 로그인화면으로이동 // 서버api호출해야할듯
									SendOut(false);
								}

								@Override
								public void setondialocancelkclick() {}
							});
						}
					});

			((TextView)findViewById(R.id.textversion)).setText(BrUtilManager.getInstance().GetVersion(this));

			
			setView();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	private void SendOut(final boolean flag){
		try {

			HashMap<String, Object> params = new HashMap<String, Object>();
			if(flag){
				params.put(HttpUtil.KEY_URL, Global.URL_LOGOUT);
			}else{
				params.put(HttpUtil.KEY_URL, Global.URL_MEMBERDEL);	
			}

			String userId = myApp.getUserInfo().getUserId();
			params.put("userId", userId);
			//params.put("파일key", new File("파일경로"));
			HttpUtil.getInstance().requestHttp(this, params,new OnAfterParsedData() {
				@Override
				public void onResult(boolean result, String resultData) {
					try {
						if(result){ /// 종료하면 여기로 탑니다
							//arraylist = HappyVo.PasingJson(resultData, arraylist);
							//여기서 저장된 id와 비번 삭제한다
							//SharedManager.getInstance().setString(context, valuekey, value)
							Log.e("", "");
						}else{}	
					} catch (Exception e) {
					}finally{ /// 끝나면 핸들러로 화면셋팅
						if(flag) {
							mActionHandler.sendEmptyMessage(2);
						}
						else {
							mActionHandler.sendEmptyMessage(1);
						}

					}
				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	private void setView(){
		try {
			for (int i = 0; i < mCheckbox.length; i++) {
				setCheckbox(i);
			}
				//(1_2_4);
			String st = SharedManager.getInstance().getString(SC00117.this, Global.Shared_sttime);
			String end = SharedManager.getInstance().getString(SC00117.this, Global.Shared_endtime);
			if(st.equals("")) {
				((TextView)findViewById(R.id.time1)).setText("시작시간  " + "오전"+ "12" + ":" + "00");
			}
			else {
				String[] split = st.split("_");
				String ampm = split[0].equals("1")?"오전" : "오후";
				String hour = 	BrDateManager.getInstance().ReturnZero(Integer.parseInt(split[1]));
				String minute = BrDateManager.getInstance().ReturnZero(Integer.parseInt(split[2]));
				((TextView)findViewById(R.id.time1)).setText("시작시간  " + ampm+ hour + ":" + minute);
			}
			if(end.equals("")) {
				((TextView)findViewById(R.id.time2)).setText("종료시간  " + "오전"+ "06" + ":" + "00");
			}
			else {
				String[] split2 = end.split("_");
				String ampm2 = split2[0].equals("1")?"오전" : "오후";
				String hour2 = BrDateManager.getInstance().ReturnZero(Integer.parseInt(split2[1]));
				String minute2 = BrDateManager.getInstance().ReturnZero(Integer.parseInt(split2[2]));
				((TextView)findViewById(R.id.time2)).setText("종료시간  " + ampm2+ hour2 + ":" + minute2);
			}
//			String[] split = st.split("_");
//			String[] split2 = end.split("_");
//			String ampm = split[0].equals("1")?"오전" : "오후";
//			String ampm2 = split2[0].equals("1")?"오전" : "오후";
//			String hour = 	BrDateManager.getInstance().ReturnZero(Integer.parseInt(split[1]));
//			String hour2 = BrDateManager.getInstance().ReturnZero(Integer.parseInt(split2[1]));
//			String minute = BrDateManager.getInstance().ReturnZero(Integer.parseInt(split[2]));
//			String minute2 = BrDateManager.getInstance().ReturnZero(Integer.parseInt(split2[2]));
//			((TextView)findViewById(R.id.time1)).setText("시작시간  " + ampm+ hour + ":" + minute);
//			((TextView)findViewById(R.id.time2)).setText("종료시간  " + ampm2+ hour2 + ":" + minute2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setCheckbox(int index){
		try {
			switch(index){
			case 0:
				((CheckBox)findViewById(mCheckbox[index])).setChecked(SharedManager.getInstance().getBoolean(this, Global.Shared_Sound));	
				break;
			case 1:
				((CheckBox)findViewById(mCheckbox[index])).setChecked(SharedManager.getInstance().getBoolean(this, Global.Shared_vib));	
				break;
			case 2:
				((CheckBox)findViewById(mCheckbox[index])).setChecked(SharedManager.getInstance().getBoolean(this, Global.Shared_nopush));	
				break;
			case 3:
				((CheckBox)findViewById(mCheckbox[index])).setChecked(SharedManager.getInstance().getBoolean(this, Global.Shared_manner));	
				break;
			case 4:
				((CheckBox)findViewById(mCheckbox[index])).setChecked(SharedManager.getInstance().getBoolean(this, Global.Shared_nosound));
				
//				if(SharedManager.getInstance().getBoolean(this, Global.Shared_nosound)) {
//					((CheckBox)findViewById(mCheckbox[0])).setChecked(false);
//					((CheckBox)findViewById(mCheckbox[1])).setChecked(false);	
//					
//					SharedManager.getInstance().setBoolean(this, Global.Shared_Sound, false);
//					SharedManager.getInstance().setBoolean(this, Global.Shared_vib,false);
//				}
				break;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	@Override
	public void onClick(View v) {
		final int INDEX = Integer.valueOf(v.getTag() + "");
		setChecked(INDEX);
	}

	public void setChecked(int index) {
		switch (index) {
		case 0:
			SharedManager.getInstance().setBoolean(this, Global.Shared_Sound,((CheckBox)findViewById(mCheckbox[index])).isChecked());
			if(((CheckBox)findViewById(mCheckbox[index])).isChecked()) {
				((CheckBox)findViewById(mCheckbox[4])).setChecked(false);
				SharedManager.getInstance().setBoolean(this, Global.Shared_nosound,false);
			}
			break;
		case 1:
			SharedManager.getInstance().setBoolean(this, Global.Shared_vib,((CheckBox)findViewById(mCheckbox[index])).isChecked());
			if(((CheckBox)findViewById(mCheckbox[index])).isChecked()) {
				((CheckBox)findViewById(mCheckbox[4])).setChecked(false);
				SharedManager.getInstance().setBoolean(this, Global.Shared_nosound,false);
			}
			break;
		case 2:
			SharedManager.getInstance().setBoolean(this, Global.Shared_nopush,((CheckBox)findViewById(mCheckbox[index])).isChecked());
			break;
		case 3:
			SharedManager.getInstance().setBoolean(this, Global.Shared_manner,((CheckBox)findViewById(mCheckbox[index])).isChecked());
			break;
		case 4:
			SharedManager.getInstance().setBoolean(this, Global.Shared_nosound,((CheckBox)findViewById(mCheckbox[index])).isChecked());
			if(((CheckBox)findViewById(mCheckbox[index])).isChecked()) {
				((CheckBox)findViewById(mCheckbox[0])).setChecked(false);
				((CheckBox)findViewById(mCheckbox[1])).setChecked(false);
				SharedManager.getInstance().setBoolean(this, Global.Shared_Sound,false);
				SharedManager.getInstance().setBoolean(this, Global.Shared_vib,false);
			}
			break;
		}
	}

	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				try {
					SharedManager.getInstance().setString(SC00117.this,  Global.Shared_UserId, "");
					SharedManager.getInstance().setString(SC00117.this,  Global.Shared_UserPwd, "");
					SharedManager.getInstance().setString(SC00117.this,  Global.Shared_UserPwd, "");
					SharedManager.getInstance().setBoolean(SC00117.this, Global.Shared_IsLogin, false);
					
					
					Common.clearSharedValue(SC00117.this);

					BrUtilManager.getInstance().showToast(SC00117.this, "홀딩5를 이용해 주셔서 감사합니다.");
					
					AllFinish();
					startActivity(new Intent(SC00117.this, SC001.class));
					finish();

					break;					
				} catch (Exception e) {
					WriteFileLog.writeException(e);
					e.printStackTrace();
				}

			case 2:
				try {
					SharedManager.getInstance().setString(SC00117.this,  Global.Shared_UserId, "");
					SharedManager.getInstance().setString(SC00117.this,  Global.Shared_UserPwd, "");
					SharedManager.getInstance().setString(SC00117.this,  Global.Shared_UserPwd, "");
					SharedManager.getInstance().setBoolean(SC00117.this, Global.Shared_IsLogin, false);

					
					Common.clearSharedValue(SC00117.this);
					
					BrUtilManager.getInstance().showToast(SC00117.this, "로그아웃 되었습니다.");

					AllFinish();
					startActivity(new Intent(SC00117.this, SC001.class));
					finish();	
				} catch (Exception e) {
					WriteFileLog.writeException(e);
					e.printStackTrace();
				}
				

				break;
			}
		}
	};

}
