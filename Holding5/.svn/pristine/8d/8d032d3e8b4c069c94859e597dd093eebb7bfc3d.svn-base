package com.br.holding5.sc008;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.ms.AudioView;
import com.br.holding5.ms.AudioView.getFilePathListener;
import com.br.holding5.ms.Common;
import com.br.holding5.ms.MSUtil;
import com.br.holding5.sc003.AWSFileUploader2;
import com.br.holding5.sc003.gallery.CustomGallery;
import com.br.holding5.sc003.gallery.DeleteImageView;
import com.br.holding5.sc004.SC004;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.image.BrImageUtilManager;

public class SC008_S11117 extends BaseActivity{

	Context context;
	ImageView imageView_back;
	RadioButton radio_group_1;
	RadioButton radio_group_2;
	RadioButton radio_group_3;
	RadioButton radio_group_4;
	RadioButton radio_group_5;
	RadioButton radio_group_6;
	RadioButton radio_group_7;
	RadioButton radio_group_8;
	RadioButton radio_group_9;
	RadioButton radio_group_10;
	RadioButton radio_group2_1;
	RadioButton radio_group2_2;
	RadioButton radio_group2_3;
	RadioButton radio_group2_4;
	RadioButton radio_group2_5;
	EditText editText_contents;
	private ImageButton mCropAddBtn;
	private ImageButton mPictureAddBtn;
	private ImageButton mAudioAddBtn;
	private ImageButton mVideoAddBtn;
	private ImageButton mBoardContentAddBtn;
	private ImageButton mCaptureVideoAddBtn;
	private LinearLayout mLoadingLayout;
	Button button_reg;
	LinearLayout mImageLayout;


	ArrayList<RadioButton> groupRadioButton1 = new ArrayList<RadioButton>();
	ArrayList<RadioButton> groupRadioButton2 = new ArrayList<RadioButton>();
	int groupRadioButton1_num = 1;
	int groupRadioButton2_num = 1;

