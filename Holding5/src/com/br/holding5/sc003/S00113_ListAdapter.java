package com.br.holding5.sc003;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.br.holding5.ms.ResizeImageView;
import com.br.holding5.sc002.ViewHolder;
import com.br.holding5.sc014.SC00115;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.dialogclick;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class S00113_ListAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<ReplyVO> mItems;
	private LayoutInflater mInflater;
	private int mSelectSeq;
	private int mSelectPosition;
	private String mUserId;
	private CommonDialog mDialog;
	private MediaPlayer mMediaPlayer;
	private boolean mUnknownYn = false;
	private ReplyVO mSelectVO = null;
	Holding5 myApp;
	
//	private boolean[] mEditYn;
	//	private SparseArray<WeakReference<View>> mViewArray;

	public interface statusCallBack {
		public void callback(int type, int seq, String userId, int position);
		public void deleteCallback(int seq, int position);
		public void reportCallback(int seq, String userId, int kind, int position);
		public void nickNameCallback(String nickName);
		public void replyEditCallback(ReplyVO reply);
	}


	statusCallBack mStatusCallBack;

	private EditText mEditText;
	private TextView mTextView;

	public S00113_ListAdapter(Context context, ArrayList<ReplyVO> items, statusCallBack callback, boolean unknownYn, Holding5 myApp) {
		mContext = context;
		mStatusCallBack = callback;
		mInflater = LayoutInflater.from(context);
		mItems = items;
		mUnknownYn = unknownYn;
		this.myApp = myApp;
		mUserId = myApp.getUserInfo().getUserId();
//		mEditYn = new boolean[items.size()];
//		
//		for(int i = 0  ; i < mEditYn.length; i ++) {
//			mEditYn[i] = false;
//		}
		//		this.mViewArray = new SparseArray<WeakReference<View>>(items.size());
	}
	

	public void setUnknown(boolean flag) {
		this.mUnknownYn  = flag;
	}
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public ReplyVO getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		try {
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.s00113_item, null);
			}


			//			convertView = mInflater.inflate(R.layout.sc003_item, null);
			TextView nameTxt = ViewHolder.get(convertView, R.id.reply_item_name_txt);
			TextView timeTxt = ViewHolder.get(convertView, R.id.reply_item_time_txt);
			final TextView contentTxt = ViewHolder.get(convertView, R.id.reply_item_content_txt);
			ImageButton menuBtn = ViewHolder.get(convertView, R.id.ico_s_menu_btn);
			TextView speakerTxt = ViewHolder.get(convertView, R.id.ico_speaker_txt);
			TextView goodTxt = ViewHolder.get(convertView, R.id.ico_good_txt);
			//			TextView replyTxt = ViewHolder.get(convertView, R.id.ico_reply_txt);
			Button goodBtn = ViewHolder.get(convertView, R.id.good_btn);
			//			Button replyBtn = ViewHolder.get(convertView, R.id.reply_btn);
			Button repotBtn = ViewHolder.get(convertView, R.id.repot_btn);
			RelativeLayout audioLayout= ViewHolder.get(convertView, R.id.reply_audio_layout);
			RelativeLayout videoLayout = ViewHolder.get(convertView, R.id.reply_video_layout);
//			ResizeImageView videoPhoto = ViewHolder.get(convertView, R.id.reply_item_video_img);
			TextView videoPlayBtn = ViewHolder.get(convertView, R.id.reply_item_video_play_btn);
			ResizeImageView photoLayout = ViewHolder.get(convertView, R.id.reply_item_photo_img);
			TextView audioPlayBtn = ViewHolder.get(convertView, R.id.reply_audio_play_btn);
			ImageView profImg = ViewHolder.get(convertView, R.id.prof_img);
			ImageView maskImg = ViewHolder.get(convertView, R.id.prof_mask_img);
			final EditText contentEdit = ViewHolder.get(convertView, R.id.reply_item_edit_txt);
			
			//			TextView nameTxt = (TextView) convertView.findViewById(R.id.list_item_name_txt);
			//			TextView cateTxt = (TextView) convertView.findViewById(R.id.list_item_category_txt);
			//			TextView timeTxt = (TextView) convertView.findViewById(R.id.list_item_time_txt);
			//			TextView contentTxt = (TextView) convertView.findViewById(R.id.list_item_content_txt);
			//			ImageButton menuBtn = (ImageButton) convertView.findViewById(R.id.ico_s_menu_btn);
			//			TextView speakerTxt = (TextView) convertView.findViewById(R.id.ico_speaker_txt);
			//			TextView goodTxt = (TextView) convertView.findViewById(R.id.ico_good_txt);
			//			TextView replyTxt = (TextView) convertView.findViewById(R.id.ico_reply_txt);
			//			GoodButton goodBtn = (GoodButton) convertView.findViewById(R.id.good_btn);
			//			Button replyBtn = (Button) convertView.findViewById(R.id.reply_btn);
			//			Button repotBtn = (Button) convertView.findViewById(R.id.repot_btn);
			//			
			//			photoLayout.setClearImageBtnYn(false);

			final ReplyVO item = getItem(position);
			
			profImg.setTag(item);
			profImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ReplyVO item = (ReplyVO) v.getTag();
				
					if(item.getMemLvl() > 1) {
						mContext.startActivity(new Intent(mContext, SC00115.class).putExtra("yn", true).putExtra("userId", item.getCommentId()));	
					}
					else return;
					
