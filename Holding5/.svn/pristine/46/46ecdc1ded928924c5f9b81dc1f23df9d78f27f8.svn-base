package com.br.holding5.sc002;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.br.holding5.Global;
import com.br.holding5.Holding5;
import com.br.holding5.WriteFileLog;
import com.br.holding5.ms.Common;
import com.br.holding5.ms.CommonDialog;
import com.br.holding5.ms.CommonDialog.onMenuItemSelectListener;
import com.br.holding5.sc003.S00113;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.dialogclick;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class S00013_ListAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<MainListVO> mItems;
	private ArrayList<Boolean> mAudioYnArr;
	private LayoutInflater mInflater;
	private int mSelectSeq;
	private String mUserId;
	private CommonDialog mDialog;
	private Holding5 mMyApp;
	//	private SparseArray<WeakReference<View>> mViewArray;

	public interface statusCallBack {
		public void callback(int type, int seq, String userId, int position);
		public void deleteCallback(int seq);
		public void reportCallback(int seq, String userId, int kind, int position);
		public void updateCallback(int seq);
	}


	statusCallBack mStatusCallBack;


	public S00013_ListAdapter(Context context, ArrayList<MainListVO> items, statusCallBack callback, Holding5 myapp) {
		mContext = context;
		mStatusCallBack = callback;
		mInflater = LayoutInflater.from(context);
		mItems = items;
		mAudioYnArr = new ArrayList<Boolean>();
		mMyApp = myapp;
		mUserId = myapp.getUserInfo().getUserId();
		for(int i = 0; i < mItems.size(); i++) {
			if(mItems.get(i).getAudioName().length() > 10) {
				mAudioYnArr.add(true);
			}
			else {
				mAudioYnArr.add(false);
			}
		}
		//		this.mViewArray = new SparseArray<WeakReference<View>>(items.size());
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public MainListVO getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

//	public class Holder {
//		public GoodButton goodBtn;
//		public Button replyBtn;
//		public Button repotBtn;
//		RelativeLayout audioLayout;
//		TextView audioPlayBtn;
//		RelativeLayout videoLayout;
//		TextView videoPlayBtn;
//		//		LinearLayout contentLayout;
//		TextView nameTxt;
//		TextView cateTxt;
//		TextView timeTxt;
//		TextView contentTxt;
//		ImageButton menuBtn;
//		TextView speakerTxt;
//		TextView goodTxt;
//		TextView replyTxt;
//		ImageView prgImg;
//		ImageView prgMaskImg;
////		MainListVO mainVo;
//	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
//		if(convertView == null) {
//			convertView = mInflater.inflate(R.layout.s00113_item, null);
//		}
		
		try {
//			Holder holder = null;
			final MainListVO item = getItem(position);
			String content = item.getContents().toString();
			
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.s00013_item, null);
			}
			
