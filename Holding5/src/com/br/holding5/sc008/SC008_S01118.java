package com.br.holding5.sc008;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.ms.Common;
import com.br.holding5.ms.CommonDialog;
import com.br.holding5.ms.ResizeImageView;
import com.br.holding5.ms.CommonDialog.onMenuItemSelectListener;
import com.br.holding5.sc002.MainListVO;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.dialogclick;

public class SC008_S01118 extends BaseActivity{

	private static SC008_S01118 instance = null;
	public static SC008_S01118 getInstance(){
		if(instance == null)
			instance = new SC008_S01118();
		return instance;
	}

	Context context;
	ImageView imageView_back;
	ImageView imageView_profile;
	ImageView imageView_profile_mask;
	TextView textView_nickname;
	TextView textView_msgtype;
	TextView textView_regdate;
	TextView textView_contents;
	ResizeImageView imageView_contents_1;
	ResizeImageView imageView_contents_2;
	ResizeImageView imageView_contents_3;
	ResizeImageView imageView_contents_4;
	ResizeImageView imageView_contents_5;
	TextView textView_audio;
	TextView textView_video;
	ImageButton button_footer;
	LinearLayout linearLayout_more;
	LinearLayout linearLayout_modify;
	LinearLayout linearLayout_del;

	private int mSeq;
	private int mMsgType;
	private String mUserId = "";
	private CommonDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s01118);
			mUserId = myApp.getUserInfo().getUserId();
			init();


			Intent intent = getIntent();
			mSeq = intent.getIntExtra("seq", -1);
			mMsgType = intent.getIntExtra("msgType", -1);
			if(mSeq == -1) {
				BrUtilManager.getInstance().showToast(this, "데이터를 가져오지 못했습니다.");
			}
			else {
				getData();
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		try {
			if(requestCode == 1000) {
				if(resultCode == 3) {
					finish();
				}
				else if(resultCode == 2) {
					getData();
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	public void getData() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userId", mUserId);
			params.put("msgType", mMsgType);
			params.put("seq", mSeq);

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.HAPPYIN_GETMESSAGE, params, JSONObject.class, this, "happyinGetMsgCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}


	public void happyinGetMsgCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		boolean result = jsonObject.getBoolean("result");
		String message = jsonObject.getString("message");
		if(result) {
			String data = jsonObject.getString("data");


			//			"photoName2": "http:\/\/s3-ap-northeast-1.amazonaws.com\/brainyx1\/Screenshot_2013-07-30-22-48-06.png",
			//			  "photoName1": "http:\/\/s3-ap-northeast-1.amazonaws.com\/brainyx1\/Screenshot_2013-07-22-14-52-31.png",
			//			  "photoName4": "",
			//			  "photoName3": "",
			//			  "contents": "ㅎㅎㅎㅎㅎ",
			//			  "audioName": "http:\/\/s3-ap-northeast-1.amazonaws.com\/brainyx1\/음성 녹음 025.m4a",
			//			  "movName": "::",
			//			  "photoName5": "",
			//			  "kind": 0,
			//			  "id": "test1",
			//			  "religion": 0,
			//			  "memLvl": 1,
			//			  "writeTime": "2014-12-03 17:21:36.0",
			//			  "nickName": "zzzz",
			//			  "seq": 107,
			//			  "msgType": 1


			try {
				JSONObject job = new JSONObject(data);

				String photoName1 = job.getString("photoName1");
				String photoName2 = job.getString("photoName2");
				String photoName3 = job.getString("photoName3");
				String photoName4 = job.getString("photoName4");
				String photoName5 = job.getString("photoName5");
				String contents = job.getString("contents");
				String audioName = job.getString("audioName");
				String movName = job.getString("movName");
				String kind = String.valueOf(job.getInt("kind"));
				String religion = String.valueOf(job.getInt("religion"));
				String id = job.getString("id");
				String writeTime = job.getString("writeTime");
				String memLvl = String.valueOf(job.getInt("memLvl"));
				//				msgSeq = job.getInt("seq");
				String nickName = job.getString("nickName");
				int sex = job.getInt("sex");
				int memL = Integer.parseInt(memLvl);
				String birthDay = job.getString("birthDay");
				String happyPhoto = job.getString("happyPhoto");
				//				msgType = job.getInt("msgType");

				textView_nickname.setText(nickName);
				textView_contents.setText(contents);
				
				if(memL == 2) {
					imageView_profile_mask.setImageResource(R.drawable.happy_prf_bg_01);
				}
				else if(memL == 3) {
					imageView_profile_mask.setImageResource(R.drawable.happy_prf_bg_02);
				}
				else if(memL == 4) {
					imageView_profile_mask.setImageResource(R.drawable.happy_prf_bg_03);
				}
				else {
					imageView_profile_mask.setImageResource(R.drawable.prf_box_bg);
				}

				
				
				if(memL == 1) {
					int age = Common.getAgeFromBirthday(birthDay);
					if(sex == 1) {
						if(age <= 12 ){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_01_m);
						}else if(age > 12 && age <=15){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_02_m);
						}else if(age > 15 && age <= 18){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_03_m);
						}else if(age > 18 && age <= 39){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_04_m);
						}else if(age >= 40){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_05_m);
						}
					}
					else {
						if(age <= 12 ){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_01_g);
						}else if(age > 12 && age <=15){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_02_g);
						}else if(age > 15 && age <= 18){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_03_g);
						}else if(age > 18 && age <= 39){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_04_g);
						}else if(age >= 40){
							imageView_profile.setBackgroundResource(R.drawable.prf_img_05_g);
						}
					}
