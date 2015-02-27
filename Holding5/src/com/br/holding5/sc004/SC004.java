package com.br.holding5.sc004;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.ms.Common;
import com.br.holding5.ms.MSUtil;
import com.br.holding5.sc003.AWSFileUploader2;
import com.br.holding5.sc003.gallery.CustomGallery;
import com.br.holding5.sc003.gallery.DeleteImageView;
import com.br.holding5.sc003.gallery.DeleteImageView.onDeleteImageCallback;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.image.BrImageUtilManager;

public class SC004 extends BaseActivity{
	
	private int mUnknown;
	private ImageButton mBackBtn;
	private RadioButton mRadioGroup1Btn1;
	private RadioButton mRadioGroup1Btn2;
	private RadioButton mRadioGroup1Btn3;
	private RadioButton mRadioGroup1Btn4;
	private RadioButton mRadioGroup1Btn5;
	private RadioButton mRadioGroup1Btn6;
	private RadioButton mRadioGroup1Btn7;
	private RadioButton mRadioGroup1Btn8;
	private RadioButton mRadioGroup1Btn9;
	private RadioButton mRadioGroup1Btn10;
	private RadioButton mRadioGroup1Btn11;

	private EditText mBoardContentEdit;

	private RadioButton mRadioGroup2Btn1;
	private RadioButton mRadioGroup2Btn2;
	private RadioButton mRadioGroup2Btn3;
	private RadioButton mRadioGroup2Btn4;
	private RadioButton mRadioGroup2Btn5;
	private RadioButton mRadioGroup2Btn6;
	private RadioButton mRadioGroup2Btn7;
	private RadioButton mRadioGroup2Btn8;
	private RadioButton mRadioGroup2Btn9;
	private RadioButton mRadioGroup2Btn10;

	private RadioButton mRadioGroup3Btn1;
	private RadioButton mRadioGroup3Btn2;
	private RadioButton mRadioGroup3Btn3;
	private RadioButton mRadioGroup3Btn4;
	private RadioButton mRadioGroup3Btn5;

	private ImageButton mCropAddBtn;
	private ImageButton mPictureAddBtn;
	private ImageButton mAudioAddBtn;
	private ImageButton mVideoAddBtn;
	private ImageButton mBoardContentAddBtn;
	private ImageButton mCaptureVideoAddBtn;


	private CheckBox 	mUnknownCheckbox;

	private ArrayList<RadioButton> mRadioGroup1;
	private ArrayList<RadioButton> mRadioGroup2;
	private ArrayList<RadioButton> mRadioGroup2_1;
	private ArrayList<RadioButton> mRadioGroup3;

	private int mSelectGroup1;
	private int mSelectGroup2;
	private int mSelectGroup2_1;
	private int mSelectGroup3;

	private RelativeLayout mGroup2TopLayout;
	private LinearLayout mGroup2BottomLayout;
	private LinearLayout mLoadingLayout;

	private int mMemLvl;
	private DeleteImageView mPhotoView1;
	private DeleteImageView mPhotoView2;
	private DeleteImageView mPhotoView3;
	private DeleteImageView mPhotoView4;
	private DeleteImageView mPhotoView5;
	private DeleteImageView mPhotoView6;
	private DeleteImageView mPhotoView7;
	private LinearLayout	mImageLayout;

	private Uri mCropImageCacheUri;
	private Uri mCropFromImageUri;
	private String mCropImagePath;
	private int mSelectImgCount = 0;
	private final int MAX_SELECT_COUNT = 5;
	private int mUploadFileType = -1;


	private final int ACTIVITY_RECORD_SOUND = 1;
	private final int GALLERY_REQUEST_CODE = 1000;
	private final int CAMERA_CROP_REQUEST_CODE = 1001;
	private final int CAMERA_CROP_FROM_REQUEST_CODE = 1002;
	private final int VIDEO_REQUEST_CODE = 1003;
	private final int CAPTURE_REQUEST_MOVIE = 1004;

	private ArrayList<String> mSelectImg = new ArrayList<String>();
	private String mSelectAudioPath = "";
	private String mSelectVideoPath = "";
	private String mSelectVideoThumbPath = "";
	private ArrayList<Bitmap> mPictureBitmapArr = new ArrayList<Bitmap>();
	private ArrayList<DeleteImageView> mPictureArr = new ArrayList<DeleteImageView>();
	private Bitmap mVideoThumbnail;
	private boolean mChangeYn = false;
	private boolean mModify;
	private RelativeLayout mAddPhotoLayout;
	private RelativeLayout mAddLayout;
	private RelativeLayout mModifyLayout;
	private ImageButton mModifyBtn;
	private int mSeq;
	private String mTempName="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.sc004);

			mMemLvl = myApp.getUserInfo().getMemLvl();
			mBoardContentEdit = (EditText) findViewById(R.id.add_board_content_txt);
			mGroup2TopLayout = (RelativeLayout) findViewById(R.id.group_2_layout);
			mModifyLayout = (RelativeLayout) findViewById(R.id.modify_btn_layout);
			mGroup2BottomLayout = (LinearLayout) findViewById(R.id.group_2_bottom_layout);
			mLoadingLayout = (LinearLayout) findViewById(R.id.linearLayout_loading);
			mImageLayout = (LinearLayout) findViewById(R.id.add_picture_layout);
			mPhotoView1 = (DeleteImageView) findViewById(R.id.photo_view_1);
			mPhotoView2 = (DeleteImageView) findViewById(R.id.photo_view_2);
			mPhotoView3 = (DeleteImageView) findViewById(R.id.photo_view_3);
			mPhotoView4 = (DeleteImageView) findViewById(R.id.photo_view_4);
			mPhotoView5 = (DeleteImageView) findViewById(R.id.photo_view_5);
			mPhotoView6 = (DeleteImageView) findViewById(R.id.photo_view_6);
			mPhotoView7 = (DeleteImageView) findViewById(R.id.photo_view_7);

