package com.br.holding5.sc003;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Thumbnails;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.base.BubbleTagsEditText;
import com.br.holding5.ms.Common;
import com.br.holding5.ms.CommonDialog;
import com.br.holding5.ms.CommonDialog.onMenuItemSelectListener;
import com.br.holding5.ms.MSUtil;
import com.br.holding5.ms.ResizeImageView;
import com.br.holding5.sc002.MainListVO;
import com.br.holding5.sc003.S00113_ListAdapter.statusCallBack;
import com.br.holding5.sc003.gallery.CustomGallery;
import com.br.holding5.sc003.gallery.DeleteImageView;
import com.br.holding5.sc003.gallery.DeleteImageView.onDeleteImageCallback;
import com.br.holding5.sc004.SC004;
import com.br.holding5.sc014.ReplyVo;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.dialogclick;
import com.brainyxlib.image.BrImageUtilManager;

public class S00113 extends BaseActivity implements statusCallBack {


	//	private DeleteImageView mPictureView1;
	//	private DeleteImageView mPictureView2;
	//	private DeleteImageView mPictureView3;
	//	private DeleteImageView mPictureView4;
	//	private DeleteImageView mPictureView5;
	//	ScrollView scroll;
	private final int GALLERY_REQUEST_CODE = 1000;
	private final int CAMERA_CROP_REQUEST_CODE = 1001;
	private final int CAMERA_CROP_FROM_REQUEST_CODE = 1002;
	private final int VIDEO_REQUEST_CODE = 1003;
	private final int CAPTURE_REQUEST_MOVIE = 1004;
	private final int ACTIVITY_REQUEST_RECORD_SOUND = 1005;
	private final int UPDATE_BOARD_CONTENT = 1006;
	private String tags = "";
	private ArrayList<String> mSelectImg = new ArrayList<String>();
	//	private ArrayList<DeleteImageView> mPictureArr = new ArrayList<DeleteImageView>();
	private ArrayList<Bitmap> mPictureBitmapArr = new ArrayList<Bitmap>();
	private Bitmap mVideoThumbnail;
	private Uri mCropImageCacheUri;
	private Uri mCropFromImageUri;
	private String mCropImagePath;
	private DeleteImageView mReplyPhotoView;
	private CommonDialog mDialog;
	private RadioButton mSearchTypeRadioBtn1;
	private RadioButton mSearchTypeRadioBtn2;

	private ImageButton 	mAddPictureBtn;
	private ImageButton 	mCropAddPictureBtn;
	private ImageButton 	mAudioAddBtn;
	private ImageButton 	mReplyAddBtn;
	private ImageButton 	mVideoAddBtn;
	private ImageView		mProfileImageView;
	private ImageButton 	mCaptureVideoBtn;
	private CheckBox		mUnknownCheckBox;

	private int mSelectImgCount = 0;
	private final int MAX_SELECT_COUNT = 1;
	private int mUploadFileType = -1;

	private S00113_ListAdapter mReplyAdapter;
	private ArrayList<ReplyVO> mReplyItems;

	private ListView		mReplyListView;
	private boolean mUnknownCheck;


	//	private final int REPLY_VIEW_REQUEST_CODE = 1111;
	private TextView 	mIcoGoodTxt;
	private TextView	mIcoReplyTxt;
	private TextView 	mIcoReportTxt;
	private ImageButton		mBackBtn;
	private ImageButton 	mMenuBtn;
	private Button 			mGoodBtn;
	private Button 			mReportBtn;
	private Button 			mReplyBtn;
	private EditText		mReplyEditText;

	private RadioButton 	mReportRadio1;
	private RadioButton 	mReportRadio2;
	private RadioButton 	mReportRadio3;
	private RadioButton 	mReportRadio4;
	private RadioButton 	mReportRadio5;
	private RadioButton 	mReportRadio6;
	private RadioButton 	mReportRadio7;
	private ArrayList<RadioButton> mRadioReportArr;
	private ImageButton 	mReportSendBtn;
	private ImageButton 	mReportCancelBtn;

	private int mIsReport = 0;
	private ReplyVO mReplyVo;
	private int mReportGubun;//1 board 2 reply
	private int mSeq;
	private int mKind;
	private boolean mUnknown;
	private String mUserId;
	private String mDetailTempName = "";
	private int mReplySeq;
	private int mReplyKind;
	private String mReplyUserId="";
	private int mMemLvl;
	//	private String ID = "test1";
	private boolean mChangeDataYn = false;
	private int mSelectType = 0;
	private int mSelectPosition = 0 ;
	private LinearLayout mLoadingLayout ; 
	private LinearLayout mContentsLayout;
	private RelativeLayout mRelativeLayout;
	private RelativeLayout mReportLayout;
	private RelativeLayout maddbtnLayout;
	private LinearLayout mHeaderView;
	private ImageButton 	mReplyEditBtn;
	private ArrayList<String> mPathArr = new ArrayList<String>();
	private ArrayList<ResizeImageView> mResizeViewArr = new ArrayList<ResizeImageView>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			setContentView(R.layout.s00113);
			mUserId = myApp.getUserInfo().getUserId();
			mMemLvl = myApp.getUserInfo().getMemLvl();

			mHeaderView = (LinearLayout) getLayoutInflater().inflate(R.layout.s00113_header, null);
			mRelativeLayout = (RelativeLayout) findViewById(R.id.detail_layout);
			mReportLayout = (RelativeLayout) findViewById(R.id.detail_report_layout);

			mLoadingLayout = (LinearLayout) findViewById(R.id.linearLayout_loading);

			mReplyEditText = (EditText) findViewById(R.id.reply_edit_text);
			mBackBtn = (ImageButton) findViewById(R.id.detail_back_btn);

			mCaptureVideoBtn = (ImageButton) findViewById(R.id.capture_add_video_btn);
			mReplyEditBtn = (ImageButton) findViewById(R.id.reply_edit_btn);

			maddbtnLayout = (RelativeLayout) findViewById(R.id.addbtn_layout);
			mContentsLayout = (LinearLayout) mHeaderView.findViewById(R.id.content_layout);
			mGoodBtn = (Button) mHeaderView.findViewById(R.id.good_btn);
			mReportBtn = (Button) mHeaderView.findViewById(R.id.repot_btn);
			mReplyBtn = (Button) mHeaderView.findViewById(R.id.reply_btn);
			mProfileImageView = (ImageView) mHeaderView.findViewById(R.id.profile_img);
			mSearchTypeRadioBtn1 = (RadioButton) mHeaderView.findViewById(R.id.search_type_1_radio_btn);
			mSearchTypeRadioBtn2 = (RadioButton) mHeaderView.findViewById(R.id.search_type_2_radio_btn);
			mMenuBtn = (ImageButton) mHeaderView.findViewById(R.id.ico_s_menu_btn);


