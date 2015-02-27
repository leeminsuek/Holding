package com.br.holding5.sc008;

import java.util.ArrayList;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.sc003.gallery.DeleteImageView;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.dialogclick;

public class SC008_S11116 extends BaseActivity{
	
	Context context;
	ImageView imageButton_back;
	EditText editText_ment;
	TextView textView_ment;
	
	int msgType = 1;
	int msgSeq = 0;
	String userId= "";
	
	Button button_modify;
	Button button_del;
	
	DeleteImageView ImageView_pic1;
	DeleteImageView ImageView_pic2;
	DeleteImageView ImageView_pic3;
	DeleteImageView ImageView_pic4;
	DeleteImageView ImageView_pic5;
	DeleteImageView ImageView_audio;
	DeleteImageView ImageView_video;
	
	ArrayList<DeleteImageView> mImageViewArr;
	
	private HorizontalScrollView mHorizontalScrollView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s11116);
			
			getParam();
			init();
			getData();
	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
				
		
	}
	
	private void getParam() {
		Intent intent = getIntent();
		try {
			userId = intent.getExtras().getString("userId");
			msgType = intent.getExtras().getInt("msgType");
			msgSeq = intent.getExtras().getInt("msgSeq");
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	private void getData() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("msgType", msgType);
			
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
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				String data = jsonObject.getString("data");
				
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
					msgSeq = job.getInt("seq");
					String nicnName = job.getString("nickName");
					String msgType = String.valueOf(job.getInt("msgType"));
					
					editText_ment.setText(contents);
					editText_ment.setSelection(editText_ment.length());
					
					
					if(photoName1.toString().length() > 10){
						ImageView_pic1.setVisibility(View.VISIBLE);
						mHorizontalScrollView.setVisibility(View.VISIBLE);
						mImageViewArr.get(0).setPictureYn(true);
						mImageViewArr.get(0).setDeleteYn(true);
						super.myApp.imageloder.displayImage(photoName1, ImageView_pic1.getImageView());					
					}else{
						ImageView_pic1.setVisibility(View.GONE);
					}
					
					if(photoName2.toString().length() > 10){
						ImageView_pic2.setVisibility(View.VISIBLE);
						mHorizontalScrollView.setVisibility(View.VISIBLE);
						mImageViewArr.get(1).setPictureYn(true);
						mImageViewArr.get(1).setDeleteYn(true);
						super.myApp.imageloder.displayImage(photoName2, ImageView_pic2.getImageView());					
					}else{
						ImageView_pic2.setVisibility(View.GONE);
					}
					
					if(photoName3.toString().length() > 10){
						ImageView_pic3.setVisibility(View.VISIBLE);
						mHorizontalScrollView.setVisibility(View.VISIBLE);
						mImageViewArr.get(2).setPictureYn(true);
						mImageViewArr.get(2).setDeleteYn(true);
						super.myApp.imageloder.displayImage(photoName3, ImageView_pic3.getImageView());					
					}else{
						ImageView_pic3.setVisibility(View.GONE);
					}
					
					if(photoName4.toString().length() > 10){
						ImageView_pic4.setVisibility(View.VISIBLE);
						mHorizontalScrollView.setVisibility(View.VISIBLE);
						mImageViewArr.get(3).setPictureYn(true);
						mImageViewArr.get(3).setDeleteYn(true);
						super.myApp.imageloder.displayImage(photoName4, ImageView_pic4.getImageView());					
					}else{
						ImageView_pic4.setVisibility(View.GONE);
					}
					
					if(photoName5.toString().length() > 10){
						ImageView_pic5.setVisibility(View.VISIBLE);
						mHorizontalScrollView.setVisibility(View.VISIBLE);
						mImageViewArr.get(4).setPictureYn(true);
						mImageViewArr.get(4).setDeleteYn(true);
						super.myApp.imageloder.displayImage(photoName5, ImageView_pic5.getImageView());					
					}else{
						ImageView_pic5.setVisibility(View.GONE);
					}
					
					if(msgType.toString().equals("9")){
						textView_ment.setVisibility(View.GONE);
					}else{
						textView_ment.setVisibility(View.VISIBLE);
					}
					
					if(audioName.toString().length() > 10){
						ImageView_audio.setVisibility(View.VISIBLE);
						mHorizontalScrollView.setVisibility(View.VISIBLE);
						mImageViewArr.get(5).setPictureYn(true);
						mImageViewArr.get(5).setDeleteYn(true);
						mImageViewArr.get(5).setImage(R.drawable.write_add_record_bg,3);
					}else{
						ImageView_audio.setVisibility(View.GONE);
					}
					
					if(movName.toString().length() > 10){
						mImageViewArr.get(6).setPictureYn(true);
						mImageViewArr.get(6).setDeleteYn(true);
						ImageView_video.setVisibility(View.VISIBLE);
						mImageViewArr.get(6).setImage(R.drawable.write_add_movie_bg,2);
						mHorizontalScrollView.setVisibility(View.VISIBLE);
					}else{
						ImageView_video.setVisibility(View.GONE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else {
				BrUtilManager.getInstance().showToast(this, message);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}



	private void init() {
		try {
			context = this;
			imageButton_back = (ImageView)findViewById(R.id.imageButton_back);
			editText_ment = (EditText)findViewById(R.id.editText_ment);
			textView_ment = (TextView)findViewById(R.id.textView_ment);
			button_modify = (Button)findViewById(R.id.button_modify);
			button_del = (Button)findViewById(R.id.button_del);
			
			mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
			
			ImageView_pic1 = (DeleteImageView)findViewById(R.id.ImageView_pic1);
			ImageView_pic2 = (DeleteImageView)findViewById(R.id.ImageView_pic2);
			ImageView_pic3 = (DeleteImageView)findViewById(R.id.ImageView_pic3);
			ImageView_pic4 = (DeleteImageView)findViewById(R.id.ImageView_pic4);
			ImageView_pic5 = (DeleteImageView)findViewById(R.id.ImageView_pic5);
			ImageView_audio = (DeleteImageView)findViewById(R.id.ImageView_audio);
			ImageView_video = (DeleteImageView)findViewById(R.id.ImageView_video);
			
			mImageViewArr = new ArrayList<DeleteImageView>();
			
			mImageViewArr.add(ImageView_pic1);
			mImageViewArr.add(ImageView_pic2);
			mImageViewArr.add(ImageView_pic3);
			mImageViewArr.add(ImageView_pic4);
			mImageViewArr.add(ImageView_pic5);
			mImageViewArr.add(ImageView_audio);
			mImageViewArr.add(ImageView_video);
			
			for(int i = 0; i < mImageViewArr.size(); i++) {
				mImageViewArr.get(i).setVisibility(View.GONE);
			}
			
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
			
			button_modify.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(editText_ment.getText().toString().equals("")){
						BrUtilManager.getInstance().showToast(context, "등록된 글이 없습니다");
					}else{
						updateMessage();
					}
					
				}
			});
			
			button_del.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					BrUtilManager.getInstance().ShowDialog2btn(SC008_S11116.this, "알림", "정말로 삭제하시겠습니까?", new dialogclick() {
						@Override
						public void setondialogokclick() {
							delMessage();
						}
						
						@Override
						public void setondialocancelkclick() {
							return;
						}
					});
					
					
				}
			});
			