			mAddPhotoLayout = (RelativeLayout) findViewById(R.id.add_photo_layout);
			mAddLayout = (RelativeLayout) findViewById(R.id.add_layout);

			mPictureArr.add(mPhotoView1);
			mPictureArr.add(mPhotoView2);
			mPictureArr.add(mPhotoView3);
			mPictureArr.add(mPhotoView4);
			mPictureArr.add(mPhotoView5);
			mPictureArr.add(mPhotoView6);
			mPictureArr.add(mPhotoView7);

			mBackBtn = (ImageButton) findViewById(R.id.write_back_btn);
			mModifyBtn = (ImageButton) findViewById(R.id.board_modify_btn);
			mUnknownCheckbox = (CheckBox) findViewById(R.id.unknown_check);

			mRadioGroup1Btn1 = (RadioButton) findViewById(R.id.radio_group_1);
			mRadioGroup1Btn2 = (RadioButton) findViewById(R.id.radio_group_2);
			mRadioGroup1Btn3 = (RadioButton) findViewById(R.id.radio_group_3);
			mRadioGroup1Btn4 = (RadioButton) findViewById(R.id.radio_group_4);
			mRadioGroup1Btn5 = (RadioButton) findViewById(R.id.radio_group_5);
			mRadioGroup1Btn6 = (RadioButton) findViewById(R.id.radio_group_6);
			mRadioGroup1Btn7 = (RadioButton) findViewById(R.id.radio_group_7);
			mRadioGroup1Btn8 = (RadioButton) findViewById(R.id.radio_group_8);
			mRadioGroup1Btn9 = (RadioButton) findViewById(R.id.radio_group_9);
			mRadioGroup1Btn10 = (RadioButton) findViewById(R.id.radio_group_10);
			mRadioGroup1Btn11 = (RadioButton) findViewById(R.id.radio_group_11);

			mRadioGroup2Btn1 = (RadioButton) findViewById(R.id.radio_group1_1);
			mRadioGroup2Btn2 = (RadioButton) findViewById(R.id.radio_group1_2);
			mRadioGroup2Btn3 = (RadioButton) findViewById(R.id.radio_group1_3);
			mRadioGroup2Btn4 = (RadioButton) findViewById(R.id.radio_group1_4);
			mRadioGroup2Btn5 = (RadioButton) findViewById(R.id.radio_group1_5);
			mRadioGroup2Btn6 = (RadioButton) findViewById(R.id.radio_group1_1_1);
			mRadioGroup2Btn7 = (RadioButton) findViewById(R.id.radio_group1_1_2);
			mRadioGroup2Btn8 = (RadioButton) findViewById(R.id.radio_group1_1_3);
			mRadioGroup2Btn9 = (RadioButton) findViewById(R.id.radio_group1_1_4);
			mRadioGroup2Btn10 = (RadioButton) findViewById(R.id.radio_group1_1_5);

			mRadioGroup3Btn1 = (RadioButton) findViewById(R.id.radio_group2_1);
			mRadioGroup3Btn2 = (RadioButton) findViewById(R.id.radio_group2_2);
			mRadioGroup3Btn3 = (RadioButton) findViewById(R.id.radio_group2_3);
			mRadioGroup3Btn4 = (RadioButton) findViewById(R.id.radio_group2_4);
			mRadioGroup3Btn5 = (RadioButton) findViewById(R.id.radio_group2_5);

			mCropAddBtn = (ImageButton) findViewById(R.id.crop_add_picture_btn);
			mPictureAddBtn = (ImageButton) findViewById(R.id.add_picture_btn);
			mAudioAddBtn = (ImageButton) findViewById(R.id.add_audio_btn);
			mVideoAddBtn = (ImageButton) findViewById(R.id.add_video_btn);
			mBoardContentAddBtn = (ImageButton) findViewById(R.id.board_content_add_btn);
			mCaptureVideoAddBtn = (ImageButton) findViewById(R.id.capture_add_video_btn);

			mRadioGroup1 = new ArrayList<RadioButton>();
			mRadioGroup2 = new ArrayList<RadioButton>();
			mRadioGroup2_1 = new ArrayList<RadioButton>();
			mRadioGroup3 = new ArrayList<RadioButton>();

			mRadioGroup1.add(mRadioGroup1Btn1);
			mRadioGroup1.add(mRadioGroup1Btn2);
			mRadioGroup1.add(mRadioGroup1Btn3);
			mRadioGroup1.add(mRadioGroup1Btn4);
			mRadioGroup1.add(mRadioGroup1Btn5);
			mRadioGroup1.add(mRadioGroup1Btn6);
			mRadioGroup1.add(mRadioGroup1Btn7);
			mRadioGroup1.add(mRadioGroup1Btn8);
			mRadioGroup1.add(mRadioGroup1Btn9);
			mRadioGroup1.add(mRadioGroup1Btn10);
			mRadioGroup1.add(mRadioGroup1Btn11);

