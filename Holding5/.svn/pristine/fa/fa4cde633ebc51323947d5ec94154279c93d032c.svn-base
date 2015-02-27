package com.br.holding5.sc010;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.ms.ResizeImageView;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData2;
import com.brainyx.holding5.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * @author shwoo
 *
 */
public class SC010Detail extends BaseActivity {

	protected static final String TAG = SC010Detail.class.getSimpleName();
	private String requestKey; 

	private TextView tvAudio;
	private TextView tvVideo;

	private TextView tvMsgType;
	private TextView tvWriteTime;
	private TextView tvContent;
	private TextView tvNickName;

	private ImageView ivProfile;
	private ImageView ivMaskProfile;
	private ImageButton ibBack;
	private ResizeImageView[] ivContents;
	private final int[] ivContents_RES = new int[]{R.id.iv_s01116_contents_1,
			R.id.iv_s01116_contents_2,R.id.iv_s01116_contents_3,R.id.iv_s01116_contents_4,R.id.iv_s01116_contents_5}; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s01116);

			setInit();
			setLayout();
			setListener();

			requestKey = getIntent().getStringExtra("seq");
			Log.d(TAG, "requestKey = " + requestKey);

			// request detail data.
			requestDetailData();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}


	/**
	 * 상세 정보 요청.
	 * @param SEQ sequence number.
	 */
	private void requestDetailData() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "s01116/" + requestKey);
			HttpUtil.getInstance().requestHttp2(this, params, new OnAfterParsedData2() {

				@Override
				public void onResult3() {


				}

				@Override
				public void onResult2(JSONObject jsonObject) throws JSONException {
					/*{
						  "resultCd": "00",
						  "result": {
						    "nick_name": "스타해피인",
						    "sex": 1,
						    "happy_photo": "\/images\/test1.png",
						    "mov_name": "::",
						    "contents": "가입축하합니다\n\n해피인테스트입니다",
						    "msg_type": 1,
						    "yearY": "1999",
						    "seq": 138,
						    "write_time": 1418383772,
						    "photo_name": [
						      "",
						      "",
						      "",
						      "",
						      ""
						    ],
						    "mem_lvl": 2,
						    "audio_name": ""
						  }
						}*/

					if(jsonObject != null)
					{
						Log.d(TAG, "jsonObject = " + jsonObject + "=====");
						if(SC010.SUCCESS_CODE.equals(jsonObject.get(SC010.KEY_RC).toString())) {
							JSONObject obj = (JSONObject) jsonObject.get("result");

							final String seq = obj.get("seq").toString();
							if(requestKey.equals(seq))
							{
								JSONArray arrPhoto = (JSONArray)obj.getJSONArray("photo_name");
								final int SIZE = arrPhoto.length();
								for(int index = 0 ; index < SIZE; index++)
								{
									final String strURL = arrPhoto.get(index).toString();
									if(strURL.length() > 10)
									{
										final ResizeImageView iv = ivContents[index];
										Log.d(TAG,"iv:"+iv.toString());
										runOnUiThread(new Runnable() {
											public void run() {
												String url = "";
												if(strURL.startsWith("http:")) {
													url = strURL;
												}
												else {
													url = Global.host + strURL;
												}
												// request image.
												iv.setVisibility(View.VISIBLE);
												myApp.imageloder.displayImage(url, iv, myApp.options, new ImageLoadingListener() {

													@Override
													public void onLoadingStarted(String arg0, View arg1) {
													}

													@Override
													public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
													}

													@Override
													public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {

													}

													@Override
													public void onLoadingCancelled(String arg0, View arg1) {
													}
												});
											}
										});


									}
								}

								if( obj.get("mem_lvl") != null)
								{
									Log.d(TAG,"mem_lvl:"+obj.get("mem_lvl").toString());

									final String level = obj.get("mem_lvl").toString();
									final int sex = Integer.valueOf(obj.get("sex").toString());	//1=m, 2=g
									final int birthY = Integer.valueOf(obj.get("yearY").toString());
									final int lvl = Integer.valueOf(level);
									Log.d("SC010Adapter", "level = " + level);
									Log.d("SC010Adapter", "sex = " + sex);
									Log.d("SC010Adapter", "birthY = " + birthY);
									Log.d("SC010Adapter", "lvl = " + lvl);

									if(lvl == 1){
										SimpleDateFormat df = new SimpleDateFormat("yyyy");
										Date currentDate = new Date();
										String resetDate = df.format(currentDate);
										int reset = Integer.valueOf(resetDate);

										Log.d("SC010Adapter", "birthY = " + birthY);
										Log.d("SC010Adapter", "reset = " + reset);
										final int chknum = reset - birthY;
										runOnUiThread(new Runnable() {
											
											@Override
											public void run() {
												if(chknum <= 12){
													if(sex == 1){
														ivProfile.setImageResource(R.drawable.prf_img_01_m);
													}else{
														ivProfile.setImageResource(R.drawable.prf_img_01_g);
													}
												}
												else if(chknum <= 15){
													if(sex == 1){
														ivProfile.setImageResource(R.drawable.prf_img_02_m);
													}else{
														ivProfile.setImageResource(R.drawable.prf_img_02_g);
													}
												}
												else if(chknum <= 18){
													if(sex == 1){
														ivProfile.setImageResource(R.drawable.prf_img_03_m);
													}else{
														ivProfile.setImageResource(R.drawable.prf_img_03_g);
													}
												}
												else if(chknum <= 39){
													if(sex == 1){
														ivProfile.setImageResource(R.drawable.prf_img_04_m);
													}else{
														ivProfile.setImageResource(R.drawable.prf_img_04_g);
													}
												}
												else{
													if(sex == 1){
														ivProfile.setImageResource(R.drawable.prf_img_05_m);
													}else{
														ivProfile.setImageResource(R.drawable.prf_img_05_g);
													}
												}



												ivMaskProfile.setImageResource(R.drawable.prf_box_bg);
												
											}
										});
										// 12, 15, 18, 39, ~
										
									}
									else {
										
										final String phtchk = obj.get("happy_photo").toString();
										if(phtchk.length() > 10){
											if(obj.get("happy_photo") != null) {
												String url = "";
												
												if(obj.get("happy_photo").toString().startsWith("http")) {
													url = obj.get("happy_photo").toString();
												}
												else {
													url = Global.host + obj.get("happy_photo").toString();
												}
												final String ivProfileUrl =  url;

												runOnUiThread(new Runnable() {
													public void run() {
														myApp.imageloder.displayImage(ivProfileUrl, ivProfile, myApp.options, new ImageLoadingListener() {
															@Override
															public void onLoadingStarted(String arg0, View arg1) {
															}

															@Override
															public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
															}

															@SuppressLint("NewApi")
															@SuppressWarnings("deprecation")
															@Override
															public void onLoadingComplete(String arg0, View v, Bitmap arg2) {
																BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), arg2);
																Drawable drawable = (Drawable)bitmapDrawable;
																if(Integer.valueOf(android.os.Build.VERSION.SDK) > 15)
																{
																	v.setBackground(drawable);

																}
																else
																{
																	v.setBackgroundDrawable(drawable);
																}

																ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.prf_box_bg));
															}

															@Override
															public void onLoadingCancelled(String arg0, View arg1) 
															{

															}

														});
														
														if(lvl == 2) {
															ivMaskProfile.setImageResource(R.drawable.happy_prf_bg_01);
														}
														else if(lvl == 3) {
															ivMaskProfile.setImageResource(R.drawable.happy_prf_bg_02);
														}
														else if(lvl == 4) {
															ivMaskProfile.setImageResource(R.drawable.happy_prf_bg_03);
														}
														else if(lvl == 5) {
															ivMaskProfile.setImageResource(R.drawable.prf_box_bg);
														}
													}
												});

											}
										}
									}

								}

								//								final String phtchk = obj.get("happy_photo").toString();
								//								if(phtchk.length() > 0){
								//									if(obj.get("happy_photo") != null) {
								//										final String ivProfileUrl =  Global.host + obj.get("happy_photo").toString();
								//										
								//										runOnUiThread(new Runnable() {
								//											public void run() {
								//												myApp.imageloder.displayImage(ivProfileUrl, ivProfile, myApp.options, new ImageLoadingListener() {
								//													@Override
								//													public void onLoadingStarted(String arg0, View arg1) {
								//													}
								//													
								//													@Override
								//													public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
								//													}
								//													
								//													@SuppressLint("NewApi")
								//													@SuppressWarnings("deprecation")
								//													@Override
								//													public void onLoadingComplete(String arg0, View v, Bitmap arg2) {
								//														BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), arg2);
								//														Drawable drawable = (Drawable)bitmapDrawable;
								//														if(Integer.valueOf(android.os.Build.VERSION.SDK) > 15)
								//														{
								//															v.setBackground(drawable);
								//															
								//														}
								//														else
								//														{
								//															v.setBackgroundDrawable(drawable);
								//														}
								//														
								//														ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.prf_box_bg));
								//													}
								//													
								//													@Override
								//													public void onLoadingCancelled(String arg0, View arg1) 
								//													{
								//														
								//													}
								//													
								//												});
								//											}
								//										});
								//																		
								//									}
								//								}
								//								else{
								//									runOnUiThread(new Runnable() {
								//										public void run() {
								//											ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.prf_box_bg));
								//										}
								//									});
								//								}


								if(obj.get("nick_name") != null)
								{
									final String sNickName = obj.get("nick_name").toString();
									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											tvNickName.setText(sNickName);

										}
									});

								}
								else
								{

								}

								if(obj.get("msg_type") != null)	{
									final String msg_type = obj.get("msg_type").toString();

									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											//tvMsgType.setText(msg_type);
											final int iType = Integer.valueOf(msg_type);
											switch (iType) {
											case 1:
												tvMsgType.setText("가입축하");
												break;
											case 2:
												tvMsgType.setText("생일축하");
												break;
											case 3:
												tvMsgType.setText("시험격려");
												break;
											case 4:
												tvMsgType.setText("성년의 날");
												break;
											case 5:
												tvMsgType.setText("추석");
												break;
											case 6:
												tvMsgType.setText("크리스마스");
												break;
											case 7:
												tvMsgType.setText("새해");
												break;
											case 8:
												tvMsgType.setText("설날");
												break;
											case 9:
											default:
												tvMsgType.setText("희망메세지");
												break;
											}
										}
									});

								}

								if(obj.get("contents") != null)	{
									final String contents = obj.get("contents").toString();

									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											tvContent.setText(contents);

										}
									});

								}


								if( obj.get("write_time") != null)
								{
									final String write_time = obj.get("write_time").toString();

									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											//tvWriteTime.setText(write_time);
											tvWriteTime.setText(com.br.holding5.ms.Common.CreateDataWithCheck(write_time));
//											long svcTime = Long.parseLong(write_time)+32400;
//											long curretUnixTime = (System.currentTimeMillis()/1000)+32400;		//millis -> seconds (korea).
//
//											long caseTime = curretUnixTime - svcTime;
//											if(caseTime < 60)
//											{
//												//set SecondsView
//												tvWriteTime.setText(caseTime+" 초전");
//											}
//											else if(caseTime < 3600)
//											{
//												//set MinutesView
//												float data = caseTime/60;
//												int time = (int) data;
//												tvWriteTime.setText(time+" 분전");
//											}
//											else if(caseTime < 86400)
//											{
//												//set HoursView
//												float data = caseTime/60/24;
//												int time = (int) data;
//												tvWriteTime.setText(time+" 시간전");
//											}
//											else
//											{
//												//set DateView (2099.12.31)
//												SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
//												Date dt = new Date(svcTime*1000);
//												tvWriteTime.setText(df.format(dt));
//											}
										}
									});
								}

								boolean audiochk = obj.has("audio_name");
								Log.d(TAG,"audio_name:"+obj.has("audio_name"));
								if(audiochk == true){
									Log.d(TAG,"audiochk:"+audiochk);
									if(obj.get("audio_name").toString().length() > 10)	{

										final String audio_name = obj.get("audio_name").toString();
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												tvAudio.setVisibility(View.VISIBLE);
												tvAudio.setTag(audio_name);
												tvAudio.setText("준비된 녹음이 있습니다.");

											}
										});
									}
								}

								boolean movechk = obj.has("mov_name");
								Log.d(TAG,"mov_name:"+obj.has("mov_name"));
								if(movechk == true){
									Log.d(TAG,"movechk:"+movechk);
									if(obj.get("mov_name").toString().length() > 10)	{
										final String mov_name = obj.get("mov_name").toString();
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												tvVideo.setVisibility(View.VISIBLE);
												String[] split = mov_name.split("::");
												tvVideo.setTag(split[0]);
												tvVideo.setText("준비된 영상이 있습니다.");

											}
										});

									}
								}

							}
						}
					}
					else
					{
						// TODO :: set error state.

					}

				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}


	private void setListener() {
		try {
			tvVideo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(v.getTag() + "");
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(uri, "video/*");
					startActivity(intent);

				}
			});

			tvAudio.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(v.getTag() + "");
					Intent intent = new Intent(Intent.ACTION_VIEW);  
					intent.setDataAndType(uri, "audio/*");  
					startActivity(intent);
				}
			});

			ibBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onBackPressed();

				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}


	/**
	 * init layout field.
	 */
	private void setLayout() {
		try {
			tvAudio 	= (TextView) findViewById(R.id.tv_s01116_audio);
			tvVideo 	= (TextView) findViewById(R.id.tv_s01116_video);
			tvNickName	= (TextView) findViewById(R.id.tv_s01116_item_nick_name);
			tvMsgType 	= (TextView) findViewById(R.id.tv_s01116_item_msg_type);
			tvWriteTime = (TextView) findViewById(R.id.tv_s01116_item_write_time);
			tvContent 	= (TextView) findViewById(R.id.tv_s01116_item_contents);
			ivMaskProfile = (ImageView) findViewById(R.id.iv_s01116_item_profile_mask);
			ivProfile 	= (ImageView) findViewById(R.id.iv_s01116_item_profile);


			ivContents = new ResizeImageView[ivContents_RES.length];
			for(int i =0; i<ivContents_RES.length; i++)
			{
				ivContents[i] = (ResizeImageView) findViewById(ivContents_RES[i]);

			}
			ibBack 		= (ImageButton) findViewById(R.id.ib_s01116_back);

		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}


	private void setInit() {


	}


	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
