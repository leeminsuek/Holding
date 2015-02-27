package com.br.holding5;

import java.io.File;
import java.util.HashMap;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.br.holding5.base.BaseActivity;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData;
import com.brainyxlib.AWSFileUploader;

public class Example extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		upload();
	}
	
	/**
	 * http 통신 예제
	 */
	public void ExHttp() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, "컨트롤러명");
			params.put("key", "value");
			params.put("파일key", new File("파일경로"));
			HttpUtil.getInstance().requestHttp(this, params,
					new OnAfterParsedData() {
						@Override
						public void onResult(boolean result, String resultData) {
							try {
								if(result){ /// 종료하면 여기로 탑니다
									
									
								}else{}	
							} catch (Exception e) {
							}finally{ /// 끝나면 핸들러로 화면셋팅
								
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 아마존 파일서버 업로드 예제
	 */
	public void upload(){
				AWSFileUploader uploader = new AWSFileUploader(this);
				uploader.setAwsAccesskey("AKIAJ6EUVTNPNFL4WVSQ"); //accesskey
				uploader.setAwsSecretKey("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"); //secretkey
				uploader.setAWSBuckekName("brainyx1");//버킷이름
				uploader.setFilename("ImageTemp.jpg");//파일명
				uploader.setFilePath(Environment.getExternalStorageDirectory() + "/");//실제파일경로
				uploader.setListener(new AWSFileUploader.OnResultListener() { // 업로드완료후 콜백
					@Override
					public boolean setOnResultListener(boolean result) {
						Log.e("", "");
						return false;
					}
				});
				uploader.execute();
	}
}