//			if(convertView == null) {
//				convertView = View.inflate(mContext,R.layout.s00013_item, null);
//				holder = new Holder();
//				holder.audioLayout = (RelativeLayout) convertView.findViewById(R.id.board_audio_layout);
//				holder.audioPlayBtn = (TextView) convertView.findViewById(R.id.reply_audio_play_btn);
//				holder.videoLayout = (RelativeLayout) convertView.findViewById(R.id.board_video_layout);
//				holder.videoPlayBtn = (TextView) convertView.findViewById(R.id.reply_video_play_btn);
//				//				holder.contentLayout = (LinearLayout) convertView.findViewById(R.id.board_audio_layout);
//				holder.nameTxt = (TextView) convertView.findViewById(R.id.list_item_name_txt);
//				holder.cateTxt = (TextView) convertView.findViewById(R.id.list_item_category_txt);
//				holder.timeTxt = (TextView) convertView.findViewById(R.id.list_item_time_txt);
//				holder.contentTxt = (TextView) convertView.findViewById(R.id.list_item_content_txt);
//				holder.menuBtn = (ImageButton) convertView.findViewById(R.id.ico_s_menu_btn);
//				holder.speakerTxt = (TextView) convertView.findViewById(R.id.ico_speaker_txt);
//				holder.goodTxt = (TextView) convertView.findViewById(R.id.ico_good_txt);
//				holder.replyTxt = (TextView) convertView.findViewById(R.id.ico_reply_txt);
//				holder.goodBtn = (GoodButton) convertView.findViewById(R.id.good_btn);
//				holder.replyBtn = (Button) convertView.findViewById(R.id.reply_btn);
//				holder.repotBtn = (Button) convertView.findViewById(R.id.repot_btn);
//				holder.prgImg = (ImageView) convertView.findViewById(R.id.list_item_profile_img);
//				holder.prgMaskImg = (ImageView) convertView.findViewById(R.id.list_item_profile_mask);
//				
//				
//				convertView.setTag(holder);
//			}
//			else {
//				holder = (Holder) convertView.getTag();
//			}
				
			RelativeLayout audioLayout = ViewHolder.get(convertView, R.id.board_audio_layout);
			TextView audioPlayBtn = ViewHolder.get(convertView, R.id.reply_audio_play_btn);
			RelativeLayout videoLayout = ViewHolder.get(convertView, R.id.board_video_layout);
			TextView videoPlayBtn = ViewHolder.get(convertView, R.id.reply_video_play_btn);
			
			TextView nameTxt = ViewHolder.get(convertView, R.id.list_item_name_txt);
			TextView cateTxt = ViewHolder.get(convertView, R.id.list_item_category_txt);
			TextView timeTxt = ViewHolder.get(convertView, R.id.list_item_time_txt);
			TextView contentTxt = ViewHolder.get(convertView, R.id.list_item_content_txt);
			ImageButton menuBtn = ViewHolder.get(convertView, R.id.ico_s_menu_btn);
			TextView speakerTxt = ViewHolder.get(convertView, R.id.ico_speaker_txt);
			TextView goodTxt = ViewHolder.get(convertView, R.id.ico_good_txt);
			TextView replyTxt = ViewHolder.get(convertView, R.id.ico_reply_txt);
			GoodButton goodBtn = ViewHolder.get(convertView, R.id.good_btn);
//			Button replyBtn = ViewHolder.get(convertView, R.id.reply_btn);
			Button repotBtn = ViewHolder.get(convertView, R.id.repot_btn);
			ImageView prgImg = ViewHolder.get(convertView, R.id.list_item_profile_img);
			ImageView prgMaskImg = ViewHolder.get(convertView, R.id.list_item_profile_mask);
			
			audioLayout.setTag(item.getAudioName());
			videoLayout.setTag(item.getMovName());
			audioPlayBtn.setTag(item.getAudioName());
			videoPlayBtn.setTag(item.getMovName());
			
			if(item.getAudioName().length() > 10) {
				audioLayout.setVisibility(View.VISIBLE);
			}
			else {
				audioLayout.setVisibility(View.GONE);
			}
			
			if(item.getMovName().length() > 10) {
				videoLayout.setVisibility(View.VISIBLE);
			}
			else {
				videoLayout.setVisibility(View.GONE);
			}
			
