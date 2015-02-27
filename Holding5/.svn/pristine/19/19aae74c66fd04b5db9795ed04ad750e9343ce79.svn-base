 package com.br.holding5.sc014;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.s00118.S00118;
import com.br.holding5.s00121.S00121;
import com.br.holding5.sc007.SC007;
import com.br.holding5.sc007.SC007Happy;
import com.br.holding5.sc008.SC008;
import com.br.holding5.sc008.SC008_S00124;
import com.br.holding5.sc010.SC010;
import com.br.holding5.sc116.S00116;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData;
import com.brainyx.holding5.R;
import com.brainyxlib.BrDateManager;
import com.brainyxlib.BrUtilManager;

public class SC014 extends BaseActivity implements OnClickListener {

	private int[] layoutId = { R.id.layout1, R.id.layout2, R.id.layout3,
			R.id.layout4, R.id.layout5, R.id.layout6, R.id.layout7,
			R.id.layout8, R.id.layout9, R.id.layout10, R.id.layout11 };

	ImageView imgicon1;
	TextView noreadcnt,noreadcnt2,memname;
	ImageView appIcon1, appIcon2, appIcon3;
	MemberVO member = new MemberVO(); 
	private int noReadAlarm, noReadPost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.s00014);

		init();
		Send();
	}

	private void init() {
		findViewById(R.id.header_back_button).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});

		for (int i = 0; i < layoutId.length; i++) {
			View v = findViewById(layoutId[i]);
			v.setOnClickListener(this);
			v.setTag(i);
		}
		appIcon1 = (ImageView) findViewById(R.id.app_icon_1);
		appIcon2 = (ImageView) findViewById(R.id.app_icon_2);
		appIcon3 = (ImageView) findViewById(R.id.app_icon_3);

		appIcon1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					
					//대학가자 com.ucs.edutalk
					String url = "market://details?id=com.ucs.edutalk";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);					
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}

			}
		});
		
		appIcon2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String url = "market://details?id=com.ucs.police_chat";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);				
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}
			}
		});
		
		appIcon3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String url = "market://details?id=com.ucs.walkietalk";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);					
				} catch (Exception e) {
					WriteFileLog.writeException(e);
				}
			}
		});
		
		
		imgicon1 = (ImageView)findViewById(R.id.imgicon1);
		noreadcnt = (TextView)findViewById(R.id.noreadcnt);
		noreadcnt2 = (TextView)findViewById(R.id.noreadcnt2);
		memname = (TextView)findViewById(R.id.memname);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.URL_USERNOREADCOUNT);
			String userId = myApp.getUserInfo().getUserId();
			params.put("userId", userId);
			//params.put("파일key", new File("파일경로"));
			HttpUtil.getInstance().requestHttp(this, params,
					new OnAfterParsedData() {
				@Override
				public void onResult(boolean result, String resultData) {
					try {
						if(result){ /// 종료하면 여기로 탑니다
							JSONObject object;
							//JSONArray data = null;
							//data = new JSONArray(json.toString());
							object = new JSONObject(resultData.toString());
							if(object != null){
								noReadPost = object.getInt("noReadPost");
								noReadAlarm = object.getInt("noReadAlarm");
								
								
							}
						}else{}	
					} catch (Exception e) {
					}finally{ /// 끝나면 핸들러로 화면셋팅
						mActionHandler.sendEmptyMessage(3);
					}
				}
			});
			if(Global.mMoreRefreshProfile == true) {
				Send();
				Global.mMoreRefreshProfile = false;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	
		
	}

	public void Send() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.URL_GETPROFILE);
			String userId = myApp.getUserInfo().getUserId();
			params.put("userId", userId);
			//params.put("파일key", new File("파일경로"));
			HttpUtil.getInstance().requestHttp(this, params,
					new OnAfterParsedData() {
				@Override
				public void onResult(boolean result, String resultData) {
					try {
						if(result){ /// 종료하면 여기로 탑니다
							member = MemberVO.PasingJson(resultData, member);
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

	public void SetView(){
		try {
			if(member.getMemLvl() == 1){
				String[] birth = member.getBirthDay().split("-");
				int age = BrDateManager.getInstance().GetAge(Integer.valueOf(birth[0]), Integer.valueOf(birth[1]), 1);
				if(member.getSex() == 1){
					setMenDreamPhoto(age);
				}else{
					setGirlDreamPhoto(age);
				}
				((RelativeLayout)findViewById(R.id.layout8)).setVisibility(View.VISIBLE);
			}else{
				if(member.getHappyPhoto() !=  null && member.getHappyPhoto().length() > 5){
					if(member.getHappyPhoto().startsWith("http")) {
						myApp.imageloder.displayImage(member.getHappyPhoto(), imgicon1);
					}
					else {
						myApp.imageloder.displayImage(Global.host + member.getHappyPhoto(), imgicon1);
					}
					
				}else{

				}
				
				
				((RelativeLayout)findViewById(R.id.layout8)).setVisibility(View.GONE);
				
				if(member.getMemLvl() > 1 && member.getMemLvl() <= 4){
					((RelativeLayout)findViewById(R.id.layout9)).setVisibility(View.VISIBLE);
					((RelativeLayout)findViewById(R.id.layout10)).setVisibility(View.VISIBLE);
				}
				else {
					((RelativeLayout)findViewById(R.id.layout9)).setVisibility(View.GONE);
					((RelativeLayout)findViewById(R.id.layout10)).setVisibility(View.GONE);
				}
			}
//			noreadcnt.setText(String.valueOf(member.getNoReadAlarm()));
//			noreadcnt2.setText(String.valueOf(member.getNoReadPost()));
			memname.setText(String.valueOf(member.getNickName()));
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	private void setMenDreamPhoto(int age){
		if(age <= 12 ){
			imgicon1.setBackgroundResource(R.drawable.prf_img_01_m);
		}else if(age > 12 && age <=15){
			imgicon1.setBackgroundResource(R.drawable.prf_img_02_m);
		}else if(age > 15 && age <= 18){
			imgicon1.setBackgroundResource(R.drawable.prf_img_03_m);
		}else if(age > 18 && age <= 39){
			imgicon1.setBackgroundResource(R.drawable.prf_img_04_m);
		}else if(age >= 40){
			imgicon1.setBackgroundResource(R.drawable.prf_img_05_m);
		}
	}

	private void setGirlDreamPhoto(int age){
		if(age <= 12 ){
			imgicon1.setBackgroundResource(R.drawable.prf_img_01_g);
		}else if(age > 12 && age <=15){
			imgicon1.setBackgroundResource(R.drawable.prf_img_02_g);
		}else if(age > 15 && age <= 18){
			imgicon1.setBackgroundResource(R.drawable.prf_img_03_g);
		}else if(age > 18 && age <= 39){
			imgicon1.setBackgroundResource(R.drawable.prf_img_04_g);
		}else if(age >= 40){
			imgicon1.setBackgroundResource(R.drawable.prf_img_05_g);
		}
	}

	@Override
	public void onClick(View v) {
		final int INDEX = Integer.valueOf(v.getTag() + "");
		NextActivity(INDEX);
	}

	private void NextActivity(int index) {
		try {
			switch (index) {
			//알림
			case 1:
//				BrUtilManager.getInstance().showToast(SC014.this, "푸쉬 알림은 아직 미진행상태입니다.");
				StartAc(S00116.class.getName());
				break;
				//설정
			case 2:
				StartAc(SC00117.class.getName());
				break;
				//공지사항
			case 3:
				StartAc(S00118.class.getName());
				break;
				//프로필
			case 0:
				StartAc(SC00115.class.getName());
				break;
				//이벤트 입력
			case 4:
				StartAc(SC007.class.getName());
				break;
				//우체통
			case 5:
				StartAc(SC010.class.getName());
				break;
				//운영자에게 건의하기
			case 6:
				StartAc(S00121.class.getName());
				break;
				//해피인 신청
			case 7:
				StartAc(SC007Happy.class.getName());
				break;
				//격려/축하 메세지 입력
			case 8:
				StartAc(SC008.class.getName());
				break;
			case 9:		
				StartAc(SC008_S00124.class.getName());
				break;
				//
			case 10:
				StartAc(S00125.class.getName());
				break;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	private void StartAc(String classname){
		try {
			Intent intent = new Intent();
			intent.putExtra("member", member);
			intent.setClassName(this, classname);
			startActivity(intent);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	public Handler mActionHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				SetView();
				break;
			case 3:
				noreadcnt.setText(noReadAlarm+"");
				noreadcnt2.setText(noReadPost+""); 
				break;
			}
			
		}
	};

}