	private final int ACTIVITY_RECORD_SOUND = 1;
	private final int GALLERY_REQUEST_CODE = 1000;
	private final int CAMERA_CROP_REQUEST_CODE = 1001;
	private final int CAMERA_CROP_FROM_REQUEST_CODE = 1002;
	private final int VIDEO_REQUEST_CODE = 1003;
	private final int CAPTURE_REQUEST_MOVIE = 1004;
	private final int MAX_SELECT_COUNT = 5;
	private int mSelectImgCount = 0;
	private Uri mCropImageCacheUri;
	private String mSelectAudioPath = null;
	private String mSelectVideoPath = null;
	private String mSelectVideoThumbPath = null;
	private ArrayList<DeleteImageView> mPictureArr = new ArrayList<DeleteImageView>();
	private ArrayList<String> mSelectImg = new ArrayList<String>();
	private int mUploadFileType = -1;
	private CheckBox 	mUnknownCheckbox;
	private ArrayList<Bitmap> mPictureBitmapArr = new ArrayList<Bitmap>();
	private Uri mCropFromImageUri;
	private String mCropImagePath;
	private Bitmap mVideoThumbnail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s11117);

			init();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}



	private void init() {
		try {
			context = this;
			imageView_back = (ImageView)findViewById(R.id.imageView_back);
			radio_group_1 = (RadioButton)findViewById(R.id.radio_group_1);
			radio_group_2 = (RadioButton)findViewById(R.id.radio_group_2);
			radio_group_3 = (RadioButton)findViewById(R.id.radio_group_3);
			radio_group_4 = (RadioButton)findViewById(R.id.radio_group_4);
			radio_group_5 = (RadioButton)findViewById(R.id.radio_group_5);
			radio_group_6 = (RadioButton)findViewById(R.id.radio_group_6);
			radio_group_7 = (RadioButton)findViewById(R.id.radio_group_7);
			radio_group_8 = (RadioButton)findViewById(R.id.radio_group_8);
			radio_group_9 = (RadioButton)findViewById(R.id.radio_group_9);
			radio_group_10 = (RadioButton)findViewById(R.id.radio_group_10);
			radio_group2_1 = (RadioButton)findViewById(R.id.radio_group2_1);
			radio_group2_2 = (RadioButton)findViewById(R.id.radio_group2_2);
			radio_group2_3 = (RadioButton)findViewById(R.id.radio_group2_3);
			radio_group2_4 = (RadioButton)findViewById(R.id.radio_group2_4);
			radio_group2_5 = (RadioButton)findViewById(R.id.radio_group2_5);
			editText_contents = (EditText)findViewById(R.id.editText_contents);
			mLoadingLayout = (LinearLayout) findViewById(R.id.linearLayout_loading);
			
			mCropAddBtn = (ImageButton) findViewById(R.id.crop_add_picture_btn);
			mPictureAddBtn = (ImageButton) findViewById(R.id.add_picture_btn);
			mAudioAddBtn = (ImageButton) findViewById(R.id.add_audio_btn);
			mVideoAddBtn = (ImageButton) findViewById(R.id.add_video_btn);
			mBoardContentAddBtn = (ImageButton) findViewById(R.id.board_content_add_btn);
			mCaptureVideoAddBtn = (ImageButton) findViewById(R.id.capture_add_video_btn);
			
			
			button_reg = (Button)findViewById(R.id.button_reg);
			mImageLayout = (LinearLayout) findViewById(R.id.add_picture_layout);

			groupRadioButton1.add(radio_group_1);
			groupRadioButton1.add(radio_group_2);
			groupRadioButton1.add(radio_group_3);
			groupRadioButton1.add(radio_group_4);
			groupRadioButton1.add(radio_group_5);
			groupRadioButton1.add(radio_group_6);
			groupRadioButton1.add(radio_group_7);
			groupRadioButton1.add(radio_group_8);
			groupRadioButton1.add(radio_group_9);
			groupRadioButton1.add(radio_group_10);

			groupRadioButton2.add(radio_group2_1);
			groupRadioButton2.add(radio_group2_2);
			groupRadioButton2.add(radio_group2_3);
			groupRadioButton2.add(radio_group2_4);
			groupRadioButton2.add(radio_group2_5);

			radio_group_1.setChecked(true);
			radio_group2_1.setChecked(true);

			for(int i = 0; i < groupRadioButton1.size(); i++){
				groupRadioButton1.get(i).setTag(i);
			}

			for(int i = 0; i < groupRadioButton2.size(); i++){
				groupRadioButton2.get(i).setTag(i);
			}

			for(int i = 0; i < groupRadioButton1.size(); i++ ){
				groupRadioButton1.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked){
							RadioButton rb = (RadioButton) buttonView;
							groupRadioButton1_num = (Integer)rb.getTag();

							for (int j = 0; j < groupRadioButton1.size(); j++) {

								if(groupRadioButton1_num == j){
									continue;
								}else{
									groupRadioButton1.get(j).setChecked(false);
								}
							}
						}
					}
				});
			}

			for(int i = 0; i < groupRadioButton2.size(); i++ ){
				groupRadioButton2.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked){
							RadioButton rb = (RadioButton) buttonView;
							groupRadioButton2_num = (Integer)rb.getTag();

							for (int j = 0; j < groupRadioButton2.size(); j++) {

								if(groupRadioButton2_num == j){
									continue;
								}else{
									groupRadioButton2.get(j).setChecked(false);
								}
							}
						}
					}
				});
			}





			//listener
			//////////////////////////////////////////////////////////////////////////////

			imageView_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
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
						Intent intent = new Intent(SC008_S11117.this,CustomGallery.class);
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
						return;
					}
					
					Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
					startActivityForResult(intent, ACTIVITY_RECORD_SOUND);

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
						return;
					}
					
					Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
					startActivityForResult(intent, CAPTURE_REQUEST_MOVIE);
				}
			});

			mVideoAddBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if(!mSelectVideoPath.equals("")) {
						BrUtilManager.getInstance().showToast(getBaseContext(), "비디오 파일은 한개만 선택 할 수 있습니다.");
						return;
					}
					
					Intent i = new Intent(Intent.ACTION_PICK);
					i.setType("video/*");
					startActivityForResult(i, VIDEO_REQUEST_CODE);
				}
			});

			button_reg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(editText_contents.getText().toString().equals("")) {
						BrUtilManager.getInstance().showToast(context, "내용을 입력해 주세요.");
					}
					else {

						if(mSelectImg.size() == 0 && mSelectAudioPath == null && mSelectVideoPath == null && mSelectVideoThumbPath == null) {
							requestBoardAdd();
						}
						else {
							uploadFiles();
						}
					}

				}
			});
		}
		catch(Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	




	private void requestBoardAdd() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			String userId = myApp.getUserInfo().getUserId();
			if(mUnknownCheckbox.isChecked()) {
				params.put("userId", userId);
				params.put("tempName", myApp.getTempResource());
			}
			else {
				params.put("userId", userId);
				params.put("tempName", "");
			}
			//		params.put("userId", "iss72002");
			//		params.put("tempName", "");
			params.put("kind", groupRadioButton1_num);
			params.put("seriousLevel", groupRadioButton1_num);

			params.put("unknown" ,mUnknownCheckbox.isChecked() == true ? 1 : 0);
			params.put("contents", editText_contents.getText().toString());

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


			//		if(mUploadFileType == 1) {
			//			int count = 0;
			//			for(int i = 0; i < mSelectImg.size(); i ++) {
			//				count++;
			//				params.put("photoName"+(i+1), mSelectImg.get(i));
			//			}
			//
			//			if(5-count == 0) {
			//				params.put("audioName", "");
			//				params.put("movName", "");
			//			}
			//			else {
			//				for(int i = 5-count ; i < 5; i++) {
			//					params.put("photoName"+(i+1), "");
			//				}
			//			}
			//		}
			//		else if(mUploadFileType == 2) {
			//			params.put("photoName1", "");
			//			params.put("photoName2", "");
			//			params.put("photoName3", "");
			//			params.put("photoName4", "");
			//			params.put("photoName5", "");
			//			params.put("audioName", mSelectImg.get(0));
			//			params.put("movName", "");
			//
			//		}
			//		else if(mUploadFileType == 3) {
			//			params.put("photoName1", mSelectImg.get(0));
			//			params.put("photoName2", "");
			//			params.put("photoName3", "");
			//			params.put("photoName4", "");
			//			params.put("photoName5", "");
			//			params.put("audioName", "");
			//			params.put("movName", mSelectImg.get(1));
			//
			//		}

			AQuery aquery = new AQuery(this);

			Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			//		dialog.show();	

			aquery.progress(dialog).ajax(Global.BOARD_BOARD_ADD, params, JSONObject.class, this, "boardAddCallBack");
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	public void boardAddCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {
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
			WriteFileLog.writeException(e);
		}

	}
	private void initCaptureVideo(final String path) {
		try {
			AsyncTask<Void, Void, String> loadImage = new AsyncTask<Void, Void, String>() {

				@Override
				protected String doInBackground(Void... params) {
					mVideoThumbnail = ThumbnailUtils.createVideoThumbnail(path, Thumbnails.MINI_KIND);

					File testfile = new File(myApp.getFileDir_Ex() +"/tmp_"+ Common.getFileKey() + ".jpg");
					boolean flag = BrImageUtilManager.getInstance().saveOutput(SC008_S11117.this,mVideoThumbnail,getImageUri(testfile.getAbsolutePath()));
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
					mImageLayout.setVisibility(View.VISIBLE);

//					for(int j = 0 ; j < mPictureArr.size() ; j++) {
//						if(!mPictureArr.get(j).isPictureYn()) {
							mPictureArr.get(5).setImage(mVideoThumbnail, 2, false);
							mPictureArr.get(5).setVisibility(View.VISIBLE);
							mPictureArr.get(5).setVideoPath(mSelectVideoPath);
							mPictureArr.get(5).setVideoThumbPath(mSelectVideoThumbPath);
//							break;
//						}
//					}


					mLoadingLayout.setVisibility(View.GONE);
					hidePhotoView();
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
			WriteFileLog.writeException(e);
		}
	}

	private void hidePhotoView() {
		for(int i = 0 ; i < mPictureArr.size() ; i ++) {
			if(mPictureArr.get(i).isPictureYn()) {
				mPictureArr.get(i).setVisibility(View.VISIBLE);
			}
			else {
				mPictureArr.get(i).setVisibility(View.INVISIBLE);
			}
		}
	}

	private void getSelectImageCnt() {
		mSelectImgCount = 0;
		for(int i = 0 ; i < mPictureArr.size() ; i ++) {
			if(mPictureArr.get(i).isPictureYn()) {
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
						
						
						mImageLayout.setVisibility(View.VISIBLE);
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

					mImageLayout.setVisibility(View.VISIBLE);
							mPictureArr.get(5).setImage(mVideoThumbnail, 2, true);
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
			WriteFileLog.writeException(e);
			//			linearLayout_loading.setVisibility(View.GONE);
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
				int unknown = item.getInt("unknown");
				int seriousLevel = item.getInt("seriousLevel");
				//			int deleted = item.getInt("deleted");
				int kind = item.getInt("kind");
				int religion = item.getInt("religion");
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

				groupRadioButton1.get((kind-1)).setChecked(true);
				groupRadioButton2.get((religion)).setChecked(true);
				editText_contents.setText(contents);

			}
			else {
				BrUtilManager.getInstance().showToast(this, "데이터를 가져오지 못했습니다.");
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		//		mParentLayout.setVisibility(View.VISIBLE);
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

