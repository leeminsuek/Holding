package com.br.holding5.sc014;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.ms.Common;
import com.br.holding5.ms.MSUtil;
import com.br.holding5.ms.ResizeImageView;
import com.br.holding5.sc003.AWSFileUploader2;
import com.br.holding5.sc003.S00113;
import com.br.holding5.sc003.gallery.CustomGallery2;
import com.br.holding5.sc003.gallery.DeleteImageView;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData;
import com.brainyx.holding5.R;
import com.brainyxlib.BrDateManager;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.SharedManager;
import com.brainyxlib.image.BrImageUtilManager;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class SC00115 extends BaseActivity implements OnClickListener{

	ImageView profileimg;
	ImageView profileMask;
	MemberVO member = new MemberVO();
	EditText edittext4,edittext5;
	TextView edittext1,edittext2,edittext3;
	private TextView mHeaderTitleTxt;
	private int[] modifybtn = {R.id.modifybtn1,R.id.modifybtn2,R.id.modifybtn3,R.id.modifybtn4,R.id.modifybtn5};
	private int mPage = 0;
	ListView listview;
	ArrayList<Object>array = new ArrayList<Object>();
	//ArrayList<BoardVo> arraylist = new ArrayList<BoardVo>();
	//ArrayList<ReplyVo> arraylist2 = new ArrayList<ReplyVo>();
	private String mHeaderTitle="";
	AListAdapter adapter;
	AListAdapter2 adapter2;
//	ScrollView scrollView;
	View footerview;
	boolean hasmoredata = false; 
	boolean isdataloading = false;
	ImageButton morebtn;
	boolean listflag = true;
	String birthday  = "";
	LinearLayout headerView;
	private String userId = "";
	private boolean userYn = false;
	private final int BOARD_SEQ = 1002;
	private final int GALLERY_CODE = 1005;
	private final int CROP_CAMERA_CODE = 1006;
	private final int PICTURE_CAMERA_CODE = 1004;
	private Uri mImageCaptureUri;
	private Uri m_ImageUri;
	private File mNewProfile;
	private Bitmap mPhoto;
	private String mProfileImgPath = "";
	private ArrayList<String> mSelectImg = new ArrayList<String>();
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if(requestCode == GALLERY_CODE) {
				if(resultCode == RESULT_FIRST_USER) {
					doTakePhotoAction();
				}
				else if(resultCode == RESULT_OK) { 
					mSelectImg = data.getStringArrayListExtra("simg");
					UploadProfile();
//					if(mSelectImg.size() > 0 ) {
//						initSelectImage(mSelectImg.get(0));	
//					}
				}
				else if(resultCode == RESULT_CANCELED) {
					return;
				}
			}
			else if(requestCode == PICTURE_CAMERA_CODE) {
				if(resultCode == RESULT_OK) {
					File outFilePath = new File(myApp.getFileDir_Ex() + "/tmp_"+ System.currentTimeMillis() + ".jpg");
					m_ImageUri = Uri.fromFile(outFilePath);
					Intent intent = new Intent("com.android.camera.action.CROP");
					intent.setDataAndType(mImageCaptureUri, "image/*");
					intent.putExtra("outputX", 200); // crop한 이미지의 x축 크기
					intent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
					intent.putExtra("aspectX", 2); // crop 박스의 x축 비율 
					intent.putExtra("aspectY", 2); // crop 박스의 y축 비율
					intent.putExtra("scale", true);
					intent.putExtra("outputFormat", "JPEG");
					intent.putExtra("output", m_ImageUri); // 파일로 저장
					try {
						startActivityForResult(intent, CROP_CAMERA_CODE);
					} catch (Exception e) {
						e.getMessage();
					}
				}
			}
			else if(requestCode == CROP_CAMERA_CODE) {
				if(resultCode == RESULT_OK) {
					if (m_ImageUri != null) {
//						try {
							
							mProfileImgPath = m_ImageUri.getPath();
							UploadProfile();
//							AsyncTask<Void, Void, Bitmap> loadImage = new AsyncTask<Void, Void, Bitmap>() {
//
//								@Override
//								protected Bitmap doInBackground(Void... params) {
//									try {
//										mPhoto = Images.Media.getBitmap(getContentResolver(),m_ImageUri);
//									} catch (FileNotFoundException e) {
//										WriteFileLog.writeException(e);
//									} catch (IOException e) {
//										WriteFileLog.writeException(e);
//									}
//									ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
//									Bitmap curThumb = mPhoto;
//									curThumb.compress(CompressFormat.JPEG, 70, byteArr);
//									return null;
//								}
//
//								@Override
//								protected void onPreExecute() {
//									super.onPreExecute();
//								}
//
//								@Override
//								protected void onPostExecute(Bitmap result) {
//									super.onPostExecute(result);
//
//									if (mProfileImgPath.startsWith("http")) {
////										Common.profileBitmap = getImageFromURL(resultUri);
//									}else {
//										
//										Bitmap resize = BrImageUtilManager.getInstance().getBitmap(mProfileImgPath, true,BrUtilManager.getInstance().getScreenWidth(getBaseContext()),BrUtilManager.getInstance().getScreenHeight(getBaseContext()));
//
//										File testfile = new File(myApp.getFileDir_Ex() + Common.getFileKey() + ".jpg");
//										boolean flag = BrImageUtilManager.getInstance().saveOutput(getBaseContext(),resize,getImageUri(testfile.getAbsolutePath()));
//										mNewProfile = testfile;
//										mSelectImg.clear();
//										mSelectImg.add(mProfileImgPath);
//										profileimg.setImageBitmap(resize);
//									}
//								}
//
//								@Override
//								protected void onProgressUpdate(Void... values) {
//									super.onProgressUpdate(values);
//								}
//							};
//
//							loadImage.execute();
//						} catch (Exception e) {
//
//						}
//						Bitmap photo = null;
//						String resultUri = m_ImageUri.getPath();
//						try {
//							photo = Images.Media.getBitmap(getContentResolver(),m_ImageUri);
//						} catch (FileNotFoundException e) {
//							WriteFileLog.writeException(e);
//						} catch (IOException e) {
//							WriteFileLog.writeException(e);
//						}
//						ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
//						Bitmap curThumb = photo;
//						curThumb.compress(CompressFormat.JPEG, 70, byteArr);
					}
				}
			}
			else if(requestCode == 1001) {
				if(RESULT_OK == resultCode){
					String pass = data.getStringExtra("passwd");
					member.setPswd(pass);
					
					SharedManager.getInstance().setString(getBaseContext(), Global.Shared_UserPwd, pass);
				}
			}
			else if(requestCode == BOARD_SEQ) {
				
				if(resultCode == 1) {
					array.clear();
					getdata();
				}
			}else{
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}
	
	private void UploadProfile() {
			try{
				AWSFileUploader2 uploader = new AWSFileUploader2(this);
//				uploader.setAwsAccesskey("AKIAJRVQWLQAFSWNG5XQ"); 
//				uploader.setAwsSecretKey("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");
//				uploader.setAWSBuckekName("brainyx1");//버킷이름
				uploader.setFolderName(myApp.getUserInfo().getUserId());
				String[] files = new String[1];
				String[] filesPath = new String[1];
				
				files[0] = mSelectImg.get(0).substring(mSelectImg.get(0).lastIndexOf("/")+1, mSelectImg.get(0).length());
				filesPath[0] = mSelectImg.get(0).substring(0,mSelectImg.get(0).lastIndexOf("/")+1);
//					files[files.length-1] =  "video::"+videoPath.substring(videoPath.lastIndexOf("/")+1, videoPath.length());
//					filesPath[filesPath.length-1] = videoPath.substring(0,videoPath.lastIndexOf("/")+1);

				uploader.setFiles(files);
				uploader.setFilesPath(filesPath);

				uploader.setListener(new AWSFileUploader2.OnResultListener() { // 업로드완료후 콜백
					@Override
					public boolean setOnResultListener(boolean result) {
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
						try {
							mSelectImg.clear();
							mSelectImg = result;
							member.setHappyPhoto(mSelectImg.get(0));
							UploadProfileImage();	
						} catch (Exception e) {
							e.printStackTrace();
						}
						
				
//						myApp.imageloder.displayImage(result.get(0), profileimg);
					}
				});
				uploader.execute();
			}
			catch(Exception e) {
				e.printStackTrace();
				WriteFileLog.writeException(e);
			}
	}
	
	
	private void initSelectImage(final String path) {
		try {
			AsyncTask<String, Void, Bitmap> loadImage = new AsyncTask<String,Void, Bitmap>() {

				@Override
				protected Bitmap doInBackground(String... params) {
					
					Bitmap resize = BrImageUtilManager.getInstance().getBitmap(path, true,800,600);

					File testfile = new File(myApp.getFileDir_Ex() + MSUtil.getFileKey() + ".jpg");
					boolean flag = BrImageUtilManager.getInstance().saveOutput(getBaseContext(),resize,getImageUri(testfile.getAbsolutePath()));

					return resize;
				}
				
				//패스워드는 영문 대/소문자, 숫자, 특수문자를 구분하여 6~2-자로 이루어집니다. 올바른 패스워드를 입력해주세요.

				@Override
				protected void onPreExecute() {
//					mLoadingLayout.setVisibility(View.VISIBLE);
					super.onPreExecute();
				}

				@Override
				protected void onPostExecute(Bitmap result) {
					super.onPostExecute(result);
					
					profileimg.setImageBitmap(result);
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
	
	private Uri getImageUri(String path) {
		return Uri.fromFile(new File(path));
	}
	
	private void doTakePhotoAction() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 임시로 사용할 파일의 경로를 생성
		String url = "tmp_" + String.valueOf(System.currentTimeMillis())+ ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(myApp.getFileDir_Ex(), url));

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,mImageCaptureUri);
		intent.putExtra("return-data", true);
		intent.putExtra("passok", "ok");
		startActivityForResult(intent, PICTURE_CAMERA_CODE);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.s00115);
		Intent intent = getIntent();
		userYn = intent.getBooleanExtra("yn", false);
		if(userYn){
			userId = intent.getStringExtra("userId");
		}
		else {
			userId  = myApp.getUserInfo().getUserId();
		}
		init();
		SendProfile();
		//getdata();
	}
	
	private void IntentGallery() { 
		Intent intent = new Intent(this,CustomGallery2.class);
		intent.putExtra("size",1);//선택가능한갯수
		startActivityForResult(intent, GALLERY_CODE);	
	}
	
	private void UploadProfileImage() {
		try {
			if(mSelectImg.size() > 0) {
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put(HttpUtil.KEY_URL, Global.PROFILE_IMAGE_UPLOAD);
				params.put("userId", myApp.getUserInfo().getUserId());
				params.put("profile", mSelectImg.get(0));
				HttpUtil.getInstance().requestHttp(this, params,
						new OnAfterParsedData() {
					@Override
					public void onResult(boolean result, String resultData) {
						try {
							if(result){ /// 종료하면 여기로 탑니다
								BrUtilManager.getInstance().showToast(getBaseContext(), "프로필 사진이 변경되었습니다.");
								Log.e("", "");
							}else{}	
						} catch (Exception e) {
						}finally{ /// 끝나면 핸들러로 화면셋팅
							//mActionHandler.sendEmptyMessage(1);
							runOnUiThread(new Runnable() {
								public void run() {
									if(mSelectImg.size() > 0) {
										myApp.imageloder.displayImage(mSelectImg.get(0), profileimg, new ImageLoadingListener() {
											@Override
											public void onLoadingStarted(String arg0, View arg1) {
											}
											
											@Override
											public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
											}
											
											@Override
											public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
												Global.mMainRefreshProfile = true;
												Global.mDetailRefreshProfile = true;
												Global.mMoreRefreshProfile = true;
											}
											
											@Override
											public void onLoadingCancelled(String arg0, View arg1) {
											}
										});
										
										array.clear();
										getdata();
									}
									
									
								}
							});
						}
					}
				});	
			}
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}


	public void SendProfile() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.URL_GETPROFILE);