//			prgImg.setTag(item);
			prgImg.setImageBitmap(null);
			prgImg.setTag(item);
			if(item.getMemLvl() == 5) {
				String happyPhoto = ((MainListVO) prgImg.getTag()).getHappyPhoto();
				if(happyPhoto.startsWith("http")) {
					mMyApp.imageloder.displayImage(happyPhoto, prgImg,mMyApp.options);
				}
				else { 
					mMyApp.imageloder.displayImage(Global.host + happyPhoto, prgImg,mMyApp.options);	
				}
				
				
//				prgImg.setImageBitmap(Common.getProfileImage(mContext, item));
			}
			else if(item.getMemLvl() == 2 || item.getMemLvl() == 3 || item.getMemLvl() == 4) { //해피인
				String happyPhoto = ((MainListVO) prgImg.getTag()).getHappyPhoto();
				String url = "";
				if(happyPhoto.startsWith("http")) {
					url = happyPhoto;
				}
				else {
					url = Global.host + happyPhoto;
				}
				mMyApp.imageloder.displayImage(url, prgImg,mMyApp.options, new ImageLoadingListener() {					
					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
						ImageView imgView = (ImageView) arg1;
						imgView.setImageBitmap(arg2);
					}
					
					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				if(item.getMemLvl() == 2) {
					prgMaskImg.setImageResource(R.drawable.happy_prf_bg_01);
				}
				else if(item.getMemLvl() == 3) {
					prgMaskImg.setImageResource(R.drawable.happy_prf_bg_02);
				}
				else if(item.getMemLvl() == 4) {
					prgMaskImg.setImageResource(R.drawable.happy_prf_bg_03);
				}
			}
			else if(item.getMemLvl() == 1) {
				mMyApp.imageloder.displayImage(Common.getProfileImage(mContext,item), prgImg,mMyApp.options);
				prgMaskImg.setImageResource(R.drawable.prf_box_bg);
			}
 
			if(content.length() > 80) {
				content = content.substring(0,80);
				content += "[ ... ]";
			}
			contentTxt.setText(content);
			speakerTxt.setText(item.getRepotCnt()+"");
			goodTxt.setText(item.getLikeCnt()+"");
			replyTxt.setText(item.getReplyCnt()+"");

			if(item.getUnknown() == 1) {
				nameTxt.setText(item.getTempName());
				nameTxt.setTypeface(null, Typeface.NORMAL);

			}
			else {
				nameTxt.setText(item.getName());


				if(item.getMemLvl() != 1) {
					nameTxt.setTypeface(null, Typeface.BOLD);
				}
				else {
					nameTxt.setTypeface(null, Typeface.NORMAL);
				}
			}

			cateTxt.setText(Common.getCategoryKindString(Integer.parseInt(item.getCategory())));
			timeTxt.setText(Common.CreateDataWithCheck(item.getCreateTime()));

			if(mUserId.equals(item.getUserId())) {
				menuBtn.setVisibility(View.VISIBLE);
			}
			else {
				menuBtn.setVisibility(View.INVISIBLE);
			}
			
			
			menuBtn.setTag(item);
			menuBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					MainListVO vo = (MainListVO) v.getTag();
					mSelectSeq = vo.getSeq();
					mDialog = new CommonDialog(mContext);
					mDialog.createBoardMenu(new onMenuItemSelectListener() {
						@Override
						public void onMenuItemSelected(int position) {
							mDialog.dismiss();
							createDialog(position);
						}
					});
					mDialog.show();

				}
			});

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, S00113.class);
					intent.putExtra("seq", item.getSeq());
					((Activity)mContext).startActivityForResult(intent, 1000);
				}
			});
			
			goodBtn.setTag(item);
			goodBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					MainListVO vo = (MainListVO) v.getTag();
					if(vo.getUserId().equals(mMyApp.getUserInfo().getUserId())) {
						BrUtilManager.getInstance().showToast(mContext, "본인이 작성한 글은 좋아요를 누를 수 없습니다.");
						return;
					}
					
					setStatus(1, vo.getSeq(), vo.getName(), position);
				}
			});

			repotBtn.setTag(item);
			repotBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					MainListVO vo = (MainListVO) v.getTag();
					if(vo.getMemLvl() > 1 && vo.getMemLvl() < 5) {
						BrUtilManager.getInstance().showToast(mContext, "해피인이 쓴 글은 신고할 수 없습니다.");
					}
					else if(vo.getUserId().equals(mMyApp.getUserInfo().getUserId())) {
						BrUtilManager.getInstance().showToast(mContext, "본인이 작성한 글은 신고하기를 할 수 없습니다.");
					}
					else if(vo.getIsReport()!=0) {
						BrUtilManager.getInstance().showToast(mContext, "이미 신고한 글입니다.");
					}
					else {
						mStatusCallBack.reportCallback(vo.getSeq(), mMyApp.getUserInfo().getUserId(), Integer.parseInt(vo.getCategory()), position);	
					}

				}
			});
			
			audioPlayBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(v.getTag() + "");
					Intent intent = new Intent(Intent.ACTION_VIEW);  
					intent.setDataAndType(uri, "audio/*");  
					mContext.startActivity(intent);
				}
			});
			
			videoPlayBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String[] split = ((String) v.getTag()).split("::");
					Uri uri = Uri.parse(split[0] + "");
					Intent intent = new Intent(Intent.ACTION_VIEW);  
					intent.setDataAndType(uri, "video/*");  
					mContext.startActivity(intent);
				}
			});
			
			