//					BrUtilManager.getInstance().showToast(mContext, item.getMemLvl() + "click");
				}
			});
		//TODO dd
			if(item.getMemLvl() == 2) {
				maskImg.setImageResource(R.drawable.happy_prf_bg_01);
			}
			else if(item.getMemLvl() == 3) {
				maskImg.setImageResource(R.drawable.happy_prf_bg_02);
			}
			else if(item.getMemLvl() == 4) {
				maskImg.setImageResource(R.drawable.happy_prf_bg_03);
			}
			else {
				maskImg.setImageResource(R.drawable.prf_box_bg);
			}

			if(item.getMemLvl() == 1 ) {
				profImg.setImageBitmap(Common.getProfileImage(mContext, item.getMemLvl(), item.getSex(), item.getBirthDay()));
			}
			else if(item.getMemLvl() == 5) {
//				myApp.imageloder.displayImage(Global.host + item.getHappyPhoto(),profImg);
				//				holder.prgImg.setImageBitmap(Common.getProfileImage(mContext, item));
			}
			else {
				if(item.getHappyPhoto().startsWith("http")) {
					myApp.imageloder.displayImage(item.getHappyPhoto(), profImg);
				}
				else {
					myApp.imageloder.displayImage(Global.host + item.getHappyPhoto(), profImg);	
				}
				
				//				holder.prgImg.setImageBitmap(Common.getProfileImage(mContext, item));
			}

			
			
			
			
			if(item.getMovName().length() < 3) {
				videoLayout.setVisibility(View.GONE);
			}
			else {
				videoLayout.setVisibility(View.VISIBLE);
//				String[] split = item.getMovName().split("::");
//				String thumbPath = split[0];
//				String movPath = split[1];
				videoPlayBtn.setTag( item.getMovName());
				videoPlayBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String[] split = v.getTag().toString().split("::");
						String thumbPath = split[0];
						String movPath = split[1];
						Uri uri = Uri.parse(movPath);
//						Intent i = new Intent(Intent.ACTION_VIEW);
						Intent intent = new Intent(Intent.ACTION_VIEW);  
						intent.setDataAndType(uri, "video/*");  
						((Activity)mContext).startActivity(intent);
					}
				});
			}

			if(item.getPhotoName().length() < 3) {
				photoLayout.setVisibility(View.GONE);
			}
			else {
				photoLayout.setVisibility(View.VISIBLE);
				myApp.imageloder.displayImage(item.getPhotoName(), photoLayout, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						// TODO Auto-generated method stub
						Log.e("", "");

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
						// TODO Auto-generated method stub
						Log.e("", "");
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
						// TODO Auto-generated method stub
						Log.e("", "");
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						// TODO Auto-generated method stub
						Log.e("", "");
					}
				});
				//				photoLayout.setImage(item.getPhotoName(),1);
			}

			if(item.getAudioName().length() < 10) {
				audioLayout.setVisibility(View.GONE);
			}
			else {
				audioLayout.setVisibility(View.VISIBLE);
			}
			String content = item.getCommentContents();

			if(mUserId.equals(item.getCommentId())) {
				menuBtn.setVisibility(View.VISIBLE);
			}
			else {
				menuBtn.setVisibility(View.INVISIBLE);
			}
			//			contentTxt.setMoreText(content);