			mReportSendBtn = (ImageButton) findViewById(R.id.report_btn);
			mReportCancelBtn = (ImageButton) findViewById(R.id.report_cancle_btn);
			mReportRadio1 = (RadioButton) findViewById(R.id.radioButton1);
			mReportRadio2 = (RadioButton) findViewById(R.id.radioButton2);
			mReportRadio3 = (RadioButton) findViewById(R.id.radioButton3);
			mReportRadio4 = (RadioButton) findViewById(R.id.radioButton4);
			mReportRadio5 = (RadioButton) findViewById(R.id.radioButton5);
			mReportRadio6 = (RadioButton) findViewById(R.id.radioButton6);
			mReportRadio7 = (RadioButton) findViewById(R.id.radioButton7);
			mRadioReportArr = new ArrayList<RadioButton>();
			mRadioReportArr.add(mReportRadio1);
			mRadioReportArr.add(mReportRadio2);
			mRadioReportArr.add(mReportRadio3);
			mRadioReportArr.add(mReportRadio4);
			mRadioReportArr.add(mReportRadio5);
			mRadioReportArr.add(mReportRadio6);
			mRadioReportArr.add(mReportRadio7);
			for(int i = 0 ; i < mRadioReportArr.size(); i ++) {
				mRadioReportArr.get(i).setTag(i);
			}
			for(int i = 0 ; i < mRadioReportArr.size() ; i++) {
				mRadioReportArr.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked) {
							RadioButton btn = (RadioButton) buttonView;
							int tag = (Integer) btn.getTag();
							mKind = tag;
							for(int i = 0 ; i < mRadioReportArr.size() ; i++) {
								if(tag == i) continue;
								else mRadioReportArr.get(i).setChecked(false);
							}
						}
					}
				});
			}

			mReportSendBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						//						if(mReportGubun == 1) { 
						//							showReportAlert();
						//						}
						//						else if(mReportGubun == 2) {
						showReportAlert();
						//						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			mReplyEditBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ReplyVO item = (ReplyVO) v.getTag();

					String content = mReplyEditText.getText().toString();
					item.setCommentContents(content);

					requestReplyEdit(item);

				}
			});

			mReportCancelBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					toggleLayout(false);
				}
			});
			//			bubbleEditText = new BubbleTagsEditText(mReplyEditText, S00113.this); 
			mReplyItems = new ArrayList<ReplyVO>();

			mUnknownCheckBox = (CheckBox) findViewById(R.id.unknown_check);
			mLoadingLayout = (LinearLayout) findViewById(R.id.linearLayout_loading);
			mReplyListView = (ListView) findViewById(R.id.reply_list);
			mAddPictureBtn = (ImageButton) findViewById(R.id.add_picture_btn);
			mVideoAddBtn = (ImageButton) findViewById(R.id.add_video_btn);
			mReplyEditText = (EditText) findViewById(R.id.reply_edit_text);
			mReplyAddBtn = (ImageButton) findViewById(R.id.reply_add_btn);
			mCropAddPictureBtn = (ImageButton) findViewById(R.id.crop_add_picture_btn);
			mAudioAddBtn = (ImageButton) findViewById(R.id.add_audio_btn);

			mReplyPhotoView = (DeleteImageView) findViewById(R.id.reply_photo_img);


			mSearchTypeRadioBtn1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked) mSelectType = 0;
					requestReplyList();
				}
			});

			mSearchTypeRadioBtn2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked) mSelectType = 1;
					requestReplyList();
				}
			});
			mReplyPhotoView.setOnDeleteImageCallback(new onDeleteImageCallback() {
				@Override
				public void deleteImageCallback(int type) {
					mUploadFileType = -1;
					mSelectImg.clear();
					mReplyPhotoView.setVisibility(View.GONE);
				}
			});
			mReplyAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mReplyEditText.getText().length() == 0 ) {
						BrUtilManager.getInstance().showToast(S00113.this, "댓글 내용을 입력해 주세요.");
						return;
					}
					if(mReplyPhotoView.getVisibility() == View.VISIBLE) {
						uploadFile();
					}
					else {
						requestReplyAdd("", "", "");
					}
				}
			});
			//		mReplyListView.setOnTouchListener(new OnTouchListener() {
			//			public boolean onTouch(View v, MotionEvent event) {
			//				mReplyListView.requestDisallowInterceptTouchEvent(true);
			//				return false;
			//			}
			//		});
			mAddPictureBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(MAX_SELECT_COUNT - mSelectImgCount <= 0) {
						BrUtilManager.getInstance().showToast(getBaseContext(), "더이상 선택 할 수 없습니다.");
					}
					else {
						if(mUploadFileType != -1) {
							BrUtilManager.getInstance().showToast(S00113.this, "한가지만 업로드 가능합니다.");
						}
						else {
							Intent intent = new Intent(S00113.this,CustomGallery.class);
							intent.putExtra("size", 1);//선택가능한갯수
							startActivityForResult(intent, GALLERY_REQUEST_CODE);	
						}
					}
				}
			});
			mVideoAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mUploadFileType != -1) {
						BrUtilManager.getInstance().showToast(S00113.this, "한가지만 업로드 가능합니다.");
					}
					else {
						Intent i = new Intent(Intent.ACTION_PICK);
						i.setType("video/*");
						startActivityForResult(i, VIDEO_REQUEST_CODE);		
					}

				}
			});

			mCaptureVideoBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mUploadFileType != -1) {
						BrUtilManager.getInstance().showToast(S00113.this, "한가지만 업로드 가능합니다.");
					}
					else {
						Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
						startActivityForResult(intent, CAPTURE_REQUEST_MOVIE);
					}
				}
			});

			//			scroll = (ScrollView) findViewById(R.id.scrollView1);

			//		mReplyListView.setOnTouchListener(new OnTouchListener() {        //리스트뷰 터취 리스너
			//			@Override
			//			public boolean onTouch(View v, MotionEvent event) { 
			//				scroll.requestDisallowInterceptTouchEvent(true);    // 리스트뷰에서 터취가되면 스크롤뷰만 움직이게
			//				return false;
			//			}
			//		});
			mCropAddPictureBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mUploadFileType != -1) {
						BrUtilManager.getInstance().showToast(S00113.this, "한가지만 업로드 가능합니다.");
					}
					else {
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

						// 임시로 사용할 파일의 경로를 생성
						String url = "tmp_" + String.valueOf(System.currentTimeMillis())+ ".jpg";
						mCropImageCacheUri = Uri.fromFile(new File(myApp.getFileDir_Ex(), url));

						intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,mCropImageCacheUri);
						intent.putExtra("return-data", true);
						intent.putExtra("passok", "ok");
						startActivityForResult(intent, CAMERA_CROP_REQUEST_CODE);
					}

				}
			});

			mAudioAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if(mUploadFileType != -1) {
						BrUtilManager.getInstance().showToast(S00113.this, "한가지만 업로드 가능합니다.");
					}
					else {

						Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
						startActivityForResult(intent, ACTIVITY_REQUEST_RECORD_SOUND);

						//					AudioView popup = new AudioView(S00113.this);
						//
						//					PopupWindow popupWindow = new PopupWindow(popup, android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
						//
						//					popupWindow.showAtLocation(popup, Gravity.NO_GRAVITY, 0,0);
						//					popup.setPopupWindow(popupWindow);
						//					popup.setOnGetFilePathListener(new getFilePathListener() {
						//						@Override
						//						public void getFilePathCallback(String path) {
						//							File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".mp4");
						//
						//							mSelectImg.clear();
						//							mSelectImg.add(path);
						//							mUploadFileType = 2;
						//							mReplyPhotoView.setVisibility(View.VISIBLE);
						//							mReplyPhotoView.setImage(R.drawable.write_add_record_bg, 3);
						//						}
						//					});
					}

				}
			});


			mReplyAdapter = new S00113_ListAdapter(this, mReplyItems, this, mUnknown, myApp);
			mReplyListView.addHeaderView(mHeaderView);
			mReplyListView.setAdapter(mReplyAdapter);
			mMenuBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog = new CommonDialog(S00113.this);
					mDialog.createBoardMenu(new onMenuItemSelectListener() {
						@Override
						public void onMenuItemSelected(int position) {
							mDialog.dismiss();

							if(position == 2) {
								BrUtilManager.getInstance().ShowDialog2btn(S00113.this, "알림", "게시글을 삭제하시겠습니까?",  new dialogclick() {
									@Override
									public void setondialogokclick() {
										// TODO Auto-generated method stub
										requestBoardDelete(mSeq);

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
								Intent intent = new Intent(S00113.this, SC004.class);
								intent.putExtra("seq", mSeq);
								intent.putExtra("modify", true);
								startActivityForResult(intent, UPDATE_BOARD_CONTENT);
								//								mStatusCallBack.updateCallback(mSelectSeq);
							}
						}
					});
					mDialog.show();
				}
			});


			//		mPictureArr.add(mPictureView1);
			//		mPictureArr.add(mPictureView2);
			//		mPictureArr.add(mPictureView3);
			//		mPictureArr.add(mPictureView4);
			//		mPictureArr.add(mPictureView5);
			//		
			//		for(DeleteImageView picture: mPictureArr) {
			//			picture.setOnDeleteImageCallback(new onDeleteImageCallback() {
			//				@Override
			//				public void deleteImageCallback() {
			//					getSelectImageCnt();
			//				}
			//			});
			//		}
			//		
			//		btn.setOnClickListener(new OnClickListener() {
			//			@Override
			//			public void onClick(View v) {
			////				upload();
			//				if(MAX_SELECT_COUNT - mSelectImgCount <= 0) {
			//					BrUtilManager.getInstance().showToast(getBaseContext(), "더이상 선택 할 수 없습니다.");
			//				}
			//				else {
			//					Intent intent = new Intent(SC003.this,CustomGallery.class);
			//					intent.putExtra("size", MAX_SELECT_COUNT - mSelectImgCount);//선택가능한갯수
			//					startActivityForResult(intent, GALLERY_REQUEST_CODE);	
			//				}
			//			}
			//		});
			
			mReplyBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//				Intent intent = new Intent(S00113.this, SC003_ReplyView.class);
					//				intent.putExtra("seq", mSeq);
					//				intent.putExtra("unknown", mUnknown);
					//				startActivityForResult(intent,REPLY_VIEW_REQUEST_CODE);
				}
			});

			mBackBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setResult((mChangeDataYn) ? 1 : 2);
					finish();
				}
			});

			mContentsLayout.setVisibility(View.GONE);

			Intent resultIntent = getIntent();
			mSeq = resultIntent.getIntExtra("seq", 0);
			toggleLayout(false);

			requestGetDetail(mSeq);
			requestReplyList();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	

	private void toggleLayout(boolean yn) {
		if(yn) {
			mReportLayout.setVisibility(View.VISIBLE);
			mRelativeLayout.setVisibility(View.GONE);
		}
		else {
			mReportLayout.setVisibility(View.GONE);
			mRelativeLayout.setVisibility(View.VISIBLE);
		}
	}
	//
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(mReportLayout.getVisibility() == View.VISIBLE) {
				toggleLayout(false);
				return true;
			}
			else {
				int result;
				if(mChangeDataYn)
					result = 1;
				else  
					result = 2;
				setResult(result);
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	//no=int, commentId=String, commentContents=String,
	//photoName=String, movName=String, audioName=String, tagId=String, 

	private void showReportAlert() {
		try {
			BrUtilManager.getInstance().ShowDialog2btn(this, "알림", "정말 신고하시겠습니까?",  new dialogclick() {

				@Override
				public void setondialogokclick() {
					// TODO Auto-generated method stub
					//게시글
					if(mReportGubun == 1) {
						requestBoardReport(mSeq, mUserId, mKind);	
					}
					//댓글
					else if(mReportGubun == 2) {
						requestReplyReport(mReplySeq, mReplyUserId, mReplyKind);
						//					requestBoardReport(mSeq, mUserId, mKind);
					}

				}

				@Override
				public void setondialocancelkclick() {
					return;
				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	private void requestBoardLike(int seq, String userId) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", seq+"");
			params.put("userId", userId);
			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_LIKE, params, JSONObject.class, this, "boardLikeCallBack");			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	public void boardLikeCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				BrUtilManager.getInstance().showToast(this, message);
				int count = Integer.parseInt(mIcoGoodTxt.getText().toString());
				mIcoGoodTxt.setText((count+1)+"");
				mChangeDataYn = true;
			}
			else {
				BrUtilManager.getInstance().showToast(this, message);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	private void requestBoardDelete(int seq) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", seq+"");

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_DELETE, params, JSONObject.class, this, "boardDeleteCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void boardDeleteCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				BrUtilManager.getInstance().showToast(this, message);
				setResult(1);
				finish();
			}
			else {
				BrUtilManager.getInstance().showToast(this, message);
			}	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}


	private void requestBoardReport(int seq, String userId, int kind) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", seq+"");
			params.put("userId",  myApp.getUserInfo().getUserId());
			params.put("kind", kind);

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_REPORT, params, JSONObject.class, this, "boardReportCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void boardReportCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				BrUtilManager.getInstance().showToast(this, message);
				int count = Integer.parseInt(mIcoReportTxt.getText().toString());
				mIcoReportTxt.setText((count+1)+"");
				mIsReport = 1;
				//			c.get(mSelectPosition).setReportCnt(mReplyItems.get(mSelectPosition).getReportCnt()+1);
				toggleLayout(false);
				mChangeDataYn = true;
			}
			else {
				BrUtilManager.getInstance().showToast(this, message);
			}	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}


	private void requestGetDetail(int seq) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", seq+"");
			params.put("userId", mUserId);
			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_DETAIL, params, JSONObject.class, this, "boardDetailCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void boardDetailCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			if(!jsonObject.isNull("data")) {
				Log.d("JSON", jsonObject.toString());
				mContentsLayout.removeAllViews();
				mPathArr.clear();
				mResizeViewArr.clear();
				JSONObject item = jsonObject.getJSONObject("data");
				//			Log.d("JSON", item.toString());
				MainListVO mainListVO = new MainListVO();

				mDetailTempName = item.getString("tempName");
				String contents = item.getString("contents");
				String birthDay = item.getString("birthDay");
				int unknown = item.getInt("unknown");
				mUnknown = unknown == 1 ? true : false;
				int seriousLevel = item.getInt("seriousLevel");
				int seriousLevel2 = item.getInt("seriousLevel2");
				//			int deleted = item.getInt("deleted");
				//			mKind = item.getInt("kind");
				//			int religion = item.getInt("religion");
				String writeTime = item.getString("writeTime");
				final int memLvl = item.getInt("memLvl");
				String nickName = item.getString("nickName");
				int repotCnt = item.getInt("reportCnt");
				//				mUserId = item.getString("userId");
				final String detailUserId = item.getString("userId");
				mIsReport = item.getInt("isReport");


				if(detailUserId.equals(mUserId)) {
					mMenuBtn .setVisibility(View.VISIBLE);
				}
				else {
					mMenuBtn .setVisibility(View.INVISIBLE);
				}

				mKind = item.getInt("kind");
				int sex = item.getInt("sex");
				int likeCnt = item.getInt("likeCnt");
				int replyCnt = item.getInt("replyCnt");
				String photoName1 = item.getString("photoName1");
				String photoName2 = item.getString("photoName2");
				String photoName3 = item.getString("photoName3");
				String photoName4 = item.getString("photoName4");
				String photoName5 = item.getString("photoName5");
				String movName = item.getString("movName");
				String audioName = item.getString("audioName");
				String happyPhoto = item.getString("happyPhoto");

				TextView nickNameTxt = (TextView) findViewById(R.id.list_item_name_txt);
				TextView cateTxt = (TextView) findViewById(R.id.list_item_category_txt);
				TextView contentTxt = (TextView) findViewById(R.id.list_item_content_txt);
				TextView dateTxt = (TextView) findViewById(R.id.list_item_time_txt);
				ImageView maskImgView = (ImageView) findViewById(R.id.profile_mask_img);
				if(mUnknown==true) {
					nickNameTxt.setText(mDetailTempName);
				}
				else {
					nickNameTxt.setText(nickName);
				}
				mIcoGoodTxt = (TextView) findViewById(R.id.ico_good_txt);
				mIcoReplyTxt = (TextView) findViewById(R.id.ico_reply_txt);
				mIcoReportTxt = (TextView) findViewById(R.id.ico_speaker_txt);
				if(movName.length() > 10)createVideoView(movName);
				if(photoName1.length() > 10) createImageView(photoName1);
				if(photoName2.length() > 10) createImageView(photoName2);
				if(photoName3.length() > 10) createImageView(photoName3);
				if(photoName4.length() > 10) createImageView(photoName4);
				if(photoName5.length() > 10) createImageView(photoName5);
				if(audioName.length() > 10) createAudioView(audioName);
				mReplyAdapter.setUnknown(mUnknown);
				mGoodBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(detailUserId.equals(myApp.getUserInfo().getUserId())) {
							BrUtilManager.getInstance().showToast(S00113.this, "본인이 작성한 글은 좋아요를 누를 수 없습니다.");
							return;
						}
						requestBoardLike(mSeq, mUserId);
					}
				});

				mReportBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(memLvl > 1 &&memLvl < 5) {
							BrUtilManager.getInstance().showToast(S00113.this, "해피인이 쓴 글은 신고할 수 없습니다.");
						}
						else if(detailUserId.equals(myApp.getUserInfo().getUserId())) {
							BrUtilManager.getInstance().showToast(S00113.this, "본인이 작성한 글은 신고하기를 할 수 없습니다.");
						}
						else if(mIsReport != 0) {
							BrUtilManager.getInstance().showToast(S00113.this, "이미 신고한 글입니다.");
						}
						else {
							toggleLayout(true);
							mReportGubun = 1;	
						}

						//					showReportAlert(1, 0 , "" , 0);
						//					requestBoardReport(mSeq, mUserId, mKind);
					}
				});

				//			//위험레벨 10
				//			if(seriousLevel == 10) {
				//
				//			}
				//			//어드민
				//			else if(memLvl == 5) {
				//				//bold처리
				//			}
				//			else if(memLvl == 1) {
				//				//				mAddPictureBtn.setVisibility(View.INVISIBLE);
				//				//				mCropAddPictureBtn.setVisibility(View.INVISIBLE);
				//				//				mAudioAddBtn.setVisibility(View.INVISIBLE);
				//			}
				//			else {
				//				//				mAddPictureBtn.setVisibility(View.VISIBLE);
				//				//				mCropAddPictureBtn.setVisibility(View.VISIBLE);
				//				//				mAudioAddBtn.setVisibility(View.VISIBLE);
				//				if(memLvl == 2) {
				//
				//				}
				//				else if(memLvl == 3) {
				//
				//				}
				//				else if(memLvl == 4) {
				//
				//				}
				//				//bold처리
				//			}
				//			icoGoodTxt.setText(CommonFormat.getAgeFromBirthday(birthDay)+"");
				mIcoGoodTxt.setText(likeCnt+"");
				mIcoReplyTxt.setText(replyCnt+"");
				mIcoReportTxt.setText(repotCnt+"");
				loadingImages();

				mainListVO.setBirthDay(birthDay);
				mainListVO.setMemLvl(memLvl);
				mainListVO.setSeriousLevel(seriousLevel);
				mainListVO.setSeriousLevel2(seriousLevel2);
				mainListVO.setSex(sex);

				dateTxt.setText(Common.CreateDataWithCheck(writeTime));
				//			if(unknown==0) {
				//				nickNameTxt.setText(nickName);
				//			}
				//			else {
				//				nickNameTxt.setText("");
				//			}
				cateTxt.setText(Common.getCategoryKindString(mKind));
				contentTxt.setText(contents);



				if(memLvl == 2) {
					maskImgView.setImageResource(R.drawable.happy_prf_detail_bg_01);
				}
				else if(memLvl == 3) {
					maskImgView.setImageResource(R.drawable.happy_prf_detail_bg_02);
				}
				else if(memLvl == 4) {
					maskImgView.setImageResource(R.drawable.happy_prf_detail_bg_03);
				}
				else {
					maskImgView.setImageResource(R.drawable.prf_box_g_bg);
				}

				if(memLvl == 1 ) {
					myApp.imageloder.displayImage(Common.getProfileImage(this,mainListVO), mProfileImageView,myApp.options);
					//					mProfileImageView.setImageBitmap(Common.getProfileImage(this, mainListVO));
				}
				else if(memLvl == 5) {
					if(happyPhoto.startsWith("http")) {
						myApp.imageloder.displayImage(happyPhoto, mProfileImageView);
					}
					else {
						myApp.imageloder.displayImage(Global.host + happyPhoto, mProfileImageView);	
					}
					
					//				holder.prgImg.setImageBitmap(Common.getProfileImage(mContext, item));
				}
				else {
					if(happyPhoto.startsWith("http")) {
						myApp.imageloder.displayImage(happyPhoto, mProfileImageView);
					}
					else {
						myApp.imageloder.displayImage(Global.host + happyPhoto, mProfileImageView);	
					}
					
					//				holder.prgImg.setImageBitmap(Common.getProfileImage(mContext, item));
				}


				if(mMemLvl == 1) {
					maddbtnLayout.setVisibility(View.GONE);

					if(mUnknown == true) {
						mUnknownCheckBox.setChecked(true);
						mUnknownCheckBox.setClickable(false);
						mUnknownCheck= true;
					}
					else {
						mUnknownCheckBox.setChecked(false);
						mUnknownCheckBox.setClickable(true);
						mUnknownCheck = false;
						mUnknownCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
								mUnknownCheck = isChecked;
							}
						});
					}
				}
				else {
					maddbtnLayout.setVisibility(View.VISIBLE);

					mUnknownCheckBox.setChecked(false);
					mUnknownCheckBox.setClickable(false);
					mUnknownCheck = false;
					mUnknownCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							mUnknownCheck = isChecked;
						}
					});
				}
				//			mProfileImageView.setImageBitmap(Common.getProfileImage(S00113.this, mainListVO));
			}
			else {
				BrUtilManager.getInstance().showToast(this, "데이터를 가져오지 못했습니다.");
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		//		mParentLayout.setVisibility(View.VISIBLE);
	}

	private void createImageView(String path) {

		try {
			final RelativeLayout view = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.s00113_image_item, null);
			mPathArr.add(path);

			mContentsLayout.setVisibility(View.VISIBLE);
			ResizeImageView img = (ResizeImageView) view.findViewById(R.id.resize_imgview);
			mContentsLayout.addView(view);
			mResizeViewArr.add(img);	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

		//		myApp.imageloder.displayImage(path, img);
	}

	private void loadingImages() {
		try {
			AsyncTask<Void, Void, Void> loadings = new AsyncTask<Void, Void, Void>() {

				@Override
				protected void onPreExecute() {
					mLoadingLayout.setVisibility(View.VISIBLE);
					super.onPreExecute();

				}
				@Override
				protected Void doInBackground(Void... params) {

					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					for(int i = 0 ; i < mPathArr.size(); i ++) {
						myApp.imageloder.displayImage(mPathArr.get(i), mResizeViewArr.get(i));
					}


					mLoadingLayout.setVisibility(View.GONE);

				}

			};
			loadings.execute();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	private void createVideoView(final String path) {
		try {
			final RelativeLayout view = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.s00113_video_item, null);
			String[] pathSplit = path.split("::");
			final String videoPath = pathSplit[0];
			String thumb = pathSplit[1];

			mContentsLayout.setVisibility(View.VISIBLE);
			TextView video = (TextView) view.findViewById(R.id.reply_video_txt);
			//			ResizeImageView img = (ResizeImageView) view.findViewById(R.id.resize_imgview);
			mContentsLayout.addView(view);
			video.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(videoPath + "");
					Intent intent = new Intent(Intent.ACTION_VIEW);  
					intent.setDataAndType(uri, "video/*");  
					startActivity(intent);
				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

		//		myApp.imageloder.displayImage(thumb, img);
		//		ImageButton playBtn = (ImageButton) view.findViewById(R.id.video_play_btn);
		//		playBtn.setOnClickListener(new OnClickListener() {
		//			@Override
		//			public void onClick(View v) {
		//				Intent i = new Intent(Intent.ACTION_VIEW, Uri
		//						.parse(video));
		//				startActivity(i);
		//			}
		//		});
	}

	private void createAudioView(final String path) {

		try {
			final RelativeLayout view = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.s00113_audio_item, null);

			mContentsLayout.setVisibility(View.VISIBLE);
			TextView audio = (TextView) view.findViewById(R.id.reply_audio_txt);
			mContentsLayout.addView(view);
			mContentsLayout.setVisibility(View.VISIBLE);
			audio.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(path + "");
					Intent intent = new Intent(Intent.ACTION_VIEW);  
					intent.setDataAndType(uri, "audio/*");  
					startActivity(intent);
				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	//		private void getSelectImageCnt() {
	//			mSelectImgCount = 0;
	//			for(int i = 0 ; i < mPictureArr.size() ; i ++) {
	//				if(mPictureArr.get(i).isPictureYn()) {
	//					mSelectImgCount++;
	//				}
	//			}
	//		}

	//			public void upload(){
	//		
	//				AWSFileUploader2 uploader = new AWSFileUploader2(this);
	//				//accesskey 상관 없는듯 합니다.
	//				//		uploader.setAwsAccesskey("AKIAJ6EUVTNPNFL4WVSQ"); //accesskey
	//				uploader.setAwsAccesskey("AKIAJRVQWLQAFSWNG5XQ"); 
	//				//		uploader.setAwsSecretKey("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"); //secretkey
	//				uploader.setAwsSecretKey("0KaTryZfvej0oeZXLoLcDlX5NyIJwYRKTvQGfgNB");
	//				uploader.setAWSBuckekName("brainyx1");//버킷이름
	//		//		uploader.setFilename("ec_temp_prrzppfuoz.jpg");//파일명
	//				String[] files = new String[mSelectImg.size()];
	//				files[0] = "app_icon(36).png";
	//				files[1] = "app_icon(1024).png";
	//				files[2] = "app_icon(36)(원본).png";
	//				files[3] = "app_icon(72).png";
	//				files[4] = "app_icon(96).png";
	//				files[5] = "app_icon(114).png";
	//				
	//				
	//				String[] filesPath = new String[mSelectImg.size()];
	//				uploader.setFiles(files);
	//				uploader.setFilesPath(filesPath);
	//				
	//		//		uploader.setFilePath(Environment.getExternalStorageDirectory() + "/");//실제파일경로
	//				uploader.setListener(new AWSFileUploader2.OnResultListener() { // 업로드완료후 콜백
	//					@Override
	//					public boolean setOnResultListener(boolean result) {
	//						//				Log.e("TAG", result+"");
	//						//성공
	//						if(result) {
	//						}
	//						//실패
	//						else {
	//		
	//						}
	//						return false;
	//					}
	//		
	//					@Override
	//					public void setOnResult(ArrayList<String> result) {
	//						
	//						for(int i = 0 ; i < result.size() ; i ++) {
	//							mPictureArr.get(i).setImage(result.get(i));
	//						}
	//						
	//					}
	//				});
	//				uploader.execute();
	//		
	//				//		Log.d("TAG", "s3파일패스.,.");
	//			}


	//		private void initSelectImage() {
	//			try {
	//				AsyncTask<Void, Void, Void> loadImage = new AsyncTask<Void, Void, Void>() {
	//	
	//					@Override
	//					protected Void doInBackground(Void... params) {
	//						for(int i = 0 ; i < mSelectImg.size() ; i ++) {
	//							loadImage(mSelectImg.get(i) , mPictureArr.get(i));
	//						}
	//						return null;
	//					}
	//					
	//					@Override
	//					protected void onPreExecute() {
	//						mLoadingLayout.setVisibility(View.VISIBLE);
	//						super.onPreExecute();
	//					}
	//					
	//					@Override
	//					protected void onPostExecute(Void result) {
	//						super.onPostExecute(result);
	//						
	//						
	//						
	//						for(int i = 0 ;i < mPictureBitmapArr.size() ; i++) {
	//							
	//							for(int j = 0 ; j < mPictureArr.size() ; j++) {
	//								if(!mPictureArr.get(j).isPictureYn()) {
	//									mPictureArr.get(j).setImage(mPictureBitmapArr.get(i));
	//									break;
	//								}
	//							}
	//						}
	//						mLoadingLayout.setVisibility(View.GONE);
	//						getSelectImageCnt();
	//					}
	//					
	//					@Override
	//					protected void onProgressUpdate(Void... values) {
	//						super.onProgressUpdate(values);
	//					}
	//				};
	//				
	//				loadImage.execute();
	//			} catch (Exception e) {
	//				
	//			}
	//		}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		try {

			if(requestCode == GALLERY_REQUEST_CODE){

				if(resultCode == RESULT_OK) {
					mUploadFileType = 1;
					mSelectImg.clear();
					mPictureBitmapArr.clear();
					mSelectImg = data.getStringArrayListExtra("simg");
					Log.d("TAG", mSelectImg.get(0));
					Log.d("TAG", mSelectImg.get(0).substring(0, mSelectImg.get(0).lastIndexOf("/")+1));
					Log.d("TAG", mSelectImg.get(0).substring(mSelectImg.get(0).lastIndexOf("/")+1, mSelectImg.get(0).length()));
					Log.d("TAG",Environment.getExternalStorageDirectory().toString() + "/");
					initSelectImage();
				}


				//					for(int i = 0 ; i < mSelectImg.size() ; i ++) {
				//						loadImage(mSelectImg.get(i) , mPictureArr.get(i));
				//					}
			}
			else if(requestCode == CAMERA_CROP_REQUEST_CODE) {
				if(resultCode == RESULT_OK) {
					mUploadFileType = 1;
					File outFilePath = new File(myApp.getFileDir_Ex() + "/tmp_"+ System.currentTimeMillis() + ".jpg");

					mCropFromImageUri = Uri.fromFile(outFilePath);

					Intent intent = new Intent("com.android.camera.action.CROP");
					intent.setDataAndType(mCropImageCacheUri, "image/*");
					intent.putExtra("scale", true);
					intent.putExtra("outputFormat", "JPEG");
					intent.putExtra("output", mCropFromImageUri); // 파일로 저장
					try {
						startActivityForResult(intent, CAMERA_CROP_FROM_REQUEST_CODE);
					} catch (Exception e) {
						e.getMessage();
					}
				}

			}
			else if(requestCode == CAMERA_CROP_FROM_REQUEST_CODE) {
				if(resultCode == RESULT_OK) {
					mUploadFileType = 1;
					if (mCropFromImageUri != null) {
						mSelectImg.clear();
						mPictureBitmapArr.clear();
						Bitmap photo = null;
						mCropImagePath = mCropFromImageUri.getPath();
						try {
							photo = Images.Media.getBitmap(getContentResolver(),mCropFromImageUri);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
						Bitmap curThumb = photo;
						curThumb.compress(CompressFormat.JPEG, 70, byteArr);
						mSelectImg.add(mCropImagePath);
						initSelectImage();
					}
				}

			}
			else if(requestCode == VIDEO_REQUEST_CODE) {
				if(resultCode == RESULT_OK) {
					mSelectImg.clear();
					Uri uri = data.getData();
					String path = getPath(uri);
					mUploadFileType = 3;

					//					
					//					File videoFile = new File(path);
					//					
					mVideoThumbnail = ThumbnailUtils.createVideoThumbnail(path, Thumbnails.MINI_KIND);
					//					else {
					File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".jpg");
					boolean flag = BrImageUtilManager.getInstance().saveOutput(this,mVideoThumbnail,getImageUri(testfile.getAbsolutePath()));
					mSelectImg.add(testfile.getAbsolutePath());
					mSelectImg.add(path);
					mReplyPhotoView.setVisibility(View.VISIBLE);
					//					mReplyPhotoView.setImage(mVideoThumbnail, 2);
					mReplyPhotoView.setImage(R.drawable.write_add_movie_bg, 2);
				}


				//					getSelectImageCnt();
			}
			else if(requestCode == ACTIVITY_REQUEST_RECORD_SOUND) {
				if(resultCode == RESULT_OK) {
					Uri audioUri = data.getData();
					String path = getPath(audioUri);

					File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".mp4");

					mSelectImg.clear();
					mSelectImg.add(path);
					mUploadFileType = 2;
					mReplyPhotoView.setVisibility(View.VISIBLE);
					mReplyPhotoView.setImage(R.drawable.write_add_record_bg, 3);
				}

			}
			else if (requestCode == CAPTURE_REQUEST_MOVIE)
			{
				if(resultCode == RESULT_OK) {
					mSelectImg.clear();
					Uri uri = data.getData();
					String path = getPath(uri);
					mUploadFileType = 3;
					initCaptureVideo(path);
				}


				//					
				//					File videoFile = new File(path);
				//					
				//					mVideoThumbnail = ThumbnailUtils.createVideoThumbnail(path, Thumbnails.MINI_KIND);
				//					//					else {
				//					File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".jpg");
				//					boolean flag = BrImageUtilManager.getInstance().saveOutput(this,mVideoThumbnail,getImageUri(testfile.getAbsolutePath()));
				//					mSelectImg.add(testfile.getAbsolutePath());
				//					mSelectImg.add(path);
				//					mReplyPhotoView.setVisibility(View.VISIBLE);
				//					mReplyPhotoView.setImage(mVideoThumbnail, 2);
			}
			else if(requestCode == UPDATE_BOARD_CONTENT) {
				if(resultCode == 1) {
					mChangeDataYn = true;
					requestGetDetail(mSeq);
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
			//			linearLayout_loading.setVisibility(View.GONE);
		}
	}

	private void initCaptureVideo(final String path) {
		try {
			AsyncTask<Void, Void, String> loadImage = new AsyncTask<Void, Void, String>() {

				@Override
				protected String doInBackground(Void... params) {
					mVideoThumbnail = ThumbnailUtils.createVideoThumbnail(path, Thumbnails.MINI_KIND);
					//					else {
					File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".jpg");
					boolean flag = BrImageUtilManager.getInstance().saveOutput(S00113.this,mVideoThumbnail,getImageUri(testfile.getAbsolutePath()));


					return testfile.getAbsolutePath();
				}

				@Override
				protected void onPreExecute() {
					mLoadingLayout.setVisibility(View.VISIBLE);
					super.onPreExecute();
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);

					mSelectImg.add(result);
					mSelectImg.add(path);

					mReplyPhotoView.setVisibility(View.VISIBLE);
					mReplyPhotoView.setImage(mVideoThumbnail, 2, true);
					mLoadingLayout.setVisibility(View.GONE);
				}

				@Override
				protected void onProgressUpdate(Void... values) {
					super.onProgressUpdate(values);
				}
			};

			loadImage.execute();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	public void uploadFile(){
		try {
			AWSFileUploader2 uploader = new AWSFileUploader2(this);
//			uploader.setAwsAccesskey("AKIAJRVQWLQAFSWNG5XQ"); 
//			uploader.setAwsSecretKey("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");
//			uploader.setAWSBuckekName("brainyx1");//버킷이름
			uploader.setFolderName(myApp.getUserInfo().getUserId());
			String[] files = null;
			String[] filesPath  = null;
			if(mSelectImg.size() == 2) {
				files = new String[mSelectImg.size()];
				files[0] = mSelectImg.get(1).substring(mSelectImg.get(1).lastIndexOf("/")+1, mSelectImg.get(1).length());
				files[1] = mSelectImg.get(0).substring(mSelectImg.get(0).lastIndexOf("/")+1, mSelectImg.get(0).length());

				filesPath = new String[mSelectImg.size()];
				filesPath[0] = mSelectImg.get(1).substring(1, mSelectImg.get(1).lastIndexOf("/")+1);
				filesPath[1] = mSelectImg.get(0).substring(0, mSelectImg.get(0).lastIndexOf("/")+1);
			}
			else {
				files = new String[1];
				files[0] = mSelectImg.get(0).substring(mSelectImg.get(0).lastIndexOf("/")+1, mSelectImg.get(0).length());

				filesPath = new String[1];
				filesPath[0] = mSelectImg.get(0).substring(0, mSelectImg.get(0).lastIndexOf("/")+1);
			}


			uploader.setFiles(files);
			uploader.setFilesPath(filesPath);

			uploader.setListener(new AWSFileUploader2.OnResultListener() { // 업로드완료후 콜백
				@Override
				public boolean setOnResultListener(boolean result) {
					//				Log.e("TAG", result+"");
					//성공
					if(result) {
					}
					//실패
					else {

					}
					return false;
				}

				@Override
				public void setOnResult(ArrayList<String> result) {
					Log.d("IMG_URI", result.get(0));
					//				for(int i = 0 ; i < result.size() ; i ++) {
					if(mUploadFileType == 1) {
						requestReplyAdd(result.get(0), "", "");
					}
					else if(mUploadFileType == 2) {
						requestReplyAdd("", result.get(0), "");
					}
					else if(mUploadFileType == 3) {
						requestReplyAdd("", "", result.get(1)+"::"+result.get(0));
					}
					//					requestReplyAdd(result.get(i), "", "");
					//				}

				}
			});
			uploader.execute();

		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}
	private Uri getImageUri(String path) {
		return Uri.fromFile(new File(path));
	}


	private void loadImage(String path, DeleteImageView imageLayout) {
		try {
			Bitmap resize = BrImageUtilManager.getInstance().getBitmap(path, true,800,600);

			File testfile = new File(myApp.getFileDir_Ex() + MSUtil.getFileKey() + ".jpg");
			boolean flag = BrImageUtilManager.getInstance().saveOutput(this,resize,getImageUri(testfile.getAbsolutePath()));

			mPictureBitmapArr.add(resize);	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

		//		myApp.imageloder.displayImage(path, imageLayout.getImageView(), myApp.options);
	}

	private void initSelectImage() {
		try {
			AsyncTask<Void, Void, Void> loadImage = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					loadImage(mSelectImg.get(0) , mReplyPhotoView);
					return null;
				}

				@Override
				protected void onPreExecute() {
					mLoadingLayout.setVisibility(View.VISIBLE);
					super.onPreExecute();
				}

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);



					//					for(int i = 0 ;i < mPictureBitmapArr.size() ; i++) {
					//
					//						for(int j = 0 ; j < mPictureArr.size() ; j++) {
					//							if(!mPictureArr.get(j).isPictureYn()) {
					//								mPictureArr.get(j).setImage(mPictureBitmapArr.get(i));
					//								break;
					//							}
					//						}
					//					}
					mReplyPhotoView.setVisibility(View.VISIBLE);
					mReplyPhotoView.setImage(mPictureBitmapArr.get(0), 1, true);
					mLoadingLayout.setVisibility(View.GONE);
					//					getSelectImageCnt();
				}

				@Override
				protected void onProgressUpdate(Void... values) {
					super.onProgressUpdate(values);
				}
			};

			loadImage.execute();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	private void requestReplyAdd(String photo, String audio, String video) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("no", mSeq);
			String content = "";
			//			String tag = bubbleEditText.getTags();
			//			String tag = "";
			if(tags.equals("") ) {
				content = mReplyEditText.getText().toString();
			}
			else {
				String temp = mReplyEditText.getText().toString();
				content = temp.replace(tags, "@"+tags+"::");
			}

			params.put("commentContents", content);

			params.put("photoName", photo);
			params.put("movName", video);
			params.put("audioName", audio);
			params.put("tagId", tags);
			if(mUnknownCheck) {
				params.put("commentId", mUserId);
				params.put("tempName",myApp.getTempResource(mDetailTempName));
			}
			else {
				params.put("commentId", mUserId);
				params.put("tempName", "");
			}


			AQuery aquery = new AQuery(this);	

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_ADD_REPLY, params, JSONObject.class, this, "replyAddCallBack");			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	public void replyAddCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			Log.d("json", jsonObject.toString());
			boolean result = jsonObject.getBoolean("result");

			if(result) {
				mChangeDataYn = true;
				mReplyEditText.setText("");
				mReplyPhotoView.setVisibility(View.GONE);
				mReplyPhotoView.setDefaultImage();
				mSelectImgCount = 0;
				mUploadFileType = -1;
				int cnt = Integer.parseInt(mIcoReplyTxt.getText().toString());
				cnt++;
				tags = "";
				mIcoReplyTxt.setText(String.valueOf(cnt));
				requestReplyList();
			}
			else {

			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}
	private void requestReplyLike(int seq, String userId) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", seq+"");
			params.put("userId", userId);

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_REPLY_LIKE, params, JSONObject.class, this, "replyLikeCallBack");		
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void replyLikeCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			Log.d("json", jsonObject.toString());
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {

				boolean resultFlag = jsonObject.getBoolean("resultFlag");
				if(resultFlag) {
					mReplyItems.get(mSelectPosition).setLikeCnt(mReplyItems.get(mSelectPosition).getLikeCnt()+1);
				}
				else {
					mReplyItems.get(mSelectPosition).setLikeCnt(mReplyItems.get(mSelectPosition).getLikeCnt()-1);
				}
				BrUtilManager.getInstance().showToast(getBaseContext(), message);

				mReplyAdapter.notifyDataSetChanged();
				mChangeDataYn = true;
			}
			else {
				BrUtilManager.getInstance().showToast(this, message);
			}			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	private void requestReplyDelete(int seq) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", seq+"");

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	
			aquery.progress(dialog).ajax(Global.BOARD_DELETE_REPLY, params, JSONObject.class, this, "replyDeleteCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void replyDeleteCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			Log.d("json", jsonObject.toString());
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				mReplyItems.remove(mSelectPosition);
				mReplyAdapter.notifyDataSetChanged();
				mChangeDataYn = true;
				int cnt = Integer.parseInt(mIcoReplyTxt.getText().toString());
				cnt--;
				mIcoReplyTxt.setText(String.valueOf(cnt));
			}
			else {
				BrUtilManager.getInstance().showToast(this, message);
			}	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	private void requestReplyReport(int seq, String userId, int kind) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", seq+"");
			params.put("userId", userId);
			params.put("kind", kind+"");

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_REPLY_REPORT, params, JSONObject.class, this, "replyReportCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void replyReportCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			Log.d("json", jsonObject.toString());
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				mChangeDataYn = true;
				BrUtilManager.getInstance().showToast(this, message);
				//			int count = Integer.parseInt(mIcoReplyTxt.getText().toString());
				//			mIcoReplyTxt.setText((count+1)+"");
				mReplyItems.get(mSelectPosition).setReportCnt(mReplyItems.get(mSelectPosition).getReportCnt()+1);
				mReplyItems.get(mSelectPosition).setIsReport(1);
				mReplyAdapter.notifyDataSetChanged();
				toggleLayout(false);

			}
			else {
				BrUtilManager.getInstance().showToast(this, message);
			}	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	private void requestReplyEdit(ReplyVO reply) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", reply.getSeq());
			params.put("no", reply.getNo());
			params.put("tempName", reply.getTempName());
		
			
			String content = "";
			if(tags.equals("") ) {
				content = reply.getCommentContents();
			}
			else {
				String temp = reply.getCommentContents();
				content = temp.replace(tags, "@"+tags+"::");
			}
			params.put("contents", content);
			params.put("tagId", tags);

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_EDIT_REPLY, params, JSONObject.class, this, "replyEditCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void replyEditCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			Log.d("json", jsonObject.toString());
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				mChangeDataYn = true;
				mReplyEditBtn.setVisibility(View.GONE);
				mReplyAddBtn.setVisibility(View.VISIBLE);
				mUnknownCheckBox.setVisibility(View.VISIBLE);
				
				if(mMemLvl == 1) {
					maddbtnLayout.setVisibility(View.GONE);	
				}
				else {
					maddbtnLayout.setVisibility(View.VISIBLE);
				}
				
				mReplyEditText.setText("");
				tags="";
				requestReplyList();
				//				mChangeDataYn = true;
				//				BrUtilManager.getInstance().showToast(this, message);
				//				//			int count = Integer.parseInt(mIcoReplyTxt.getText().toString());
				//				//			mIcoReplyTxt.setText((count+1)+"");
				//				mReplyItems.get(mSelectPosition).setReportCnt(mReplyItems.get(mSelectPosition).getReportCnt()+1);
				//				mReplyAdapter.notifyDataSetChanged();
				//				toggleLayout(false);

			}
			else {
				BrUtilManager.getInstance().showToast(this, message);
			}	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	private void requestReplyList() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", mSeq+"");
			params.put("sort", mSelectType+"");
			params.put("userId", mUserId);

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_REPLY, params, JSONObject.class, this, "replyListCallBack");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void replyListCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				mReplyItems.clear();
				Log.d("json_tag", jsonObject.toString());
				JSONArray items = jsonObject.getJSONArray("data");
				for(int i = 0 ; i < items.length() ; i ++) {
					JSONObject item = items.getJSONObject(i);
					int sex = item.getInt("sex");
					int likeCnt = item.getInt("likeCnt");
					int no = item.getInt("no");
					String commentContents = item.getString("commentContents");
					String birthDay = item.getString("birthDay");
					int type = item.getInt("type");
					String tagId = item.getString("tagId");
					String commentId = item.getString("commentId");
					String tagNickName = item.getString("tagNickName");
					String commentTime = item.getString("commentTime");
					String nickName = item.getString("nickName");
					int reportCnt = item.getInt("reportCnt");
					String movName = item.getString("movName");
					String audioName = item.getString("audioName");
					String photoName = item.getString("photoName");
					//				int unknown = item.getInt("unknown");
					int isReport = item.getInt("isReport");
					String happyPhoto = item.getString("happyPhoto");
					int memLvl = item.getInt("memLvl");
					String tempName = item.getString("tempName");
					int seq = item.getInt("seq");
					//								String tempName = item.getString("tempName");

					ReplyVO reply = new ReplyVO();
					reply.setMemLvl(memLvl);
					reply.setHappyPhoto(happyPhoto);
					reply.setMovName(movName);
					reply.setAudioName(audioName);
					reply.setPhotoName(photoName);
					reply.setSex(sex);
					reply.setLikeCnt(likeCnt);
					reply.setNo(no);
					reply.setCommentContents(commentContents);
					reply.setBirthDay(birthDay);
					reply.setType(type);
					reply.setTagId(tagId);
					reply.setCommentId(commentId);
					reply.setTagNickName(tagNickName);
					reply.setCommentTime(commentTime);
					reply.setNickName(nickName);
					reply.setReportCnt(reportCnt);
					reply.setSeq(seq);
					reply.setIsReport(isReport);
					//				reply.setUnknown(unknown);
					reply.setTempName(tempName);
					mReplyItems.add(reply);
				}

				mReplyAdapter.notifyDataSetChanged();
				//			BrUtilManager.getInstance().showToast(this, message);
				//			Log.d("json_TAg", jsonObject.toString());
				//			mListItems.get(mSelectPosition).setRepotCnt(mListItems.get(mSelectPosition).getRepotCnt() +1);
				//			mListAdapter.notifyDataSetChanged();
			}
			else {
				BrUtilManager.getInstance().showToast(this, message);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}


	//	public void onBackPressed() { // if child acticity end
	//		super.onBackPressed();
	//		int result;
	//		if(mChangeDataYn)
	//			result = 1;
	//		else  
	//			result = 2;
	//		setResult(result);
	//		finish();
	//	} 


	@Override
	public void callback(int type, int seq, String userId, int position) {
		mSelectPosition = position;
		requestReplyLike(seq, userId);
	}

	@Override
	public void deleteCallback(int seq, int position) {
		mSelectPosition = position;
		requestReplyDelete(seq);
		//		mSelectPosition = s
	}

	@Override
	public void reportCallback(int seq, String userId, int kind, int position) {
		try {
			mSelectPosition = position;
			mReportGubun = 2;
			toggleLayout(true);
			mReplyKind = kind;
			mReplySeq = seq;
			mReplyUserId = userId;
			//		showReportAlert(2, seq, userId, kind);
			//		requestReplyReport(seq, userId, kind);	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	@Override
	public void nickNameCallback(String nickName) {

		try {
			//			mReplyEditText.setText("");
			BubbleTagsEditText bubbleEditText = new BubbleTagsEditText(mReplyEditText, this);
			bubbleEditText.addBubble(nickName);
			tags =nickName;
			//			String edit = mReplyEditText.getText().toString() + "@" + nickName+"::";

			//			String edit = mReplyEditText.getText().toString() + nickName;
			//			mReplyEditText.setText(edit);
			//			Editable able = mReplyEditText.getText();
			//			Selection.setSelection(able, able.length());	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}
	private String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);	
	}

	@Override
	public void replyEditCallback(ReplyVO reply) {
		try {
			String tagId = reply.getTagId();
			String content = reply.getCommentContents();
			BubbleTagsEditText bubbleEditText = new BubbleTagsEditText(mReplyEditText, this);
			bubbleEditText.addBubble(tagId);
			tags =tagId;
			content = content.replace("@"+tags+"::","");
			mReplyEditText.append(content);
//			mReplyEditText.setText(reply.getCommentContents());
			mReplyEditText.setSelection(mReplyEditText.getText().length());
			mReplyEditBtn.setVisibility(View.VISIBLE);
			mReplyAddBtn.setVisibility(View.GONE);
			mUnknownCheckBox.setVisibility(View.GONE);
			maddbtnLayout.setVisibility(View.GONE);
			mReplyEditBtn.setTag(reply);

		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}


	//	public void InsertFile(String id, String filePath) {
	//		try {
	//			HashMap<String, Object> params = new HashMap<String, Object>();
	//			params.put(HttpUtil.KEY_URL, "http://192.168.1.165:8080/upload/insert");
	//			params.put("user_id", id);
	//			params.put("file_path", filePath);
	//			HttpUtil.getInstance().requestHttp(this, params,
	//					new OnAfterParsedData() {
	//				@Override
	//				public void onResult(boolean result, String resultData) {
	//					try {
	//						if(result){ /// 종료하면 여기로 탑니다
	//
	//							Toast.makeText(getBaseContext(),"성공", 1).show();
	//						}else{}	
	//					} catch (Exception e) {
	//					}finally{ /// 끝나면 핸들러로 화면셋팅
	//
	//					}
	//				}
	//			});
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(Global.mDetailRefreshProfile == true) {
			requestGetDetail(mSeq);
			requestReplyList();
			Global.mDetailRefreshProfile = false;
		}
	}

}