//			holder.audioLayout.setTag(item.getAudioName());
//			holder.videoLayout.setTag(item.getMovName());
//			holder.audioPlayBtn.setTag(item.getAudioName());
//			holder.videoPlayBtn.setTag(item.getMovName());
//			
//			if(item.getAudioName().length() > 10) {
//				holder.audioLayout.setVisibility(View.VISIBLE);
//			}
//			else {
//				holder.audioLayout.setVisibility(View.GONE);
//			}
//			
//			if(item.getMovName().length() > 10) {
//				holder.videoLayout.setVisibility(View.VISIBLE);
//			}
//			else {
//				holder.videoLayout.setVisibility(View.GONE);
//			}
//			
////			holder.prgImg.setTag(item);
//			holder.prgImg.setImageBitmap(null);
//			holder.prgImg.setTag(item);
//			if(item.getMemLvl() == 1) {
////				holder.prgImg.setImageBitmap(Common.getProfileImage(mContext,item));
//				holder.prgMaskImg.setImageResource(R.drawable.prf_box_bg);
//			}
//			else if(item.getMemLvl() == 5) {
//				String happyPhoto = ((MainListVO) holder.prgImg.getTag()).getHappyPhoto();
//				mMyApp.imageloder.displayImage(Global.host + happyPhoto, holder.prgImg,mMyApp.options);
//				
////				holder.prgImg.setImageBitmap(Common.getProfileImage(mContext, item));
//			}
//			else {
//				String happyPhoto = ((MainListVO) holder.prgImg.getTag()).getHappyPhoto();
//				mMyApp.imageloder.displayImage(Global.host + happyPhoto, holder.prgImg,mMyApp.options, new ImageLoadingListener() {					
//					@Override
//					public void onLoadingStarted(String arg0, View arg1) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
//						ImageView imgView = (ImageView) arg1;
//						imgView.setImageBitmap(arg2);
//					}
//					
//					@Override
//					public void onLoadingCancelled(String arg0, View arg1) {
//						// TODO Auto-generated method stub
//						
//					}
//				});
//				if(item.getMemLvl() == 2) {
//					holder.prgMaskImg.setImageResource(R.drawable.happy_prf_bg_01);
//				}
//				else if(item.getMemLvl() == 3) {
//					holder.prgMaskImg.setImageResource(R.drawable.happy_prf_bg_02);
//				}
//				else if(item.getMemLvl() == 4) {
//					holder.prgMaskImg.setImageResource(R.drawable.happy_prf_bg_03);
//				}
////				holder.prgImg.setImageBitmap(Common.getProfileImage(mContext, item));
//			}
//			
//			
// 
////						TextView nameTxt = (TextView) convertView.findViewById(R.id.list_item_name_txt);
////						TextView cateTxt = (TextView) convertView.findViewById(R.id.list_item_category_txt);
////						TextView timeTxt = (TextView) convertView.findViewById(R.id.list_item_time_txt);
////						TextView contentTxt = (TextView) convertView.findViewById(R.id.list_item_content_txt);
////						ImageButton menuBtn = (ImageButton) convertView.findViewById(R.id.ico_s_menu_btn);
////						TextView speakerTxt = (TextView) convertView.findViewById(R.id.ico_speaker_txt);
////						TextView goodTxt = (TextView) convertView.findViewById(R.id.ico_good_txt);
////						TextView replyTxt = (TextView) convertView.findViewById(R.id.ico_reply_txt);
////						GoodButton goodBtn = (GoodButton) convertView.findViewById(R.id.good_btn);
////						Button replyBtn = (Button) convertView.findViewById(R.id.reply_btn);
////						Button repotBtn = (Button) convertView.findViewById(R.id.repot_btn);
//			//			
//
//
//
//
//
//			//			contentTxt.setMoreText(content);
//			if(content.length() > 80) {
//				content = content.substring(0,80);
//				content += "[ ... ]";
//			}
//			holder.contentTxt.setText(content);
//			holder.speakerTxt.setText(item.getRepotCnt()+"");
//			holder.goodTxt.setText(item.getLikeCnt()+"");
//			holder.replyTxt.setText(item.getReplyCnt()+"");
//
//			if(item.getUnknown() == 1) {
//				holder.nameTxt.setText(item.getTempName());
//				holder.nameTxt.setTypeface(null, Typeface.NORMAL);
//
//			}
//			else {
//				holder.nameTxt.setText(item.getName());
//
//
//				if(item.getMemLvl() != 1) {
//					holder.nameTxt.setTypeface(null, Typeface.BOLD);
//				}
//				else {
//					holder.nameTxt.setTypeface(null, Typeface.NORMAL);
//				}
//			}
//
//			holder.cateTxt.setText(Common.getCategoryKindString(Integer.parseInt(item.getCategory())));
//			holder.timeTxt.setText(Common.CreateDataWithCheck(item.getCreateTime()));
//
//			if(mUserId.equals(item.getUserId())) {
//				holder.menuBtn.setVisibility(View.VISIBLE);
//			}
//			else {
//				holder.menuBtn.setVisibility(View.INVISIBLE);
//			}
//			
//			
//			holder.menuBtn.setTag(item);
//			holder.menuBtn.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					MainListVO vo = (MainListVO) v.getTag();
//					mSelectSeq = vo.getSeq();
//					mDialog = new CommonDialog(mContext);
//					mDialog.createBoardMenu(new onMenuItemSelectListener() {
//						@Override
//						public void onMenuItemSelected(int position) {
//							mDialog.dismiss();
//							createDialog(position);
//						}
//					});
//					mDialog.show();
//
//				}
//			});
//
//			convertView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(mContext, S00113.class);
//					intent.putExtra("seq", item.getSeq());
//					((Activity)mContext).startActivityForResult(intent, 1000);
//				}
//			});
//			
//			holder.goodBtn.setTag(item);
//			holder.goodBtn.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					MainListVO vo = (MainListVO) v.getTag();
//					if(vo.getUserId().equals(mMyApp.getUserInfo().getUserId())) {
//						BrUtilManager.getInstance().showToast(mContext, "본인이 작성한 글은 좋아요를 누를 수 없습니다.");
//						return;
//					}
//					
//					setStatus(1, vo.getSeq(), vo.getName(), position);
//				}
//			});
//
//			holder.repotBtn.setTag(item);
//			holder.repotBtn.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					MainListVO vo = (MainListVO) v.getTag();
//					if(vo.getMemLvl() > 1 && vo.getMemLvl() < 5) {
//						BrUtilManager.getInstance().showToast(mContext, "해피인이 쓴 글은 신고할 수 없습니다.");
//					}
//					else if(vo.getUserId().equals(mMyApp.getUserInfo().getUserId())) {
//						BrUtilManager.getInstance().showToast(mContext, "본인이 작성한 글은 신고하기를 할 수 없습니다.");
//					}
//					else if(vo.getIsReport()!=0) {
//						BrUtilManager.getInstance().showToast(mContext, "이미 신고한 글입니다.");
//					}
//					else {
//						mStatusCallBack.reportCallback(vo.getSeq(), mMyApp.getUserInfo().getUserId(), Integer.parseInt(vo.getCategory()), position);	
//					}
//
//				}
//			});
//			
//			holder.audioPlayBtn.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Uri uri = Uri.parse(v.getTag() + "");
//					Intent intent = new Intent(Intent.ACTION_VIEW);  
//					intent.setDataAndType(uri, "audio/*");  
//					mContext.startActivity(intent);
//				}
//			});
//			
//			holder.videoPlayBtn.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					String[] split = ((String) v.getTag()).split("::");
//					Uri uri = Uri.parse(split[0] + "");
//					Intent intent = new Intent(Intent.ACTION_VIEW);  
//					intent.setDataAndType(uri, "video/*");  
//					mContext.startActivity(intent);
//				}
//			});


		}
		catch(Exception e) {
			WriteFileLog.writeException(e);
		}
		return convertView;
	}


	private void createDialog(int position) {
		//삭제
		if(position == 2) {
			BrUtilManager.getInstance().ShowDialog2btn(mContext, "알림", "게시글을 삭제하시겠습니까?",  new dialogclick() {
				
				@Override
				public void setondialogokclick() {
					// TODO Auto-generated method stub
					mStatusCallBack.deleteCallback(mSelectSeq);
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
			mStatusCallBack.updateCallback(mSelectSeq);
		}
	}


	private void setStatus(int type, int seq, String userId, int position) {
		mStatusCallBack.callback(type, seq, userId, position);
	}
}
