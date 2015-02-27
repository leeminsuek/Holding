package com.br.holding5.sc008;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.ms.Common;
import com.br.holding5.ms.CommonDialog;
import com.br.holding5.ms.CommonDialog.onMenuItemSelectListener;
import com.br.holding5.ms.ResizeImageView;
import com.br.holding5.sc002.MainListVO;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.dialogclick;

public class SC008_S00124 extends BaseActivity{

	private static SC008_S00124 instance = null;
	public static SC008_S00124 getInstance(){
		return instance;
	}

	Context context;
	ImageView imageView_back;
	Button button_write;
	ListView listview;
	String testUserId = "";
	HopeMsg_adapter hopemsg_adapter;
	CommonDialog mDialog;
	ArrayList<MainListVO> array_list = new ArrayList<MainListVO>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s00124);
			testUserId = myApp.getUserInfo().getUserId();
			init();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			getData();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}


	private void init() {
		// TODO Auto-generated method stub
		try {
			context = this;
			imageView_back = (ImageView)findViewById(R.id.imageView_back);
			button_write = (Button)findViewById(R.id.button_write);
			listview = (ListView)findViewById(R.id.listView);
			hopemsg_adapter = new HopeMsg_adapter(SC008_S00124.this, listview);
			listview.setAdapter(hopemsg_adapter);

			//listener
			//////////////////////////////////////////////////////////////////////////////
			imageView_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			button_write.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(SC008_S00124.this, SC004_S11117.class);
					startActivity(intent);

				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}

	//희망메세지리스트받아오기
	private void getData() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userId", testUserId);
			params.put("page", 1);

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.HAPPYIN_HOPEMESSAGE, params, JSONObject.class, this, "happyinHopeCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void happyinHopeCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {

				String data = jsonObject.getString("data");
				JSONArray ja = new JSONArray(data);

				array_list.clear();

				for(int i = 0; i < ja.length(); i++){
					JSONObject job = new JSONObject();
					job = ja.getJSONObject(i);

					//						{"":"\/images\/test2.png","photoName1":"\/images\/test2.png","photoName4":"\/images\/test2.png","photoName3":"\
					//						/images\/test2.png","contents":"안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십
					//						안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안
					//						녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십안녕하세요. 글자수체크입니다. 이십 안녕
					//						하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세
					//						요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글자수체크입니다. 이십 안녕하세요. 글
					//						자수체크입니다. 이십","audioName":"\/images\/testmovie.wmv","movName":"\/images\/testmovie.wmv","photoName5":"\/image
					//						s\/test2.png","kind":1,"id":"test3","religion":0,"memLvl":0,"writeTime":"2014-11-23 01:25:53.0","seq":27,"nicnName":"","msgType":9}" +

					String photoName1 = job.getString("photoName1");
					String photoName2 = job.getString("photoName2");
					String photoName3 = job.getString("photoName3");
					String photoName4 = job.getString("photoName4");
					String photoName5 = job.getString("photoName5");
					String audioName = job.getString("audioName");
					String movName = job.getString("movName");
					String contents = job.getString("contents");
					String birthDay = job.getString("birthDay");
					
					int kind = job.getInt("kind");
					String id = job.getString("id");
					//						int religion = job.getInt("religion");
					int memLvl = job.getInt("memLvl");
					String writeTime = job.getString("writeTime");
					int seq = job.getInt("seq");
					int sex = job.getInt("sex");
					String nicnName = job.getString("nickName");
					int msgType = job.getInt("msgType");
					String happyPhoto = job.getString("happyPhoto");

					MainListVO ml = new MainListVO();
					ml.setSex(sex);
					ml.setHappyPhoto(happyPhoto);
					ml.setPhotoName1(photoName1);
					ml.setPhotoName2(photoName2);
					ml.setPhotoName3(photoName3);
					ml.setPhotoName4(photoName4);
					ml.setPhotoName5(photoName5);
					ml.setAudioName(audioName);
					ml.setBirthDay(birthDay);
					ml.setMovName(movName);
					ml.setContents(contents);
					ml.setCategory(String.valueOf(kind));
					ml.setUserId(id);
					ml.setMemLvl(memLvl);
					ml.setCreateTime(writeTime);
					ml.setSeq(seq);
					ml.setName(nicnName);
					ml.setMsgType(msgType);
					array_list.add(ml);
				}


			}else {
				BrUtilManager.getInstance().showToast(this, message);
			}

			hopemsg_adapter.notifyDataSetChanged();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}

	public class HopeMsg_adapter extends ArrayAdapter<MainListVO>{	
		//			Activity context;
		//			private ListView listView;
		private int pkgIndex = 0;

		public HopeMsg_adapter(Activity acontext, ListView alist) {
			super(context, R.layout.cell_s00124_, array_list);
			//				this.context = acontext;
			//				this.listView = alist; 

		}	


		@Override
		public int getItemViewType(int position) {		
			return 0;							
		};

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		OnClickListener onClickAudioListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(v.getTag() != null) {
						MainListVO ml = (MainListVO)v.getTag();

					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};

		OnClickListener onClickVideoListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(v.getTag() != null) {
						MainListVO ml = (MainListVO)v.getTag();

					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};

		OnClickListener onClickModListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(v.getTag() != null) {
						MainListVO ml = (MainListVO)v.getTag();

						Intent intent = new Intent(context, SC008_S11116.class);
						intent.putExtra("userId", ml.getUserId());
						intent.putExtra("msgType", 9);
						intent.putExtra("msgSeq", ml.getSeq());
						startActivity(intent);

					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};

		OnClickListener onClickDelListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(v.getTag() != null) {
						MainListVO ml = (MainListVO)v.getTag();
						delMessage(ml.getSeq());
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};

		OnClickListener onClickPopupListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(v.getTag() != null) {
						MainListVO ml = (MainListVO)v.getTag();


					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};

		public class Holder {

			TextView textView_nickname;
			TextView textView_msgtype;
			TextView textView_regdate;
			TextView textView_contents;
			ResizeImageView imageView_contents_1;
			ResizeImageView imageView_contents_2;
			ResizeImageView imageView_contents_3;
			ResizeImageView imageView_contents_4;
			ResizeImageView imageView_contents_5;
			ImageView imageView_profile;
			ImageView imageView_mask;
			TextView textView_audio;
			TextView textVIew_video;
			ImageButton button_footer;
			LinearLayout linearLayout_more;
			LinearLayout linearLayout_modify;
			LinearLayout linearLayout_del;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			final MainListVO tmpItem = array_list.get(position);
			String contents = tmpItem.getContents();

			try {
				//					LayoutInflater inflater = context.getLayoutInflater();	
				Holder holder = null;

				if(convertView == null) {
					convertView = View.inflate(context,R.layout.cell_s00124_, null);
					holder = new Holder();

					holder.textView_nickname = (TextView)convertView.findViewById(R.id.textView_nickname);
					holder.textView_msgtype = (TextView)convertView.findViewById(R.id.textView_msgtype);
					holder.textView_regdate = (TextView)convertView.findViewById(R.id.textView_regdate);
					holder.textView_contents = (TextView)convertView.findViewById(R.id.textView_contents);
					holder.imageView_contents_1 = (ResizeImageView)convertView.findViewById(R.id.imageView_contents_1);
					holder.imageView_contents_2 = (ResizeImageView)convertView.findViewById(R.id.imageView_contents_2);
					holder.imageView_contents_3 = (ResizeImageView)convertView.findViewById(R.id.imageView_contents_3);
					holder.imageView_contents_4 = (ResizeImageView)convertView.findViewById(R.id.imageView_contents_4);
					holder.imageView_contents_5 = (ResizeImageView)convertView.findViewById(R.id.imageView_contents_5);
					holder.textView_audio = (TextView)convertView.findViewById(R.id.textView_audio);
					holder.textVIew_video = (TextView)convertView.findViewById(R.id.textView_video);
					holder.button_footer = (ImageButton)convertView.findViewById(R.id.button_footer);
					holder.linearLayout_more = (LinearLayout)convertView.findViewById(R.id.linearLayout_more);
					holder.linearLayout_modify = (LinearLayout)convertView.findViewById(R.id.linearLayout_modify);
					holder.linearLayout_del = (LinearLayout)convertView.findViewById(R.id.linearLayout_del);
					holder.imageView_profile = (ImageView) convertView.findViewById(R.id.imageView_profile);
					holder.imageView_mask  = (ImageView) convertView.findViewById(R.id.imageView_mask);

					holder.textView_audio.setOnClickListener(onClickAudioListener);
					holder.textVIew_video.setOnClickListener(onClickVideoListener);
					holder.linearLayout_modify.setOnClickListener(onClickModListener);
					holder.linearLayout_del.setOnClickListener(onClickDelListener);

					convertView.setTag(holder);

				}else{
					holder = (Holder) convertView.getTag();
				}


				if(tmpItem.getUserId().equals(testUserId)) {
					holder.button_footer.setVisibility(View.VISIBLE);
				}
				else {
					holder.button_footer.setVisibility(View.INVISIBLE);	
				}
				//					holder.textView_audio.setTag(tmpItem);
				//					holder.textVIew_video.setTag(tmpItem);
				//					holder.linearLayout_modify.setTag(tmpItem);
				//					holder.linearLayout_del.setTag(tmpItem);
				if(tmpItem.getMemLvl() == 2) {
					holder.imageView_mask.setImageResource(R.drawable.happy_prf_bg_01);
				}
				else if(tmpItem.getMemLvl() == 3) {
					holder.imageView_mask.setImageResource(R.drawable.happy_prf_bg_02);
				}
				else if(tmpItem.getMemLvl() == 4) {
					holder.imageView_mask.setImageResource(R.drawable.happy_prf_bg_03);
				}
				else {
					holder.imageView_mask.setImageResource(R.drawable.prf_box_bg);
				}

				if(tmpItem.getMemLvl() == 1) {
					int age = Common.getAgeFromBirthday(tmpItem.getBirthDay());
					if(tmpItem.getSex() == 1) {
						if(age <= 12 ){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_01_m);
						}else if(age > 12 && age <=15){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_02_m);
						}else if(age > 15 && age <= 18){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_03_m);
						}else if(age > 18 && age <= 39){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_04_m);
						}else if(age >= 40){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_05_m);
						}
					}
					else {
						if(age <= 12 ){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_01_g);
						}else if(age > 12 && age <=15){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_02_g);
						}else if(age > 15 && age <= 18){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_03_g);
						}else if(age > 18 && age <= 39){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_04_g);
						}else if(age >= 40){
							holder.imageView_profile.setBackgroundResource(R.drawable.prf_img_05_g);
						}
					}
