package com.br.holding5;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;

import com.br.holding5.ms.UserInfo;
import com.br.holding5.sc014.SC00117;
import com.brainyx.holding5.R;
import com.brainyxlib.BrImageCacheManager;
import com.brainyxlib.SharedManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Holding5 extends Application{

	private Typeface typeface;
	private Typeface typefaceBold;
	public UserInfo mUserInfo;
	public DisplayImageOptions options ;
	public ImageLoader imageloder;
	public ArrayList<String> tempResource = new ArrayList<String>();
	public ArrayList<Activity> mActivityStack = new ArrayList<Activity>();
	@Override
	public void onCreate() {
		try {
			super.onCreate();
			WriteFileLog.initialize(this);
			loadTypeface();
		
			options = BrImageCacheManager.getInstance().ImageLoaderInit(getApplicationContext(), R.drawable.title_logo_img , R.drawable.title_logo_img);
			mUserInfo = new UserInfo();
//			mUserInfo.setUserId("aad@dreamwiz.com");
			imageloder = ImageLoader.getInstance();	
			
			
		} catch (Exception e) {
			// TODO: handle exception
			WriteFileLog.writeException(e);
		}
		
	}

	public String getFileDir_Ex() {
		String result = "";
		try {
			int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
			if (sdkVersion < 8) {
				File extSt = Environment.getExternalStorageDirectory();
				result = extSt.getAbsolutePath() + "/Android" + "/data"+ "/com.holding5";
			} else {
				result = getExternalFilesDir(null).getAbsolutePath();
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return result;
	}
	
	
	
	public void loadTypeface() {
		try {
			if (typeface == null)
				typeface = Typeface.createFromAsset(getAssets(),"NanumBarunGothic.otf");

			if (typefaceBold == null)
				typefaceBold = Typeface.createFromAsset(getAssets(),"NanumBarunGothicBold.otf");	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}

	public Typeface getFontNormal() {
		return typeface;
	}
	
	public String getTempResource(String tempName) {
		Random random = new Random();
		while(true) {
			int a = random.nextInt(tempResource.size()-1);
			
			if(!tempName.equals(tempResource.get(a)))
				return tempResource.get(a); 
		}
		
	}
	
	public String getTempResource() {
		Random random = new Random();
			int a = random.nextInt(tempResource.size()-1);
				return tempResource.get(a); 
		
	}

	/**
	 * 
	 * @return
	 */
	public Typeface getFontBold() {
		return typefaceBold;
	}
	
	public UserInfo getUserInfo() {
		return mUserInfo;
	}

}