//					holder.imageView_profile.setImageBitmap(Common.getProfileImage(SC008_S00124.this, tmpItem));
				}
				else if(memL == 5) {
					if(happyPhoto.startsWith("http")) {
						myApp.imageloder.displayImage(happyPhoto, imageView_profile);
					}
					else {
						myApp.imageloder.displayImage(Global.host + happyPhoto, imageView_profile);	
					}
					
					//				holder.prgImg.setImageBitmap(Common.getProfileImage(mContext, item));
				}
				else {
					if(happyPhoto.startsWith("http")) {
						myApp.imageloder.displayImage(happyPhoto, imageView_profile);
					}
					else {
						myApp.imageloder.displayImage(Global.host + happyPhoto, imageView_profile);	
					}
				}
				

				if(!photoName1.toString().equals("")){
					imageView_contents_1.setVisibility(View.VISIBLE);
					super.myApp.imageloder.displayImage(photoName1, imageView_contents_1);					
				}

				if(!photoName2.toString().equals("")){
					imageView_contents_2.setVisibility(View.VISIBLE);
					super.myApp.imageloder.displayImage(photoName2, imageView_contents_2);					
				}

				if(!photoName3.toString().equals("")){
					imageView_contents_3.setVisibility(View.VISIBLE);
					super.myApp.imageloder.displayImage(photoName3, imageView_contents_3);					
				}

				if(!photoName4.toString().equals("")){
					imageView_contents_4.setVisibility(View.VISIBLE);
					super.myApp.imageloder.displayImage(photoName4, imageView_contents_4);					
				}

				if(!photoName5.toString().equals("")){
					imageView_contents_5.setVisibility(View.VISIBLE);
					super.myApp.imageloder.displayImage(photoName5, imageView_contents_5);					
				}

				textView_msgtype.setText(Common.getCategoryKindString(Integer.parseInt(kind)));
				textView_regdate.setText(Common.CreateDataWithCheck(writeTime));

				if(!audioName.toString().equals("")){
					textView_audio.setVisibility(View.VISIBLE);
				}

				if(!movName.toString().equals("::")){
					textView_video.setVisibility(View.VISIBLE);
				}

				textView_audio.setTag(audioName);
				textView_audio.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Uri uri = Uri.parse(v.getTag().toString());
						Intent intent = new Intent(Intent.ACTION_VIEW);  
						intent.setDataAndType(uri, "audio/*");  
						context.startActivity(intent);
					}
				});

				textView_video.setTag(movName);
				textView_video.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String mov = v.getTag().toString();
						String[] split = mov.split("::");
						String movie = split[0];
						Uri uri = Uri.parse(movie);
						Intent intent = new Intent(Intent.ACTION_VIEW);  
						intent.setDataAndType(uri, "video/*");  
						context.startActivity(intent);
					}
				});


			} catch (Exception e) {
				WriteFileLog.writeException(e);
			}

		}else {
			BrUtilManager.getInstance().showToast(this, message);
		}
	}



	private void init() {
		try {
			context = this;
			imageView_back = (ImageView)findViewById(R.id.imageView_back);
			imageView_profile = (ImageView)findViewById(R.id.imageView_profile);
			textView_nickname = (TextView)findViewById(R.id.textView_nickname);
			textView_msgtype = (TextView)findViewById(R.id.textView_msgtype);
			textView_regdate = (TextView)findViewById(R.id.textView_regdate);
			textView_contents = (TextView)findViewById(R.id.textView_contents);
			imageView_contents_1 = (ResizeImageView)findViewById(R.id.imageView_contents_1);
			imageView_contents_2 = (ResizeImageView)findViewById(R.id.imageView_contents_2);
			imageView_contents_3 = (ResizeImageView)findViewById(R.id.imageView_contents_3);
			imageView_contents_4 = (ResizeImageView)findViewById(R.id.imageView_contents_4);
			imageView_contents_5 = (ResizeImageView)findViewById(R.id.imageView_contents_5);
			textView_audio = (TextView)findViewById(R.id.textView_audio);
			textView_video = (TextView)findViewById(R.id.textView_video);
			button_footer = (ImageButton)findViewById(R.id.button_footer);
			linearLayout_more = (LinearLayout)findViewById(R.id.linearLayout_more);
			linearLayout_modify = (LinearLayout)findViewById(R.id.linearLayout_modify);
			linearLayout_del = (LinearLayout)findViewById(R.id.linearLayout_del);
			imageView_profile_mask = (ImageView) findViewById(R.id.imageView_profile_mask);
			button_footer.setTag(false);


			//listener
			//////////////////////////////////////////////////////////////////////////////
			imageView_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});

			button_footer.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					boolean footerFlag = (Boolean) button_footer.getTag(); 