//					holder.imageView_profile.setImageBitmap(Common.getProfileImage(SC008_S00124.this, tmpItem));
				}
				else if(tmpItem.getMemLvl() == 5) {
					if(tmpItem.getHappyPhoto().startsWith("http")) {
						myApp.imageloder.displayImage(tmpItem.getHappyPhoto(), holder.imageView_profile);
					}
					else {
						myApp.imageloder.displayImage(Global.host + tmpItem.getHappyPhoto(), holder.imageView_profile);	
					}
					
					//				holder.prgImg.setImageBitmap(Common.getProfileImage(mContext, item));
				}
				else {
					if(tmpItem.getHappyPhoto().startsWith("http")) {
						myApp.imageloder.displayImage(tmpItem.getHappyPhoto(), holder.imageView_profile);
					}
					else {
						myApp.imageloder.displayImage(Global.host + tmpItem.getHappyPhoto(), holder.imageView_profile);	
					}
					
				}

				
				
				holder.button_footer.setTag(tmpItem);
				holder.button_footer.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						final MainListVO selectPosition = (MainListVO) v.getTag();
						mDialog = new CommonDialog(context);
						mDialog.createBoardMenu(new onMenuItemSelectListener() {
							@Override
							public void onMenuItemSelected(int position) {
								mDialog.dismiss();
								createDialog(position, selectPosition);
							}
						});
						mDialog.show();

					}
				});
				//					
				//					if(tmpItem.isPopup()){
				//						holder.linearLayout_more.setVisibility(View.VISIBLE);
				//					}else{
				holder.linearLayout_more.setVisibility(View.GONE);
				//					}

				holder.textView_audio.setTag(tmpItem.getAudioName());
				if(((String) holder.textView_audio.getTag()).length() > 10) {
					holder.textView_audio.setVisibility(View.VISIBLE);
				}else {
					holder.textView_audio.setVisibility(View.GONE);
				}

				holder.textVIew_video.setTag(tmpItem.getMovName());
				if(((String) holder.textVIew_video.getTag()).length() > 10) {
					holder.textVIew_video.setVisibility(View.VISIBLE);
				}else {
					holder.textVIew_video.setVisibility(View.GONE);
				}

				holder.textView_audio.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String[] split = ((String) v.getTag()).split("::");
						Uri uri = Uri.parse(split[0]);
						Intent intent = new Intent(Intent.ACTION_VIEW);  
						intent.setDataAndType(uri, "audio/*");  
						context.startActivity(intent);
					}
				});

				holder.textVIew_video.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Uri uri = Uri.parse(v.getTag().toString());
						Intent intent = new Intent(Intent.ACTION_VIEW);  
						intent.setDataAndType(uri, "video/*");  
						context.startActivity(intent);
					}
				});
				//			contentTxt.setMoreText(content);
				if(contents.length() > 80) {
					contents = contents.substring(0,80);
					contents += "[ ... ]";
				}
				holder.textView_contents.setText(contents);


				if(tmpItem.getPhotoName1().length() > 10 ) {
					holder.imageView_contents_1.setVisibility(View.VISIBLE);
					myApp.imageloder.displayImage(tmpItem.getPhotoName1(), holder.imageView_contents_1);
				}
				else {
					holder.imageView_contents_1.setVisibility(View.GONE);
				}

				holder.textView_nickname.setText(tmpItem.getName());
				holder.textView_msgtype.setText(Common.getCategoryKindString(Integer.parseInt(tmpItem.getCategory())));
				holder.textView_regdate.setText(Common.CreateDataWithCheck(tmpItem.getCreateTime()));

				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, SC008_S01118.class);
						intent.putExtra("seq", tmpItem.getSeq());
						intent.putExtra("msgType", tmpItem.getMsgType());
						context.startActivity(intent);
					}
				});


			} catch (Exception e) {
				WriteFileLog.writeException(e);
			}
			return convertView;
		}

	}	
	private void createDialog(int position, final MainListVO seq) {
		try {
			//삭제
			if(position == 2) {
				BrUtilManager.getInstance().ShowDialog2btn(context, "알림", "게시글을 삭제하시겠습니까?",  new dialogclick() {

					@Override
					public void setondialogokclick() {
						delMessage(seq.getSeq());
						// TODO Auto-generated method stub
						//							mStatusCallBack.deleteCallback(mSelectSeq);
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
//				BrUtilManager.getInstance().showToast(context, "권한이 없습니다.");
				Intent intent = new Intent(SC008_S00124.this, SC008_S11118.class);
				intent.putExtra("seq", seq.getSeq());
				intent.putExtra("msgType", seq.getMsgType());
				startActivityForResult(intent, 1000);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		try {
			if(requestCode == 1000) {
				if(resultCode == 2) {
					getData();
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
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
				getData();
			}else {
				BrUtilManager.getInstance().showToast(this, message);
			}	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}









}