//			String userId = myApp.getUserInfo().getUserId();
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
						getdata();
						//mActionHandler.sendEmptyMessage(1);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}

	private void getdata(){
		try {

			isdataloading = true;
			HashMap<String, Object> params = new HashMap<String, Object>();
			if(listflag){
				params.put(HttpUtil.KEY_URL, Global.URL_BOARDLIST);	
			}else{
				params.put(HttpUtil.KEY_URL, Global.URL_REPLYLIST);
			}

//			String userId = myApp.getUserInfo().getUserId();
			params.put("userId",userId);
			params.put("page", mPage);
			//params.put("파일key", new File("파일경로"));
			HttpUtil.getInstance().requestHttp(this, params,new OnAfterParsedData() {
				@Override
				public void onResult(boolean result, String resultData) {
					try {
						if(result){ /// 종료하면 여기로 탑니다
							if(mPage == 1){
								array.clear();
							}
							if(listflag){
								array = BoardVo.PasingJson(resultData, array);
							}else{
								array = ReplyVo.PasingJson(resultData, array);	
							}

							if(array.size() == 3 && mPage == 0){
								hasmoredata = true;
							}else if(mPage > 0 && array.size() == (mPage * 30)){
								hasmoredata = true;
							}else{
								hasmoredata = false;
							}
							Log.e("", "");
						}else{}	
					} catch (Exception e) {
					}finally{ /// 끝나면 핸들러로 화면셋팅
						mActionHandler.sendEmptyMessage(1);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}

	private void init(){
		try {
			/*Bundle bundle = getIntent().getExtras();
			member = bundle.getParcelable("member");*/
			headerView = (LinearLayout)getLayoutInflater().inflate(R.layout.s00115_header, null);
			
			for(int i = 0 ; i < modifybtn.length ; i ++){
				View v = (View)headerView.findViewById(modifybtn[i]);
				v.setTag(i);
				v.setOnClickListener(this);
			}

			profileimg = (ImageView)headerView.findViewById(R.id.profileimg);
			profileMask = (ImageView)headerView.findViewById(R.id.profilemask);
			edittext1 = (TextView)headerView.findViewById(R.id.edittext1);
			edittext2 = (TextView)headerView.findViewById(R.id.edittext2);
			edittext3 = (TextView)headerView.findViewById(R.id.edittext3);
			edittext4 = (EditText)headerView.findViewById(R.id.edittext4);
			edittext5 = (EditText)headerView.findViewById(R.id.edittext5);
			mHeaderTitleTxt = (TextView)findViewById(R.id.header_title);
			listview = (ListView)findViewById(R.id.listview);



			//listview.setAdapter(adapter);
//			scrollView = (ScrollView) findViewById(R.id.scrollView);
			morebtn = (ImageButton)findViewById(R.id.morebtn);
			
			profileMask.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(userId.equals(myApp.getUserInfo().getUserId()) && myApp.getUserInfo().getMemLvl() > 1)
						IntentGallery();
				}
			});
			

			findViewById(R.id.header_back_button).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});

			morebtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!isdataloading ){
						mPage += 1;
						getdata();
					}
				}
			});

			headerView.findViewById(R.id.tab1).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					((ImageView)headerView.findViewById(R.id.tab1)).setBackgroundResource(R.drawable.tap_menu_01_dw);
					((ImageView)headerView.findViewById(R.id.tab2)).setBackgroundResource(R.drawable.tap_menu_02);
					mPage = 0;
					array.clear();
					listflag = true;
					getdata();
				}
			});

			headerView.findViewById(R.id.tab2).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					((ImageView)headerView.findViewById(R.id.tab1)).setBackgroundResource(R.drawable.tap_menu_01);
					((ImageView)headerView.findViewById(R.id.tab2)).setBackgroundResource(R.drawable.tap_menu_02_dw);
					mPage = 0;
					array.clear();
					listflag = false;

					getdata();
				}
			});
			listview.addHeaderView(headerView);