//			if(content.indexOf("@") != -1 && content.indexOf("::") != -1 && content.indexOf("@") < content.indexOf("::")) {
////				String tag = content.replace(content.substring(content.indexOf("@"), content.indexOf("::")+1), "테스트입니다.");
////				contentTxt.setText(content);
//				contentTxt.setText("");
//				int start = content.indexOf("@");
//				int end = content.indexOf("::");
//				content = content.replace("@", " ");
//				content = content.replace("::", " ");
//				SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder(content);
//				mSpannableStringBuilder.setSpan(new ForegroundColorSpan(0xFF04478f), start, end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//				contentTxt.append(mSpannableStringBuilder);
//
//				
////				contentTxt.setText(tag);	
//			}
//			else {
//				contentTxt.setText(content);
//			}
			Common.setTagName(content, contentTxt);
			speakerTxt.setText(item.getReportCnt()+"");
			goodTxt.setText(item.getLikeCnt()+"");
			
			
			if(item.getTempName().equals("")) {
				nameTxt.setText(item.getNickName());
			}
			else {
				nameTxt.setText(item.getTempName());
			}
			

			timeTxt.setText(Common.CreateDataWithCheck(item.getCommentTime()));
			//			if(mUserId.equals(item.getName())) {
			//				menuBtn.setVisibility(View.VISIBLE);
			//			}
			//			else {
			//				menuBtn.setVisibility(View.INVISIBLE);
			//			}
			audioPlayBtn.setTag(item.getAudioName());
			audioPlayBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(v.getTag() + "");
					Intent intent = new Intent(Intent.ACTION_VIEW);  
					intent.setDataAndType(uri, "audio/*");  
					mContext.startActivity(intent);
				}
			});

			menuBtn.setTag(item);
			menuBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//					BrUtilManager.getInstance().showToast(mContext, "메뉴버튼");
					//					Intent intent = new Intent(mContext, PopupActivity.class);
					//					((Activity)mContext).startActivity(intent);
					ReplyVO vo = (ReplyVO) v.getTag();
					mSelectPosition = position;
					mSelectSeq = vo.getSeq();
					mDialog = new CommonDialog(mContext);
					mSelectVO = vo;
					mEditText = contentEdit;
					mTextView = contentTxt;
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
			
			goodBtn.setTag(item);
			goodBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ReplyVO vo = (ReplyVO) v.getTag();
					if(vo.getCommentId().equals(myApp.getUserInfo().getUserId())) {
						BrUtilManager.getInstance().showToast(mContext, "본인이 작성한 글은 좋아요를 누를 수 없습니다.");
						return;
					}
					setStatus(1, vo.getSeq(), vo.getCommentId(), position);
				}
			});

			repotBtn.setTag(item);
			repotBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ReplyVO vo = (ReplyVO) v.getTag();
					if(vo.getMemLvl() > 1 && vo.getMemLvl() < 5) {
						BrUtilManager.getInstance().showToast(mContext, "해피인이 쓴 글은 신고할 수 없습니다.");
						return;
					}
					
					if(vo.getCommentId().equals(myApp.getUserInfo().getUserId())) {
						BrUtilManager.getInstance().showToast(mContext, "본인이 작성한 글은 신고하기를 할 수 없습니다.");
						return;
					}
					
					if(vo.getIsReport() != 0) {
						BrUtilManager.getInstance().showToast(mContext, "이미 신고한 댓글입니다.");
						return;
					}

					//					if(item.getMemLvl() > 1 && item.getMemLvl() < 5) {
					//						BrUtilManager.getInstance().showToast(mContext, "해피인이 쓴 글은 신고할 수 없습니다.");
					//					}
					//					else {
					mStatusCallBack.reportCallback(item.getSeq(), mUserId, item.getType(), position);	
					//					}

				}
			});

			nameTxt.setTag(item);
			nameTxt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ReplyVO vo = (ReplyVO) v.getTag();
					mStatusCallBack.nickNameCallback(vo.getNickName());
				}
			});
		}
		catch(Exception e) {
			WriteFileLog.writeException(e);
		}
		return convertView;
	}

	private void createDialog(final int position) {
		try {
			//삭제
			if(position == 2) {
				
				BrUtilManager.getInstance().ShowDialog2btn(mContext, "알림", "댓글을 삭제하시겠습니까?", new dialogclick() {
					@Override
					public void setondialogokclick() {
						mStatusCallBack.deleteCallback(mSelectSeq, mSelectPosition);
					}
					
					@Override
					public void setondialocancelkclick() {
					}
				});
			}
			//수정
			else {
//				mEditText.setVisibility(View.VISIBLE);
//				mTextView.setVisibility(View.GONE);
//				mEditText.setText(mTextView.getText().toString());
//				mEditText.setSelection(mEditText.getText().length());
//				mEditYn[position] = true;
//				notifyDataSetChanged();
				if(mSelectVO != null)
					mStatusCallBack.replyEditCallback(mSelectVO);
//				BrUtilManager.getInstance().showToast(mContext, "지원하지 않습니다.");
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}


	private void setStatus(int type, int seq, String userId, int position) {
		mStatusCallBack.callback(type, seq, userId, position);
	}
}
