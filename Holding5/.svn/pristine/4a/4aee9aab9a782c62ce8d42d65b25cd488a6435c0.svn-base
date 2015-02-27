package com.br.holding5.sc003.gallery;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.br.holding5.WriteFileLog;
import com.br.holding5.ms.MSUtil;
import com.brainyx.holding5.R;
import com.brainyxlib.BrImageCacheManager;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.dialogclick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/** <p><b>Class Name : </b>DeleteImageView</p>
 * <b>Description :</b>
 * <p>이미지 삭제 버튼 있는 이미지뷰</p>
 *
 * @author LMS
 * @version 1.0.0 
 * @since 2014. 8. 25.
 * @see ????Class
 */
@SuppressLint("ResourceAsColor") public class DeleteImageView extends FrameLayout implements OnClickListener{
	public DisplayImageOptions options ;
	public interface onDeleteImageCallback {
		public void deleteImageCallback(int type);
	}

	public onDeleteImageCallback mOnDeleteImageCallback; 

	public ImageLoader imageloder;
	MSUtil msUtil ;
	/**
	 * 이미지삭제버튼
	 */
	private ImageButton _deleteButton;
	/**
	 * 이미지뷰
	 */
	private ImageView _pictureImage;
	/**
	 * 비트맵 생성여부
	 */
	private boolean _bitmapYn = false;
	/**
	 * 이미지 삽입 여부
	 */
	private boolean _addPictureYn = false;
	/**
	 * 사진 url
	 */
	private String 	_photoUrl ="";
	/**
	 * 사진 uuid
	 */
	private String 	_photoUUID = "";

	/**
	 * 기본이미지 비트맵
	 */
	private Bitmap					_defaultBitmap;


	private String videoPath="";
	private String videoThumbPath="";
	private boolean mClearImageBtnYn = true;
	private boolean mDeleteYn = false;
	private int mFileType = 0;//1 imgae, 2 vide, 3 audio
	private com.nostra13.universalimageloader.core.ImageLoader _imageLoader;
	
	public DeleteImageView(Context context)
	{
		super(context);
		init();
	}
	
	

	public int getmFileType() {
		return mFileType;
	}



	public void setmFileType(int mFileType) {
		this.mFileType = mFileType;
	}



	public DeleteImageView(Context context, boolean flag, int width, int height)
	{
		super(context);
	}
	
	public void setDeleteYn(boolean flag) {
		this.mDeleteYn = flag;
	}
	
	public void setPictureYn (boolean flag) {
		this._addPictureYn = flag;
	}

	public void setClearImageBtnYn(boolean flag) {
		mClearImageBtnYn = flag;

		if(mClearImageBtnYn) {
			_deleteButton.setEnabled(flag);
			_deleteButton.setVisibility(View.VISIBLE);
		}
		else {
			_deleteButton.setEnabled(flag);
			_deleteButton.setVisibility(View.INVISIBLE);
		}
	}

	public DeleteImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public ImageView getImageView() {
		return _pictureImage;
	}

	public void setPhotoUUID ( String uuid ) {
		_photoUUID = uuid;
	}

	public String getPhotoUUID () {
		return _photoUUID;
	}