//			listview.setOnTouchListener(new OnTouchListener() {        //리스트뷰 터취 리스너
//				@Override
//				public boolean onTouch(View v, MotionEvent event) { 
//					scrollView.requestDisallowInterceptTouchEvent(true);    // 리스트뷰에서 터취가되면 스크롤뷰만 움직이게
//					return false;
//				}
//			});

			/*	listview.setOnScrollListener(new OnScrollListener() {
				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					try {
						if(hasmoredata && !isdataloading){
							if (firstVisibleItem + visibleItemCount >= totalItemCount) {
								mPage = mPage + 1;
								getdata();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});*/

			//SetView();
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}

	private void listViewHeightSet(Adapter listAdapter, ListView listView)
	{
		try {
			int totalHeight = 0;
			for (int i = 0; i < listAdapter.getCount(); i++)
			{
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}


			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			listView.setLayoutParams(params);	
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
		
	}


	private void SetView(){
		try {
			if(member.getMemLvl() == 1){
				String[] birth = member.getBirthDay().split("-");
				int age = BrDateManager.getInstance().GetAge(Integer.valueOf(birth[0]), Integer.valueOf(birth[1]), 1);
				if(member.getSex() == 1){
					setMenDreamPhoto(age);
				}else{
					setGirlDreamPhoto(age);
				}
			}else{
				if(member.getHappyPhoto() !=  null && member.getHappyPhoto().length() > 5){
					if(member.getHappyPhoto().startsWith("http")) {
						myApp.imageloder.displayImage(member.getHappyPhoto(), profileimg);
					}
					else {
						myApp.imageloder.displayImage(Global.host + member.getHappyPhoto(), profileimg);						
					}

				}
				
				if(member.getMemLvl() == 2) {
					profileMask.setImageResource(R.drawable.happy_prf_bg_01);
				}
				else if(member.getMemLvl() == 3) {
					profileMask.setImageResource(R.drawable.happy_prf_bg_02);
				}
				else if(member.getMemLvl() == 4) {
					profileMask.setImageResource(R.drawable.happy_prf_bg_03);
				}
				else {
					profileMask.setImageResource(R.drawable.prf_box_bg);
				}
			}
			mHeaderTitle = member.getName();
			mHeaderTitleTxt.setText(mHeaderTitle);
			edittext1.setText(member.getNickName());
			edittext2.setText(member.getPswd());
			String[] birth = member.getBirthDay().split(" ");
			edittext3.setText(birth[0]);
			edittext4.setText(member.getSchool());
			edittext5.setText(member.getLocation());
			
			if(mPage == 0 || mPage == 1){
				if(listflag){
					adapter = new AListAdapter(SC00115.this, array);
//					listview.addHeaderView(headerView);
					listview.setAdapter(adapter);
//					listViewHeightSet(adapter, listview);
//					adapter.notifyDataSetChanged();
				}else{
					adapter2 = new AListAdapter2(SC00115.this, array);
//					listview.addHeaderView(headerView);
					listview.setAdapter(adapter2);
//					listViewHeightSet(adapter2, listview);
//					adapter2.notifyDataSetChanged();
				}
			}else {
				if(listflag){
//					listViewHeightSet(adapter, listview);
//					listview.addHeaderView(headerView);
					listview.setAdapter(adapter);
//					adapter.notifyDataSetChanged();
				}else{
//					listview.addHeaderView(headerView);
					listview.setAdapter(adapter2);
//					listViewHeightSet(adapter2, listview);
//					adapter2.notifyDataSetChanged();
				}
			}

			isdataloading = false;
			if(hasmoredata){
				morebtn.setVisibility(View.VISIBLE);
			}else{
				morebtn.setVisibility(View.GONE);
			}


		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}

	private void setMenDreamPhoto(int age){
		if(age <= 12 ){
			profileimg.setBackgroundResource(R.drawable.prf_img_01_m);
		}else if(age > 12 && age <=15){
			profileimg.setBackgroundResource(R.drawable.prf_img_02_m);
		}else if(age > 15 && age <= 18){
			profileimg.setBackgroundResource(R.drawable.prf_img_03_m);
		}else if(age > 18 && age <= 39){
			profileimg.setBackgroundResource(R.drawable.prf_img_04_m);
		}else if(age >= 40){
			profileimg.setBackgroundResource(R.drawable.prf_img_05_m);
		}
	}

	private void setGirlDreamPhoto(int age){
		if(age <= 12 ){
			profileimg.setBackgroundResource(R.drawable.prf_img_01_g);
		}else if(age > 12 && age <=15){
			profileimg.setBackgroundResource(R.drawable.prf_img_02_g);
		}else if(age > 15 && age <= 18){
			profileimg.setBackgroundResource(R.drawable.prf_img_03_g);
		}else if(age > 18 && age <= 39){
			profileimg.setBackgroundResource(R.drawable.prf_img_04_g);
		}else if(age >= 40){
			profileimg.setBackgroundResource(R.drawable.prf_img_05_g);
		}
	}
	@Override
	public void onClick(View v) {
		final int INDEX = Integer.valueOf(v.getTag() + "");
		/*	if(INDEX == 0){
			if(!BrUtilManager.getInstance().getEditTextNullCheck(edittext1)){
				BrUtilManager.getInstance().ShowDialog(SC00115.this, getString(R.string.app_name), "닉네임을 입력해주세요");
				return;
			}
		}else */if(INDEX == 1){
			/*	if(!BrUtilManager.getInstance().getEditTextNullCheck(edittext2)){
				BrUtilManager.getInstance().ShowDialog(SC00115.this, getString(R.string.app_name), "비밀번호를 입력해주세요");
				return;
			}else{*/
			/*	CustomDialog dialog = new CustomDialog(this);
				dialog.show();*/
			Intent intent = new Intent(this,PassChanged.class);
			intent.putExtra("passwd", member.getPswd());
			startActivityForResult(intent, 1001);
			//}
		}else if(INDEX == 2){
			BrDateManager.getInstance().CallYearMonthPicker(this, new BrDateManager.onDateSelected() {
				@Override
				public void onDateSelectedListner(String year, String month, String day) {
					Log.e("", "");
					birthday = year + "-" + month + "-" + day + " 12:00:00";
					ModifySend(INDEX);
				}
			});
		}else if(INDEX == 3){
			if(!BrUtilManager.getInstance().getEditTextNullCheck(edittext4)){
				BrUtilManager.getInstance().ShowDialog(SC00115.this, getString(R.string.app_name), "학교명을 입력해주세요");
				return;
			}else{
				ModifySend(INDEX);
			}
		}else if(INDEX == 4){
			if(!BrUtilManager.getInstance().getEditTextNullCheck(edittext4)){
				BrUtilManager.getInstance().ShowDialog(SC00115.this, getString(R.string.app_name), "지역을 입력해주세요");
				return;
			}else{
				ModifySend(INDEX);
			}
		}
	}

	private void ModifySend(int position){
		switch(position){
		case 0:
			Send(position,Global.URL_MODIFYNICK);
			break;
		case 2:
			Send(position,Global.URL_CHANGEBIRTH);
			break;
		case 3:
			Send(position,Global.URL_CHANGESCHOOL);
			break;
		case 4:
			Send(position,Global.URL_CHANGELOCATION);
			break;
		}
	}

	public void Send(final int position , String url) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, url);
//			String userId = myApp.getUserInfo().getUserId();
			params.put("userId", userId);
			if(position == 0){
				params.put("nickName", edittext1.getText().toString());
			}else if(position == 1){
				params.put("oldPw", member.getPswd());
				params.put("newPw", edittext2.getText().toString());
			}else if(position == 2){
				params.put("birthDay", birthday);
			}else if(position == 3){
				params.put("school", edittext4.getText().toString());
			}else if(position == 4){
				params.put("location", edittext5.getText().toString());
			}

			//params.put("파일key", new File("파일경로"));
			HttpUtil.getInstance().requestHttp(this, params,new OnAfterParsedData() {
				@Override
				public void onResult(boolean result, String resultData) {
					try {
						if(result){ /// 종료하면 여기로 탑니다
							if(position == 2){
								member.setBirthDay(birthday);
							}else if(position == 3){
								member.setSchool(edittext4.getText().toString());
							}else if(position == 4){
								member.setLocation(edittext5.getText().toString());
							}

						}else{}	
					} catch (Exception e) {
					}finally{ /// 끝나면 핸들러로 화면셋팅
						mActionHandler.sendEmptyMessage(2);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}

	public class AListAdapter extends ArrayAdapter<Object> {
		public Context mContext;
		public AListAdapter(Context context, ArrayList<Object> arraylist) {
			super(context, R.layout.s00013_item, arraylist);
			this.mContext = context;
		}

		public class ViewHolder2 {

			ImageView list_item_profile_img;
			TextView list_item_name_txt;
			TextView list_item_category_txt;
			TextView list_item_time_txt;
			TextView list_item_content_txt;
			TextView ico_speaker_txt;
			TextView ico_reply_txt;
			TextView ico_good_txt;
			RelativeLayout RelativeLayout1;
			Button goodBtn;
			Button repotBtn;
			ImageView list_item_profile_mask;
			int seq;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder2 holder;
			BoardVo data = null;
			data = (BoardVo)array.get(position);
			String content = data.getContents();
			try {
				if (convertView == null) {
					LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = layoutInflater.inflate(R.layout.s00013_item,null);
					holder = new ViewHolder2();
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder2) convertView.getTag();
				}
				if (data != null) {

					holder.list_item_profile_img = (ImageView) convertView.findViewById(R.id.list_item_profile_img);
					holder.list_item_name_txt = (TextView) convertView.findViewById(R.id.list_item_name_txt);
					holder.list_item_category_txt = (TextView) convertView.findViewById(R.id.list_item_category_txt);
					holder.list_item_time_txt = (TextView) convertView.findViewById(R.id.list_item_time_txt);
					holder.list_item_content_txt = (TextView) convertView.findViewById(R.id.list_item_content_txt);
					holder.ico_speaker_txt = (TextView) convertView.findViewById(R.id.ico_speaker_txt);
					holder.ico_reply_txt = (TextView) convertView.findViewById(R.id.ico_reply_txt);
					holder.ico_good_txt = (TextView) convertView.findViewById(R.id.ico_good_txt);
					holder.RelativeLayout1  = (RelativeLayout)convertView.findViewById(R.id.RelativeLayout1);
					holder.list_item_profile_mask = (ImageView) convertView.findViewById(R.id.list_item_profile_mask);
					
					//				holder.RelativeLayout1.setOnClickListener(new OnClickListener() {
					//					@Override
					//					public void onClick(View v) {
					//						BoardVo vo = (BoardVo)v.getTag();
					//						int seq = vo.getSeq();
					//						// TODO Auto-generated method stub
					//						
					//					}
					//				});
					//				holder.RelativeLayout1.setTag(data);
					holder.seq = data.getSeq();
					if(member.getMemLvl() == 1){
						String[] birth = member.getBirthDay().split("-");
						int age = BrDateManager.getInstance().GetAge(Integer.valueOf(birth[0]), Integer.valueOf(birth[1]), 1);
						if(member.getSex() == 1){
							setMenDreamPhoto(age,holder.list_item_profile_img);
						}else{
							setGirlDreamPhoto(age,holder.list_item_profile_img);
						}
					}else{
						if(member.getHappyPhoto() !=  null && member.getHappyPhoto().length() > 5){
							
							if(member.getHappyPhoto().startsWith("http")) {
								myApp.imageloder.displayImage(member.getHappyPhoto(), holder.list_item_profile_img);
							}
							else {
								myApp.imageloder.displayImage(Global.host + member.getHappyPhoto(), holder.list_item_profile_img);	
							}
							
						}else{

						}
					}
					

					if(member.getMemLvl() == 2) {
						holder.list_item_profile_mask.setImageResource(R.drawable.happy_prf_bg_01);
					}
					else if(member.getMemLvl() == 3) {
						holder.list_item_profile_mask.setImageResource(R.drawable.happy_prf_bg_02);
					}
					else if(member.getMemLvl() == 4) {
						holder.list_item_profile_mask.setImageResource(R.drawable.happy_prf_bg_03);
					}
					else {
						holder.list_item_profile_mask.setImageResource(R.drawable.prf_box_bg);
					}
					
					holder.list_item_time_txt.setText(Common.CreateDataWithCheck(data.getWriteTime()));

					if(data.getUnknown() == 1){
						holder.list_item_name_txt.setText( data.getTempName());	
					}else{
						holder.list_item_name_txt.setText(data.getNickName());
					}
					
					convertView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mContext, S00113.class);
							ViewHolder2 vo = (ViewHolder2) v.getTag();
							intent.putExtra("seq", vo.seq);
							((Activity)mContext).startActivityForResult(intent, BOARD_SEQ);
						}
					});
					
					if(content.length() > 80) {
						content = content.substring(0,80);
						content += "[ ... ]";
					}
					holder.list_item_content_txt.setText(content);

					holder.list_item_category_txt.setText(Common.getCategoryKindString(data.getKind()));
					
					holder.ico_speaker_txt.setText(String.valueOf(data.getReportCnt()));
					holder.ico_reply_txt.setText(String.valueOf(data.getReplyCnt()));
					holder.ico_good_txt.setText(String.valueOf(data.getLikeCnt()));
				}
			} catch (Exception e) {
				e.printStackTrace();
				WriteFileLog.writeException(e);
			}

		
			return convertView;
		}

		private String setKind(int index){
			switch(index){

			case 1: return "공통";
			case 2: return "왕따, 학교폭력";
			case 3: return "성적, 학업문제";
			case 4: return "친구, 이성문제";
			case 5: return "부모님과 갈등";
			case 6: return "선생님과 갈등";
			case 7: return "가정형편, 경제";
			case 8: return "외모문제";
			case 9: return "기타문제";

			}

			return "";

		}
		private void setMenDreamPhoto(int age,ImageView imageview){
			if(age <= 12 ){
				imageview.setBackgroundResource(R.drawable.prf_img_01_m);
			}else if(age > 12 && age <=15){
				imageview.setBackgroundResource(R.drawable.prf_img_02_m);
			}else if(age > 15 && age <= 18){
				imageview.setBackgroundResource(R.drawable.prf_img_03_m);
			}else if(age > 18 && age <= 39){
				imageview.setBackgroundResource(R.drawable.prf_img_04_m);
			}else if(age >= 40){
				imageview.setBackgroundResource(R.drawable.prf_img_05_m);
			}
		}

		private void setGirlDreamPhoto(int age,ImageView imageview){
			if(age <= 12 ){
				imageview.setBackgroundResource(R.drawable.prf_img_01_g);
			}else if(age > 12 && age <=15){
				imageview.setBackgroundResource(R.drawable.prf_img_02_g);
			}else if(age > 15 && age <= 18){
				imageview.setBackgroundResource(R.drawable.prf_img_03_g);
			}else if(age > 18 && age <= 39){
				imageview.setBackgroundResource(R.drawable.prf_img_04_g);
			}else if(age >= 40){
				imageview.setBackgroundResource(R.drawable.prf_img_05_g);
			}
		}

	}


	public class AListAdapter2 extends ArrayAdapter<Object> {
		public Context mContext;
		public AListAdapter2(Context context, ArrayList<Object> arraylist) {
			super(context, R.layout.s00113_item, arraylist);
			this.mContext = context;
		}

		public class ViewHolder2 {

			ImageView imageView22; //프로필이미지
			TextView reply_item_name_txt; //이름 
			TextView reply_item_time_txt; // 시간
			TextView reply_item_content_txt; // 댓글내용
			RelativeLayout reply_audio_layout; //  녹음 레이아웃
			ResizeImageView reply_item_photo_img; // 첨부이미지
			RelativeLayout RelativeLayout1;
			TextView ico_speaker_txt;
			TextView ico_good_txt;
			RelativeLayout videoLayout;
			ResizeImageView videoPhoto;
			TextView videoPlayBtn;
			TextView audioPlayBtn;
			LinearLayout layout;
			ImageView prof_mask_img;
			int seq;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder2 holder;
			ReplyVo data = null;
			data = (ReplyVo)array.get(position);	
			try {
				if (convertView == null) {
					LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = layoutInflater.inflate(R.layout.s00113_item,null);
					holder = new ViewHolder2();
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder2) convertView.getTag();
				}
				if (data != null) {

					holder.imageView22 = (ImageView) convertView.findViewById(R.id.prof_img);
					holder.reply_item_name_txt = (TextView) convertView.findViewById(R.id.reply_item_name_txt);
					holder.reply_item_time_txt = (TextView) convertView.findViewById(R.id.reply_item_time_txt);
					holder.reply_item_content_txt = (TextView) convertView.findViewById(R.id.reply_item_content_txt);
					holder.reply_audio_layout = (RelativeLayout)convertView.findViewById(R.id.reply_audio_layout);
					holder.reply_item_photo_img = (ResizeImageView) convertView.findViewById(R.id.reply_item_photo_img);
					holder.ico_speaker_txt = (TextView) convertView.findViewById(R.id.ico_speaker_txt);
					holder.ico_good_txt = (TextView) convertView.findViewById(R.id.ico_good_txt);
					holder.RelativeLayout1 = (RelativeLayout)convertView.findViewById(R.id.RelativeLayout1);
					holder.videoLayout = (RelativeLayout) convertView.findViewById(R.id.reply_video_layout);
//					holder.videoPhoto = (ResizeImageView) convertView.findViewById(R.id.reply_item_video_img);
					holder.videoPlayBtn = (TextView) convertView.findViewById(R.id.reply_item_video_play_btn);
					holder.audioPlayBtn = (TextView) convertView.findViewById(R.id.reply_audio_play_btn);
					holder.layout = (LinearLayout) convertView.findViewById(R.id.reply_media_content_layout);
					holder.prof_mask_img = (ImageView) convertView.findViewById(R.id.prof_mask_img);
					
					holder.seq = data.getNo();
					//					holder.RelativeLayout1.setOnClickListener(new OnClickListener() {
					//						@Override
					//						public void onClick(View v) {
					//							ReplyVo vo = (ReplyVo)v.getTag();
					//							int seq = vo.getSeq();
					//							// TODO Auto-generated method stub
					//							
					//						}
					//					});
					//					holder.RelativeLayout1.setTag(data);
					if(member.getMemLvl() == 1){
						String[] birth = member.getBirthDay().split("-");
						int age = BrDateManager.getInstance().GetAge(Integer.valueOf(birth[0]), Integer.valueOf(birth[1]), 1);
						if(member.getSex() == 1){
							setMenDreamPhoto(age,holder.imageView22);
						}else{
							setGirlDreamPhoto(age,holder.imageView22);
						}
					}else{
						if(member.getHappyPhoto() !=  null && member.getHappyPhoto().length() > 5){
							if(member.getHappyPhoto().startsWith("http")) {
								myApp.imageloder.displayImage(member.getHappyPhoto(), holder.imageView22);
							}
							else {
								myApp.imageloder.displayImage(Global.host + member.getHappyPhoto(), holder.imageView22);								
							}

						}else{

						}
					}

					if(data.getMovName().length() < 10) {
						holder.videoLayout.setVisibility(View.GONE);
					}
					else {
						holder.videoLayout.setVisibility(View.VISIBLE);
						holder.videoPlayBtn.setTag(data.getMovName());
						holder.videoPlayBtn.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								String[] split = v.getTag().toString().split("::");
								String thumbPath = split[0];
								String movPath = split[1];
								
								Uri uri = Uri.parse(movPath);
								Intent intent = new Intent(Intent.ACTION_VIEW);  
								intent.setDataAndType(uri, "video/*");  
								((Activity)mContext).startActivity(intent);
							}
							
						});

					}
					holder.audioPlayBtn.setTag(data.getAudioName());
					holder.audioPlayBtn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Uri uri = Uri.parse((String)v.getTag() + "");
							Intent intent = new Intent(Intent.ACTION_VIEW);  
							intent.setDataAndType(uri, "audio/*");  
							((Activity)mContext).startActivity(intent);
						}
					});

					if(data.getTempName() != null && data.getTempName().length() > 0){
						holder.reply_item_name_txt.setText(data.getTempName());	
					}else{
						holder.reply_item_name_txt.setText(data.getNickName());
					}

					Common.setTagName(data.getCommentContents(), holder.reply_item_content_txt);
					
					
					holder.ico_speaker_txt.setText(String.valueOf(data.getReportCnt()));
					holder.ico_good_txt.setText(String.valueOf(data.getLikeCnt()));
					holder.reply_item_time_txt.setText(Common.CreateDataWithCheck(data.getCommentTime()));

					if(data.getAudioName() != null && data.getAudioName().length() > 5){
						holder.reply_audio_layout.setVisibility(View.VISIBLE);
					}else{
						holder.reply_audio_layout.setVisibility(View.GONE);
					}

					

					if(member.getMemLvl() == 2) {
						holder.prof_mask_img.setImageResource(R.drawable.happy_prf_bg_01);
					}
					else if(member.getMemLvl() == 3) {
						holder.prof_mask_img.setImageResource(R.drawable.happy_prf_bg_02);
					}
					else if(member.getMemLvl() == 4) {
						holder.prof_mask_img.setImageResource(R.drawable.happy_prf_bg_03);
					}
					else {
						holder.prof_mask_img.setImageResource(R.drawable.prf_box_bg);
					}
					
					if(data.getPhotoName().length() < 10) {
						holder.reply_item_photo_img.setVisibility(View.GONE);
					}
					else {
						holder.reply_item_photo_img.setVisibility(View.VISIBLE);
						myApp.imageloder.displayImage(data.getPhotoName(), holder.reply_item_photo_img, new ImageLoadingListener() {
							@Override
							public void onLoadingStarted(String arg0, View arg1) {
								Log.e("", "");
							}
							
							@Override
							public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
								Log.e("", "");
							}
							
							@Override
							public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
								listViewHeightSet(adapter, listview);
							}
							
							@Override
							public void onLoadingCancelled(String arg0, View arg1) {
								Log.e("", "");
							}
						});
					}
					
					

					convertView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mContext, S00113.class);
							ViewHolder2 vo = (ViewHolder2) v.getTag();
							intent.putExtra("seq", vo.seq);
							((Activity)mContext).startActivityForResult(intent, BOARD_SEQ);
						}
					});

					
					