			mRadioGroup2.add(mRadioGroup2Btn1);
			mRadioGroup2.add(mRadioGroup2Btn2);
			mRadioGroup2.add(mRadioGroup2Btn3);
			mRadioGroup2.add(mRadioGroup2Btn4);
			mRadioGroup2.add(mRadioGroup2Btn5);
			mRadioGroup2_1.add(mRadioGroup2Btn6);
			mRadioGroup2_1.add(mRadioGroup2Btn7);
			mRadioGroup2_1.add(mRadioGroup2Btn8);
			mRadioGroup2_1.add(mRadioGroup2Btn9);
			mRadioGroup2_1.add(mRadioGroup2Btn10);

			mRadioGroup3.add(mRadioGroup3Btn1);
			mRadioGroup3.add(mRadioGroup3Btn2);
			mRadioGroup3.add(mRadioGroup3Btn3);
			mRadioGroup3.add(mRadioGroup3Btn4);
			mRadioGroup3.add(mRadioGroup3Btn5);

			for(int i = 0 ; i < mRadioGroup1.size() ; i++) {
				mRadioGroup1.get(i).setTag(i);
			}

			for(int i = 0 ; i < mRadioGroup2.size() ; i++) {
				mRadioGroup2.get(i).setTag(i);
			}

			for(int i = 0 ; i < mRadioGroup3.size() ; i++) {
				mRadioGroup3.get(i).setTag(i);
			}

			for(int i = 0 ; i < mRadioGroup2_1.size() ; i++) {
				mRadioGroup2_1.get(i).setTag(i);
			}

			for(int i = 0 ; i < mPictureArr.size() ; i ++) {
				mPictureArr.get(i).setOnDeleteImageCallback(new onDeleteImageCallback() {
					@Override
					public void deleteImageCallback(int type) {
						if(type == 1) {
							getSelectImageCnt();	
						}
						else if(type == 2) {
							mSelectVideoPath = "";
							mSelectVideoThumbPath = "";
						}
						else if(type == 3) {
							mSelectAudioPath = "";
						}

					}
				});
			}