//					button_footer.setTag(!footerFlag);

					mDialog = new CommonDialog(SC008_S01118.this);
					mDialog.createBoardMenu(new onMenuItemSelectListener() {
						@Override
						public void onMenuItemSelected(int position) {
							mDialog.dismiss();
							createDialog(position);
						}
					});
					mDialog.show();
//					if((Boolean) button_footer.getTag()){
//						linearLayout_more.setVisibility(View.VISIBLE);
//					}else{
//						linearLayout_more.setVisibility(View.GONE);
//					}

				}
			});
			linearLayout_more.setVisibility(View.GONE);

			linearLayout_modify.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 수정하기
					Intent intent = new Intent(SC008_S01118.this, SC008_S11118.class);
					intent.putExtra("seq", mSeq);
					intent.putExtra("msgType", mMsgType);
					startActivityForResult(intent, 1000);
				}
			});

			linearLayout_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//TODO 삭제하기
					BrUtilManager.getInstance().ShowDialog2btn(SC008_S01118.this, "알림", "정말로 삭제하시겠습니까?" , new dialogclick() {
						@Override
						public void setondialogokclick() {
							delMessage(mSeq);
						}

						@Override
						public void setondialocancelkclick() {
							return;
						}
					});

				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		// TODO Auto-generated method stub
		
	}
	
	private void createDialog(int position) {
		//삭제
		if(position == 2) {
			BrUtilManager.getInstance().ShowDialog2btn(SC008_S01118.this, "알림", "게시글을 삭제하시겠습니까?",  new dialogclick() {
				
				@Override
				public void setondialogokclick() {
					// TODO Auto-generated method stub
					delMessage(mSeq);
				}
				
				@Override
				public void setondialocancelkclick() {
					// TODO Auto-generated method stub
					return;
				}
			});
		}
		//수정
		else {
			Intent intent = new Intent(SC008_S01118.this, SC008_S11118.class);
			intent.putExtra("seq", mSeq);
			intent.putExtra("msgType", mMsgType);
			startActivityForResult(intent, 1000);
		}
	}


	private void delMessage(int msgSeq) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", msgSeq);

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.HAPPYIN_DELETEMESSAGE, params, JSONObject.class, this, "hopeDelMsgCallBack");			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}


	public void hopeDelMsgCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				BrUtilManager.getInstance().showToast(this, message);
				setResult(3);
				finish();
			}else {
				BrUtilManager.getInstance().showToast(this, message);
			}	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
}