//					if(array.size() == position-1){
//						
//					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				WriteFileLog.writeException(e);
			}


		return convertView;
	}

	private void setMenDreamPhoto(int age,ImageView imageview){
		if(age <= 12 ){
			imageview.setBackgroundResource(R.drawable.prf_img_01_m);
		}else if(age > 12 && age <=15){
			imageview.setBackgroundResource(R.drawable.prf_img_02_m);
		}else if(age > 15 && age <= 18){
			imageview.setBackgroundResource(R.drawable.prf_img_03_m);
		}else if(age > 18 && age <= 39){
			imageview.setBackgroundResource(R.drawable.prf_img_04_m);
		}else if(age >= 40){
			imageview.setBackgroundResource(R.drawable.prf_img_05_m);
		}
	}

	private void setGirlDreamPhoto(int age,ImageView imageview){
		if(age <= 12 ){
			imageview.setBackgroundResource(R.drawable.prf_img_01_g);
		}else if(age > 12 && age <=15){
			imageview.setBackgroundResource(R.drawable.prf_img_02_g);
		}else if(age > 15 && age <= 18){
			imageview.setBackgroundResource(R.drawable.prf_img_03_g);
		}else if(age > 18 && age <= 39){
			imageview.setBackgroundResource(R.drawable.prf_img_04_g);
		}else if(age >= 40){
			imageview.setBackgroundResource(R.drawable.prf_img_05_g);
		}
	}

}