			for(int i = 0 ; i < mRadioGroup1.size() ; i++) {
				mRadioGroup1.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked) {
							RadioButton btn = (RadioButton) buttonView;
							int tag = (Integer) btn.getTag();
							mSelectGroup1 = tag;
							for(int i = 0 ; i < mRadioGroup1.size() ; i++) {
								if(tag == i) continue;
								else mRadioGroup1.get(i).setChecked(false);
							}

							if(tag == 0 || tag == 10) {
								for(int i = 0 ;i < mRadioGroup2.size(); i++) {
									mRadioGroup2.get(i).setEnabled(false);
								}
								for(int i = 0 ;i < mRadioGroup2_1.size(); i++) {
									mRadioGroup2_1.get(i).setEnabled(false);
								}
								
								mRadioGroup2.get(mRadioGroup2.size()-1).setChecked(true);
								mRadioGroup2_1.get(mRadioGroup2_1.size()-1).setChecked(true);
							}
							else {
								for(int i = 0 ;i < mRadioGroup2.size(); i++) {
									mRadioGroup2.get(i).setEnabled(true);
								}
								for(int i = 0 ;i < mRadioGroup2_1.size(); i++) {
									mRadioGroup2_1.get(i).setEnabled(true);
								}
							}
						}
					}
				});
			}

			for(int i = 0 ; i < mRadioGroup2.size() ; i++) {
				mRadioGroup2.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked) {
							RadioButton btn = (RadioButton) buttonView;
							int tag = (Integer) btn.getTag();
							mSelectGroup2 = tag;
							for(int i = 0 ; i < mRadioGroup2.size() ; i++) {
								if(tag == i) continue;
								else mRadioGroup2.get(i).setChecked(false);

							}
						}
					}
				});
			}

			for(int i = 0 ; i < mRadioGroup2_1.size() ; i++) {
				mRadioGroup2_1.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked) {
							RadioButton btn = (RadioButton) buttonView;
							int tag = (Integer) btn.getTag();
							mSelectGroup2_1 = tag;
							for(int i = 0 ; i < mRadioGroup2_1.size() ; i++) {
								if(tag == i) continue;
								else mRadioGroup2_1.get(i).setChecked(false);
							}
						}
					}
				});
			}

			for(int i = 0 ; i < mRadioGroup3.size() ; i++) {
				mRadioGroup3.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked) {
							RadioButton btn = (RadioButton) buttonView;
							int tag = (Integer) btn.getTag();
							mSelectGroup3 = tag;
							for(int i = 0 ; i < mRadioGroup3.size() ; i++) {
								if(tag == i) continue;
								else mRadioGroup3.get(i).setChecked(false);
							}
						}
					}
				});
			}

			mBackBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setResult(2);
					finish();

				}
			});


			mBoardContentAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mBoardContentEdit.getText().toString().equals("")) {
						BrUtilManager.getInstance().showToast(SC004.this, "내용을 입력해 주세요.");
					}
					else {

						if(mSelectImg.size() == 0 && mSelectAudioPath == null && mSelectVideoPath == null && mSelectVideoThumbPath == null) {
							requestBoardAdd();
						}
						else if(mSelectImg.size() == 0 && mSelectAudioPath.equals("") && mSelectVideoPath.equals("") && mSelectVideoThumbPath.equals("")) {
							requestBoardAdd();
						}
						else {
							uploadFiles();
						}
					}

				}
			});
			
			mCropAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if(MAX_SELECT_COUNT - mSelectImgCount <= 0) {
						BrUtilManager.getInstance().showToast(getBaseContext(), "더이상 선택 할 수 없습니다.");
						return;
					}
					
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					// 임시로 사용할 파일의 경로를 생성
					String url = "tmp_" + String.valueOf(System.currentTimeMillis())+ ".jpg";
					mCropImageCacheUri = Uri.fromFile(new File(myApp.getFileDir_Ex(), url));

					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,mCropImageCacheUri);
					intent.putExtra("return-data", true);
					intent.putExtra("passok", "ok");
					startActivityForResult(intent, CAMERA_CROP_REQUEST_CODE);
				}
			});

			mPictureAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(MAX_SELECT_COUNT - mSelectImgCount <= 0) {
						BrUtilManager.getInstance().showToast(getBaseContext(), "더이상 선택 할 수 없습니다.");
					}
					else {
						Intent intent = new Intent(SC004.this,CustomGallery.class);
						intent.putExtra("size", MAX_SELECT_COUNT - mSelectImgCount);//선택가능한갯수
						startActivityForResult(intent, GALLERY_REQUEST_CODE);	
					}
				}
			});

			mAudioAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if(!mSelectAudioPath.equals("")) {
						BrUtilManager.getInstance().showToast(getBaseContext(), "오디오 파일은 한개만 선택 할 수 있습니다.");
					}
					else {
						Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
						startActivityForResult(intent, ACTIVITY_RECORD_SOUND);
					}
					

					//				AudioView popup = new AudioView(SC004.this);
					//
					//				PopupWindow popupWindow = new PopupWindow(popup, android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
					//
					//				popupWindow.showAtLocation(popup, Gravity.NO_GRAVITY, 0,0);
					//				popup.setPopupWindow(popupWindow);
					//				popup.setOnGetFilePathListener(new getFilePathListener() {
					//					@Override
					//					public void getFilePathCallback(String path) {
					//						File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".mp4");
					//
					//						//							mSelectImg.clear();
					//						//							mSelectImg.add(path);
					//						//							mUploadFileType = 2;
					//						mSelectAudioPath = path;
					//						mImageLayout.setVisibility(View.VISIBLE);
					//						for(int j = 0 ; j < mPictureArr.size() ; j++) {
					//							if(!mPictureArr.get(j).isPictureYn()) {
					//								mPictureArr.get(j).setImage(R.drawable.write_add_record_bg,3);
					//								mPictureArr.get(j).setVisibility(View.VISIBLE);
					//								break;
					//							}
					//						}
					//						//							mPhotoView7.setImage(R.drawable.write_add_record_bg,3);
					//						hidePhotoView();
					//					}
					//				});
				}
			});

			mCaptureVideoAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!mSelectVideoPath.equals("")) {
						BrUtilManager.getInstance().showToast(getBaseContext(), "비디오 파일은 한개만 선택 할 수 있습니다.");
					}
					else {
						Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
						startActivityForResult(intent, CAPTURE_REQUEST_MOVIE);
					}
				}
			});

			mVideoAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!mSelectVideoPath.equals("")) {
						BrUtilManager.getInstance().showToast(getBaseContext(), "비디오 파일은 한개만 선택 할 수 있습니다.");
					}
					else {
						Intent i = new Intent(Intent.ACTION_PICK);
						i.setType("video/*");
						startActivityForResult(i, VIDEO_REQUEST_CODE);	
					}
				}
			});

			mModify = getIntent().getBooleanExtra("modify", false);



			if(mMemLvl == 1) {
				mGroup2BottomLayout.setVisibility(View.VISIBLE);
				mGroup2TopLayout.setVisibility(View.VISIBLE);
				
				mAudioAddBtn.setEnabled(false);
				mVideoAddBtn.setEnabled(false);
				mCaptureVideoAddBtn.setEnabled(false);
				
				mAudioAddBtn.setVisibility(View.GONE);
				mVideoAddBtn.setVisibility(View.GONE);
				mCaptureVideoAddBtn.setVisibility(View.GONE);
			}
			else {
				mGroup2BottomLayout.setVisibility(View.GONE);
				mGroup2TopLayout.setVisibility(View.GONE);
				mAudioAddBtn.setEnabled(true);
				mVideoAddBtn.setEnabled(true);
				mCaptureVideoAddBtn.setEnabled(true);
			}
			if(mModify == true) {
				int seq = getIntent().getIntExtra("seq", -1);
				if(seq > -1) 
					requestGetDetail(seq);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	
	} 

	private void toggleCheckable(ArrayList<RadioButton> arr, boolean flag) {
		for(int i = 0 ; i < arr.size() ; i++) {
			arr.get(i).setClickable(flag);
		}
	}



	public void uploadFiles(){

		try{
			AWSFileUploader2 uploader = new AWSFileUploader2(this);
//			uploader.setAwsAccesskey("AKIAJRVQWLQAFSWNG5XQ"); 
//			uploader.setAwsSecretKey("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");
//			uploader.setAWSBuckekName("brainyx1");//버킷이름
			uploader.setFolderName(myApp.getUserInfo().getUserId());
			int count = 0;
			for(int i = 0 ; i < mPictureArr.size() ; i ++) {
				if(mPictureArr.get(i).isPictureYn()) {
					if(mPictureArr.get(i).getmFileType() == 2) {
						count++;
					}
					count++;
				}
			}
			
			String[] files = new String[count];
			String[] filesPath = new String[count];
//			int imgCnt = 0;
			int pathCnt = 0;
			boolean videoYn = false;
			String videoPath = "";
			for(int i = 0 ;i < mPictureArr.size() ; i ++) {
				if(mPictureArr.get(i).isPictureYn()) {
					if(mPictureArr.get(i).getmFileType() == 1) {
//						files[pathCnt] = mSelectImg.get(imgCnt).substring(mSelectImg.get(imgCnt).lastIndexOf("/")+1, mSelectImg.get(imgCnt).length());
//						filesPath[pathCnt] = mSelectImg.get(imgCnt).substring(0, mSelectImg.get(imgCnt).lastIndexOf("/")+1);
						files[pathCnt] = mPictureArr.get(i).get_photoUrl().substring(mPictureArr.get(i).get_photoUrl().lastIndexOf("/")+1, mPictureArr.get(i).get_photoUrl().length());
						filesPath[pathCnt] =mPictureArr.get(i).get_photoUrl().substring(0,mPictureArr.get(i).get_photoUrl().lastIndexOf("/")+1);
//						imgCnt++;
						pathCnt++;
					}
					else if(mPictureArr.get(i).getmFileType() == 2) {
						String thumb = mPictureArr.get(i).getVideoThumbPath();
						videoPath = mPictureArr.get(i).getVideoPath();
						videoYn = true;
						files[pathCnt] = "videoThumb::"+thumb.substring(thumb.lastIndexOf("/")+1, thumb.length());
						filesPath[pathCnt] = thumb.substring(0,thumb.lastIndexOf("/")+1);
						pathCnt++;
					}
					else if(mPictureArr.get(i).getmFileType() == 3) {
						String photoUrl = mPictureArr.get(i).get_photoUrl();
						files[pathCnt] = "audio::"+photoUrl.substring(photoUrl.lastIndexOf("/")+1, photoUrl.length());
						filesPath[pathCnt] = photoUrl.substring(0,photoUrl.lastIndexOf("/")+1);
						pathCnt++;
					}
				}
			}
			
			if(videoYn) {
				files[files.length-1] =  "video::"+videoPath.substring(videoPath.lastIndexOf("/")+1, videoPath.length());
				filesPath[filesPath.length-1] = videoPath.substring(0,videoPath.lastIndexOf("/")+1);
			}
//			int length = 0;
//			length = mSelectImg.size();
//			if(mSelectAudioPath != null && !mSelectAudioPath.equals("")) length ++;
//			if(mSelectVideoPath != null && !mSelectVideoPath.equals("")) length ++;
//			if(mSelectVideoThumbPath != null && !mSelectVideoThumbPath.equals("")) length ++;
//			//
//			String[] files = new String[length];
//			String[] filesPath = new String[length];
//
//			for(int i = 0 ; i < mSelectImg.size(); i++) {
//				files[i] = mSelectImg.get(i).substring(mSelectImg.get(i).lastIndexOf("/")+1, mSelectImg.get(i).length());
//				filesPath[i] = mSelectImg.get(i).substring(0, mSelectImg.get(i).lastIndexOf("/")+1);
//			}
//
//			if(mSelectAudioPath != null && !mSelectAudioPath.equals("")) {
//				files[mSelectImg.size()-1] = "audio::"+mSelectAudioPath.substring(mSelectAudioPath.lastIndexOf("/")+1, mSelectAudioPath.length());
//				filesPath[mSelectImg.size()-1] = mSelectAudioPath.substring(0, mSelectAudioPath.lastIndexOf("/")+1);
//			}
//
//			if(mSelectVideoPath != null && !mSelectVideoPath.equals("")) {
//				files[mSelectImg.size()] = "video::"+mSelectVideoPath.substring(mSelectVideoPath.lastIndexOf("/")+1, mSelectVideoPath.length());
//				filesPath[mSelectImg.size()] = mSelectVideoPath.substring(0, mSelectVideoPath.lastIndexOf("/")+1);
//			}
//
//			if(mSelectVideoThumbPath != null&& !mSelectVideoThumbPath.equals("")) {
//				files[mSelectImg.size()+1] = "videoThumb::"+mSelectVideoThumbPath.substring(mSelectVideoThumbPath.lastIndexOf("/")+1, mSelectVideoThumbPath.length());
//				filesPath[mSelectImg.size()+1] = mSelectVideoThumbPath.substring(0, mSelectVideoThumbPath.lastIndexOf("/")+1);
//			}


			uploader.setFiles(files);
			uploader.setFilesPath(filesPath);

			//		uploader.setFilePath(Environment.getExternalStorageDirectory() + "/");//실제파일경로
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
					mSelectImg.clear();
					for(int i = 0 ; i < result.size() ; i ++) {
						if(result.get(i).indexOf("audio::") > -1) {
							mSelectAudioPath = result.get(i).replace("audio::", "");
						}
						else if(result.get(i).indexOf("video::") > -1) {
							mSelectVideoPath = result.get(i).replace("video::", "");
						}
						else if(result.get(i).indexOf("videoThumb::") > -1) {
							mSelectVideoThumbPath = result.get(i).replace("videoThumb::", "");
						}
						else {
							mSelectImg.add(result.get(i));
						}
					}
					//				mSelectImg.clear();
					//				mSelectImg = result;
					requestBoardAdd();
				}
			});
			uploader.execute();
		}
		catch(Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}


		//		Log.d("TAG", "s3파일패스.,.");
	}

	private void initCaptureVideo(final String path) {
		try {
			AsyncTask<Void, Void, String> loadImage = new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {
					mVideoThumbnail = ThumbnailUtils.createVideoThumbnail(path, Thumbnails.MINI_KIND);

					File testfile = new File(myApp.getFileDir_Ex() +"/tmp_"+ Common.getFileKey() + ".jpg");
					boolean flag = BrImageUtilManager.getInstance().saveOutput(SC004.this,mVideoThumbnail,getImageUri(testfile.getAbsolutePath()));
					//					mSelectImg.add(testfile.getAbsolutePath());
					//					mSelectImg.add(path);

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

					//
					//					File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".jpg");
					//					boolean flag = BrImageUtilManager.getInstance().saveOutput(SC004.this,mVideoThumbnail,getImageUri(testfile.getAbsolutePath()));
					//					//					mSelectImg.add(testfile.getAbsolutePath());
					//					//					mSelectImg.add(path);
					//					mSelectVideoThumbPath = testfile.getAbsolutePath();
					//					mSelectVideoPath = path; //썸네일::실제
					mSelectVideoThumbPath = result;
					mSelectVideoPath = path; //썸네일::실제
					hidePhotoView();
					mImageLayout.setVisibility(View.VISIBLE);

//					for(int j = 0 ; j < mPictureArr.size() ; j++) {
//						if(!mPictureArr.get(j).isPictureYn()) {
//							mPictureArr.get(5).setImage(mVideoThumbnail, 2);
					mPictureArr.get(5).setImage(R.drawable.write_add_movie_bg, 2);
							mPictureArr.get(5).setVisibility(View.VISIBLE);
							mPictureArr.get(5).setVideoPath(mSelectVideoPath);
							mPictureArr.get(5).setVideoThumbPath(mSelectVideoThumbPath);
//							break;
//						}
//					}


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
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}

	private void initSelectImage() {
		try {
			AsyncTask<Void, Void, Void> loadImage = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					for(int i = 0 ; i < mSelectImg.size() ; i ++) {
						loadImage(mSelectImg.get(i) , mPictureArr.get(i));
					}
					return null;
				}
				
				//패스워드는 영문 대/소문자, 숫자, 특수문자를 구분하여 6~2-자로 이루어집니다. 올바른 패스워드를 입력해주세요.

				@Override
				protected void onPreExecute() {
					mLoadingLayout.setVisibility(View.VISIBLE);
					super.onPreExecute();
				}

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);



					for(int i = 0 ;i < mPictureBitmapArr.size() ; i++) {

//						for(int j = 0 ; j < 5 ; j++) {
//							if(!mPictureArr.get(j).isPictureYn()) {
						DeleteImageView del = null;
						for(int j = 0 ; j < 5 ; j ++) {
							if(!mPictureArr.get(j).isPictureYn()) {
								del = mPictureArr.get(j);
								break;
							}
								
						}
						del.setImage(mPictureBitmapArr.get(i), 1, true);
						del.set_photoUrl(mSelectImg.get(i));
						del.setVisibility(View.VISIBLE);
//								break;
//							}
//						}
					}
					
					mImageLayout.setVisibility(View.VISIBLE);
					mLoadingLayout.setVisibility(View.GONE);
					getSelectImageCnt();
				}

				@Override
				protected void onProgressUpdate(Void... values) {
					super.onProgressUpdate(values);
				}
			};

			loadImage.execute();
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}

	private void hidePhotoView() {
		for(int i = 0 ; i < mPictureArr.size() ; i ++) {
			if(mPictureArr.get(i).isPictureYn()) {
				mPictureArr.get(i).setVisibility(View.VISIBLE);
			}
			else {
				mPictureArr.get(i).setVisibility(View.GONE);
			}
		}
	}

	private void getSelectImageCnt() {
		mSelectImgCount = 0;
		for(int i = 0 ; i < mPictureArr.size() ; i ++) {
			if(mPictureArr.get(i).isPictureYn()) {
				if(mPictureArr.get(i).getmFileType() == 1)
					mSelectImgCount++;
			}
		}

		if(mSelectImgCount ==0) {
			mImageLayout.setVisibility(View.GONE);
			mUploadFileType = -1;
		}
		else {
			mImageLayout.setVisibility(View.VISIBLE);
			hidePhotoView();
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

//			imageLayout.set_photoUrl(path);
			mPictureBitmapArr.add(resize);	
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
		
		//		myApp.imageloder.displayImage(path, imageLayout.getImageView(), myApp.options);
	}

	private void requestGetDetail(int seq) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("seq", seq+"");
			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_DETAIL, params, JSONObject.class, this, "boardDetailCallBack");	
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
		
		
	}

	public void boardDetailCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {

		try {
			if(!jsonObject.isNull("data")) {
				JSONObject item = jsonObject.getJSONObject("data");
				Log.d("JSON", item.toString());

				String contents = item.getString("contents");
				String birthDay = item.getString("birthDay");
				mUnknown = item.getInt("unknown");
				int seriousLevel = item.getInt("seriousLevel");
				int seriousLevel2 = item.getInt("seriousLevel2");
				mSeq = item.getInt("seq");
				//			int deleted = item.getInt("deleted");
				int kind = item.getInt("kind");
				int religion = item.getInt("religion");

				seriousLevel--;
				seriousLevel2--;
				String writeTime = item.getString("writeTime");
				int memLvl = item.getInt("memLvl");
				String nickName = item.getString("nickName");
				int repotCnt = item.getInt("reportCnt");
				String userId = item.getString("userId");
				//			int sex = item.getInt("sex");
				int likeCnt = item.getInt("likeCnt");
				int replyCnt = item.getInt("replyCnt");
				String photoName1 = item.getString("photoName1");
				String photoName2 = item.getString("photoName2");
				String photoName3 = item.getString("photoName3");
				String photoName4 = item.getString("photoName4");
				String photoName5 = item.getString("photoName5");
				String movName = item.getString("movName");
				String audioName = item.getString("audioName");
				mTempName = item.getString("tempName");

				
				boolean movYn = false;
				ArrayList<String> photoArr = new ArrayList<String>();
				if(photoName1.length() > 10) photoArr.add(photoName1);
				if(photoName2.length() > 10) photoArr.add(photoName2);
				if(photoName3.length() > 10) photoArr.add(photoName3);
				if(photoName4.length() > 10) photoArr.add(photoName4);
				if(photoName5.length() > 10) photoArr.add(photoName5);
				if(movName.length() > 10) {
					movYn = true;
				}

				mRadioGroup1.get((kind-1)).setChecked(true);
				mRadioGroup3.get((religion)).setChecked(true);
				mRadioGroup2.get((seriousLevel)).setChecked(true);
				mRadioGroup2_1.get((seriousLevel2)).setChecked(true);

				if(mModify == true) {
					toggleCheckable(mRadioGroup1, false);
					toggleCheckable(mRadioGroup2, false);
					toggleCheckable(mRadioGroup3, false);
					toggleCheckable(mRadioGroup2_1, false);
				}

				mBoardContentEdit.setText(contents);
				mBoardContentEdit.setSelection(mBoardContentEdit.getText().length());
				if(mUnknown == 1) {
					mUnknownCheckbox.setChecked(true);
				}
				else {
					mUnknownCheckbox.setChecked(false);
				}
				mUnknownCheckbox.setClickable(false);
				mAddPhotoLayout.setVisibility(View.GONE);
				mAddLayout.setVisibility(View.GONE);
				mModifyLayout.setVisibility(View.VISIBLE);
				mModifyBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						requestBoardEdit();
					}
				});

				mImageLayout.setVisibility(View.VISIBLE);
				for(int i = 0 ; i < photoArr.size() ; i ++) {
					mPictureArr.get(i).setPictureYn(true);
					mPictureArr.get(i).setDeleteYn(true);
					myApp.imageloder.displayImage(photoArr.get(i), mPictureArr.get(i).getImageView());
				}
				
				if(movYn) {
					mPictureArr.get(5).setPictureYn(true);
					mPictureArr.get(5).setDeleteYn(true);
					mPictureArr.get(5).setImage(R.drawable.write_add_movie_bg,2);
					mPictureArr.get(5).setVisibility(View.VISIBLE);
				}
				
				if(audioName.length() > 5) {
					mPictureArr.get(6).setPictureYn(true);
					mPictureArr.get(6).setDeleteYn(true);
					mPictureArr.get(6).setImage(R.drawable.write_add_record_bg,3);
					mPictureArr.get(6).setVisibility(View.VISIBLE);
				}
				for(int i = 0 ; i < mPictureArr.size(); i ++) {
					if(!mPictureArr.get(i).isPictureYn()) {
						mPictureArr.get(i).setVisibility(View.GONE);
					}
				}
			}
			else {
				BrUtilManager.getInstance().showToast(this, "데이터를 가져오지 못했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
		
		//		mParentLayout.setVisibility(View.VISIBLE);
	}

	private void requestBoardEdit() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("contents", mBoardContentEdit.getText().toString());
			params.put("seq", mSeq);
			
			if(mUnknown == 1) {
				params.put("unknown", 1);
				if(mTempName.equals("")) {
					params.put("tempName", myApp.getTempResource());
				}
				else {
					params.put("tempName", mTempName);
				}
			}
			else {
				params.put("unknown", 0);
				params.put("tempName", "");
			}
			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_EDIT, params, JSONObject.class, this, "boardEditCallBack");	
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
		
	}

	public void boardEditCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				BrUtilManager.getInstance().showToast(getBaseContext(), message);
				setResult(1);
				finish();
			}
			else {
				BrUtilManager.getInstance().showToast(getBaseContext(), message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
		
	}


	private void requestBoardAdd() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			String id = myApp.getUserInfo().getUserId();
			if(mUnknownCheckbox.isChecked()) {
				params.put("userId",id);
				params.put("tempName", myApp.getTempResource());
			}
			else {
				params.put("userId", id);
				params.put("tempName", "");
			}
			//		params.put("userId", "iss72002");
			//		params.put("tempName", "");
			params.put("kind", (mSelectGroup1+1));
			//		if(mSelectGroup1 == 11 || (mSelectGroup2+1) + (mSelectGroup2_1+1) == 10) {
			//			params.put("seriousLevel", 10);
			//		}
			//		else {
			//			params.put("seriousLevel",(mSelectGroup2+1) + (mSelectGroup2_1+1));	
			//		}
			params.put("seriousLevel", (mSelectGroup2+1));
			params.put("seriousLevel2", (mSelectGroup2_1+1));
			params.put("unknown" ,mUnknownCheckbox.isChecked() == true ? 1 : 0);
			params.put("religion", (mSelectGroup3));
			params.put("contents", mBoardContentEdit.getText().toString());

			int count = 0;
			for(int i = 0; i < mSelectImg.size(); i ++) {
				count++;
				params.put("photoName"+(i+1), mSelectImg.get(i));
			}
			for(int i =count+1; i < 5; i++) {
				params.put("photoName"+(i+1), "");
			}

			params.put("audioName", mSelectAudioPath == null ? "" : mSelectAudioPath);
			if(mSelectVideoPath == null) mSelectVideoPath = "";
			if(mSelectVideoThumbPath == null) mSelectVideoThumbPath = "";
			params.put("movName", mSelectVideoPath+"::"+mSelectVideoThumbPath);

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_BOARD_ADD, params, JSONObject.class, this, "boardAddCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
		
	}

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	public void boardAddCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
		try {
			boolean result = jsonObject.getBoolean("result");
			String message = jsonObject.getString("message");
			if(result) {
				BrUtilManager.getInstance().showToast(getBaseContext(), message);
				setResult(1);
				finish();
			}
			else {
				BrUtilManager.getInstance().showToast(getBaseContext(), message);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
		
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		try {
			if(resultCode != RESULT_OK){
				if(resultCode == RESULT_FIRST_USER){
					//					doTakePhotoAction();
				}else{
					//					filetype = 0;
					//					return;	 
				}
			}else{
				if(requestCode == GALLERY_REQUEST_CODE){
					mUploadFileType = 1;
										mSelectImg.clear();
										mPictureBitmapArr.clear();
					mSelectImg = data.getStringArrayListExtra("simg");
					//					Log.d("TAG", mSelectImg.get(0));
					//					Log.d("TAG", mSelectImg.get(0).substring(0, mSelectImg.get(0).lastIndexOf("/")+1));
					//					Log.d("TAG", mSelectImg.get(0).substring(mSelectImg.get(0).lastIndexOf("/")+1, mSelectImg.get(0).length()));
					//					Log.d("TAG",Environment.getExternalStorageDirectory().toString() + "/");
					//					mImageLayout.setVisibility(View.VISIBLE);
					initSelectImage();
					//					for(int i = 0 ; i < mSelectImg.size() ; i ++) {
					//						loadImage(mSelectImg.get(i) , mPictureArr.get(i));
					//					}
				}
				else if(requestCode == CAMERA_CROP_REQUEST_CODE) {
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
				else if(requestCode == CAMERA_CROP_FROM_REQUEST_CODE) {
					mUploadFileType = 1;
//					File outFilePath = new File(myApp.getFileDir_Ex() + "/tmp_"+ System.currentTimeMillis() + ".jpg");
//
//					mCropFromImageUri = Uri.fromFile(outFilePath);

					if (mCropFromImageUri != null) {
						//						mSelectImg.clear();
						//						mPictureBitmapArr.clear();
						Bitmap photo = null;
						mCropImagePath = mCropFromImageUri.getPath();
						try {
							photo = Images.Media.getBitmap(getContentResolver(),mCropFromImageUri);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						mSelectImg.clear();
						mPictureBitmapArr.clear();
						ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
						Bitmap curThumb = photo;
						curThumb.compress(CompressFormat.JPEG, 70, byteArr);
						mSelectImg.add(mCropImagePath);
						//						mImageLayout.setVisibility(View.VISIBLE);
						initSelectImage();
					}
				}
				else if(requestCode == VIDEO_REQUEST_CODE) {
					//					mSelectImg.clear();
					Uri uri = data.getData();
					String path = getPath(uri);
					mUploadFileType = 3;
					mImageLayout.setVisibility(View.VISIBLE);
					//					
					//					File videoFile = new File(path);
					//					
					mVideoThumbnail = ThumbnailUtils.createVideoThumbnail(path, Thumbnails.MINI_KIND);
					//					else {
					File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".jpg");
					boolean flag = BrImageUtilManager.getInstance().saveOutput(this,mVideoThumbnail,getImageUri(testfile.getAbsolutePath()));
					//					mSelectImg.add(testfile.getAbsolutePath());
					//					mSelectImg.add(path);
					mSelectVideoThumbPath = testfile.getAbsolutePath();
					mSelectVideoPath = path; //썸네일::실제

//					for(int j = 0 ; j < mPictureArr.size() ; j++) {
//						if(!mPictureArr.get(5).isPictureYn()) {

					
//							mPictureArr.get(5).setImage(mVideoThumbnail, 2);
					mPictureArr.get(5).setImage(R.drawable.write_add_movie_bg, 2);
							mPictureArr.get(5).setVisibility(View.VISIBLE);
							mPictureArr.get(5).setVideoPath(mSelectVideoPath);
							mPictureArr.get(5).setVideoThumbPath(mSelectVideoThumbPath);
//							break;
//						}
//					}
							hidePhotoView();
					//					mPictureArr.get(5).setImage(mVideoThumbnail,2);
//					getSelectImageCnt();
					//					}
				}
				else if(requestCode == ACTIVITY_RECORD_SOUND) {
					Uri audioUri = data.getData();
					String path = getPath(audioUri);

					File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".mp4");

					//					mSelectImg.clear();
					//					mSelectImg.add(path);
					mUploadFileType = 2;
					mSelectAudioPath = path;
					mImageLayout.setVisibility(View.VISIBLE);
//					for(int j = 0 ; j < mPictureArr.size() ; j++) {
//						if(!mPictureArr.get(j).isPictureYn()) {
							mPictureArr.get(6).setImage(R.drawable.write_add_record_bg,3);
							mPictureArr.get(6).set_photoUrl(mSelectAudioPath);
							mPictureArr.get(6).setVisibility(View.VISIBLE);
							
//							break;
//						}
//					}
					hidePhotoView();
				}
				else if (requestCode == CAPTURE_REQUEST_MOVIE)
				{
					Uri uri = data.getData();
					String path = getPath(uri);
					mUploadFileType = 3;
					mImageLayout.setVisibility(View.VISIBLE);
					hidePhotoView();
					initCaptureVideo(path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
			//			linearLayout_loading.setVisibility(View.GONE);
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
}