//			ImageView_pic1.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					BrUtilManager.getInstance().showToast(context, "등록된 이미지는 수정할 수 없습니다");
//				}
//			});
//			
//			ImageView_pic2.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					BrUtilManager.getInstance().showToast(context, "등록된 이미지는 수정할 수 없습니다");
//				}
//			});
//			
//			ImageView_pic3.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					BrUtilManager.getInstance().showToast(context, "등록된 이미지는 수정할 수 없습니다");
//				}
//			});
//			
//			
//			ImageView_pic4.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					BrUtilManager.getInstance().showToast(context, "등록된 이미지는 수정할 수 없습니다");
//				}
//			});
//			
//			ImageView_pic5.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					BrUtilManager.getInstance().showToast(context, "등록된 이미지는 수정할 수 없습니다");
//				}
//			});
//			
//			ImageView_audio.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					BrUtilManager.getInstance().showToast(context, "등록된 이미지는 수정할 수 없습니다");
//				}
//			});
//			
//			ImageView_video.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					BrUtilManager.getInstance().showToast(context, "등록된 이미지는 수정할 수 없습니다");
//				}
//			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		
	}
	
	private void updateMessage() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", msgSeq);
			params.put("contents", editText_ment.getText().toString());
			
			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.HAPPYIN_UPDATEMESSAGE, params, JSONObject.class, this, "happyinUpdateMsgCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	public void happyinUpdateMsgCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
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
	
	private void delMessage() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", msgSeq);
			
			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.HAPPYIN_DELETEMESSAGE, params, JSONObject.class, this, "happyinDelMsgCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	

	public void happyinDelMsgCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				
				SC008_S01117 sc008_s01117 = SC008_S01117.getInstance();
				if(sc008_s01117 != null){
					sc008_s01117.finish();
				}
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