public Handler mActionHandler = new Handler() {
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:

			SetView();

			/*if(listflag){
					adapter.notifyDataSetChanged();	
				}else{
					adapter2.notifyDataSetChanged();
				}*/

			break;
		case 2:
			SetView();
			BrUtilManager.getInstance().showToast(SC00115.this, "프로필이 수정되었습니다.");
			break;

		}
	}
};

public class CustomDialog extends Dialog {
	Context mcontext;
	EditText input_pass1,input_pass2,input_pass3;

	public CustomDialog(Context context) {
		super(context);
		mcontext = context;
		init();
	}

	private void Send(){
		try {
			if(!BrUtilManager.getInstance().getEditTextNullCheck(input_pass1)){
				BrUtilManager.getInstance().showToast(mcontext, "기존 비밀번호를 입력하세요");
				return;
			}

			if(!BrUtilManager.getInstance().getEditTextNullCheck(input_pass2)){
				BrUtilManager.getInstance().showToast(mcontext, "새 비밀번호를 입력하세요");
				return;
			}

			if(!BrUtilManager.getInstance().getEditTextNullCheck(input_pass3)){
				BrUtilManager.getInstance().showToast(mcontext, "새 비밀번호를 한번더 입력하세요");
				return;
			}

			/*if(input_pass1.getText().toString().trim().equals("")){

				}*/

			if(!input_pass2.getText().toString().trim().equals(input_pass3.getText().toString().trim())){
				BrUtilManager.getInstance().showToast(mcontext, "비밀번호를 확인하세요");
				return;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.URL_CHANGEPW);
//			String userId = myApp.getUserInfo().getUserId();
			params.put("userId", userId);
			params.put("oldPw",input_pass1.getText().toString().trim());
			params.put("newPw",input_pass3.getText().toString().trim());

			//params.put("파일key", new File("파일경로"));
			HttpUtil.getInstance().requestHttp(SC00115.this, params,new OnAfterParsedData() {
				@Override
				public void onResult(boolean result, String resultData) {
					try {
						if(result){ /// 종료하면 여기로 탑니다
							mActionHandler2.sendEmptyMessage(1);	
						}else{
							mActionHandler2.sendEmptyMessage(2);
						}	
					} catch (Exception e) {
					}finally{ /// 끝나면 핸들러로 화면셋팅

					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}

	private void init(){

		try {
			findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			findViewById(R.id.changebtn).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Send();
				}
			});

			input_pass1 = (EditText)findViewById(R.id.input_pass1);
			input_pass2 = (EditText)findViewById(R.id.input_pass2);
			input_pass3 = (EditText)findViewById(R.id.input_pass3);
			
			Intent intent  = getIntent();
			String passwd = intent.getStringExtra("passwd");
			input_pass1.setText(passwd);
		} catch (Exception e) {
			e.printStackTrace();
			WriteFileLog.writeException(e);
		}
	}

	public Handler mActionHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				BrUtilManager.getInstance().showToast(mcontext, "비밀번호가 변경되었습니다.");
				edittext2.setText(input_pass3.getText().toString().trim());
				dismiss();
				break;
			case 2:
				BrUtilManager.getInstance().showToast(mcontext, "비밀번호 변경이 실패되었습니다.");
				break;
			}
		}
	};
}
}