	public boolean isPictureYn() {
		return _addPictureYn;
	}
	ImageView mask;
	public void setOnDeleteImageCallback(onDeleteImageCallback callback) {
		mOnDeleteImageCallback = callback;
	}
	/** <p><b>Method Name : </b>init</p>
	 * <b>Description :</b>
	 * <p>초기화</p>
	 *
	 * @author LMS  
	 * @since 2014. 8. 26.
	 * void
	 */
	private void init() { 
		try {
			_deleteButton = new ImageButton(getContext());
			_pictureImage = new ImageView(getContext());
			mask = new ImageView(getContext());
			options = BrImageCacheManager.getInstance().ImageLoaderInit(getContext(), R.drawable.ic_launcher, R.drawable.ic_launcher);
			imageloder = ImageLoader.getInstance();


			msUtil = MSUtil.getInstance();
			msUtil.setContext(getContext());

			LayoutParams mainParams = new LayoutParams(msUtil.intTodp(64), msUtil.intTodp(64));
			LayoutParams imageParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			LayoutParams buttonParams = new LayoutParams(msUtil.intTodp(22), msUtil.intTodp(22));
			buttonParams.gravity = Gravity.TOP | Gravity.RIGHT;
			this.setLayoutParams(mainParams);
			_deleteButton.setLayoutParams(buttonParams);
//			_deleteButton.setLayoutParams(imageParams);
			_pictureImage.setLayoutParams(imageParams);
			mask.setLayoutParams(imageParams);
			_pictureImage.setScaleType(ScaleType.FIT_XY);
			mask.setImageResource(R.drawable.write_add_photo_bg);
//			_defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

			_pictureImage.setScaleType(ScaleType.FIT_XY);
			_pictureImage.setImageBitmap(_defaultBitmap);
			_deleteButton.setImageResource(R.drawable.btn_add_del);
//			_deleteButton.setBackgroundColor(getResources().getColor(R.color.clear));
			_deleteButton.setBackgroundResource(R.color.clear);
//			_deleteButton.setBackgroundDrawable(null);

			//		addPicture("null");


			this.addView(_pictureImage);
			this.addView(mask);
			this.addView(_deleteButton);

			_deleteButton.setOnClickListener(this);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	public void setMaskImage(int type) {
		if(type == 1) {
			mask.setImageResource(R.drawable.write_add_photo_bg_blue);
		}
		else if(type == 2) {
			mask.setImageResource(0);
		}
		else if(type == 3) {
			mask.setImageResource(0);
		}
	}
	
	public void setMaskImage2(int type) {
		if(type == 1) {
			mask.setImageResource(R.drawable.write_add_photo_bg);
		}
		else if(type == 2) {
			mask.setImageResource(0);
		}
		else if(type == 3) {
			mask.setImageResource(0);
		}
	}

	public void setDefaultImage() {
		_pictureImage.setImageBitmap(_defaultBitmap);
	}

	public String getPhotoURL() {
		return _photoUrl;
	}

	public void setImage(Bitmap bitmap, int type, boolean flag) {
		try {
			_addPictureYn = true;
			mFileType = type;
			_pictureImage.setVisibility(View.VISIBLE);
			_pictureImage.setImageBitmap(bitmap);	
			if(flag) {
				setMaskImage2(type);
			}
			else {
				setMaskImage(type);	
			}
			
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	
	
	
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getVideoThumbPath() {
		return videoThumbPath;
	}

	public void setVideoThumbPath(String videoThumbPath) {
		this.videoThumbPath = videoThumbPath;
	}
	
	
	

	public String get_photoUrl() {
		return _photoUrl;
	}

	public void set_photoUrl(String _photoUrl) {
		this._photoUrl = _photoUrl;
	}
	
	public void clearPath() {
		_photoUrl = "";
		videoPath = "";
		videoThumbPath = "";
	}
	
	public void clearImage() {
		clearPath();
//		_pictureImage.setBackgroundResource(null);
	}

	public void setImage(int resourceId , int type) {
		_addPictureYn = true;
		mFileType = type;
		_pictureImage.setVisibility(View.VISIBLE);
		_pictureImage.setImageResource(resourceId);
		setMaskImage(type);
	}
	
	
	

	public void setImage(String imageURL, int type) {
		_addPictureYn = true;
		mFileType = type;
		_pictureImage.setVisibility(View.VISIBLE);
		imageloder.displayImage(imageURL, _pictureImage);
		//배경이 흰화면일때 true
//		if(flag) {
//			setMaskImage2(type);
//		}else {
//			setMaskImage(type);
//		}

	}
	
	public void setImage(String imageURL) {
		_addPictureYn = true;
		_pictureImage.setVisibility(View.VISIBLE);
		imageloder.displayImage(imageURL, _pictureImage);
	}

	//	/** <p><b>Method Name : </b>addPicture</p>
	//	 * <b>Description :</b>
	//	 * <p>이미지뷰에 해당 사진 url을 지정</p>
	//	 *
	//	 * @author LMS  
	//	 * @since 2014. 8. 26.
	//	 * @param imageURL
	//	 * void
	//	 */
	//	public void addPicture(String imageURL) { 
	//		
	//		_addPictureYn = true;
	//		_photoUrl = imageURL;
	//		ImageRequest imageRequest = new ImageRequest(imageURL, new Listener<Bitmap>() {
	//
	//			@Override
	//			public void onResponse(Bitmap response) {
	//				if(response == null) {
	//					_pictureImage.setImageBitmap(_defaultBitmap);
	//					_addPictureYn = false;
	//					_photoUrl = "";
	//					_photoUUID = "";
	//				}
	//				else {
	//					_addPictureYn = true;
	//				}
	//			}
	//		}, 0, 0, Config.ARGB_8888, new ErrorListener() {
	//
	//			@Override
	//			public void onErrorResponse(VolleyError error) {
	//				_pictureImage.setImageBitmap(_defaultBitmap);
	//				_addPictureYn = false;
	//				_photoUrl = "";
	//				_photoUUID = "";
	//			}
	//		});
	//		imageRequest.setTag(10);
	//		_requestQueue.add(imageRequest);
	//
	//
	//		_pictureImage.setScaleType(ScaleType.CENTER);
	//		_pictureImage.setImageUrl(null, null);
	//		_pictureImage.setImageUrl(imageURL, _imageLoader);
	//	}

	@Override
	public void onClick(View v) {
		try {
			if(v.getId() == _deleteButton.getId()) {
				if(mDeleteYn) {
					BrUtilManager.getInstance().showToast(getContext(),"이미지/동영상/음성은 수정할 수 없습니다.");
					
//					AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getContext());
//					alert_confirm.setMessage("이미지/동영상/음성은 수정할 수 없습니다.").setCancelable(false).setPositiveButton("확인",
//							new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//						}
//					});
//					AlertDialog alert = alert_confirm.create();
//					alert.show();
				}
				else if(_addPictureYn) {
					BrUtilManager.getInstance().ShowDialog2btn(getContext(), getResources().getString(R.string.app_name), "삭제하시겠습니까?", new dialogclick() {
						@Override
						public void setondialogokclick() {
							_pictureImage.setImageBitmap(_defaultBitmap);
							//				_tabView.removePicturePath(_tag);
							_photoUrl = "";
							_photoUUID = "";
							_addPictureYn = false;
							if(mOnDeleteImageCallback != null) {
								clearPath();
								setVisibility(View.GONE);
								mOnDeleteImageCallback.deleteImageCallback(mFileType);
							}
						}
						
						@Override
						public void setondialocancelkclick() {
							return;
						}
					});
//					AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getContext());
//					alert_confirm.setMessage("삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
//							new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// 'YES'
//							_pictureImage.setImageBitmap(_defaultBitmap);
//							//				_tabView.removePicturePath(_tag);
//							_photoUrl = "";
//							_photoUUID = "";
//							_addPictureYn = false;
//							if(mOnDeleteImageCallback != null) {
//								clearPath();
//								setVisibility(View.GONE);
//								mOnDeleteImageCallback.deleteImageCallback(mFileType);
//							}
//								
//						}
//					}).setNegativeButton("취소",
//							new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// 'No'
//							return;
//						}
//					});
//					AlertDialog alert = alert_confirm.create();
//					alert.show();
				}
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}

	public void setAudioImage() {
		_pictureImage.setBackgroundResource(R.drawable.write_add_record_bg);
	}
	
	public void setVideoImage() {
		_pictureImage.setBackgroundResource(R.drawable.write_add_movie_bg);
	}


	//	@Override
	//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	//		// megabox Auto-generated method stub
	//
	//		Drawable drawable = getDrawable();
	//
	//		if(drawable != null) {
	//			int width = drawable.getIntrinsicWidth();
	//			int height = drawable.getIntrinsicHeight();
	//
	//			if( _maxWidth > width ) {
	//				float percent = _maxWidth / width;
	//
	//				height = (int) (height * percent);
	//				width = (int) (width * percent);
	//			}
	//			else {
	//				float percent = width / _maxWidth;
	//
	//				height = (int) (height * percent);
	//				width = (int) (width / percent);
	//			}
	//
	//			setMeasuredDimension(width, height);
	//		}
	//		else {
	//			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	//		}
	//
	//	}
}

