package com.br.holding5.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.brainyx.holding5.R;
import com.brainyxlib.http.BrHttpManager;
import com.brainyxlib.util.HttpRequestor;

public class HttpUtil {
	
	private static HttpUtil instance;
	
	public static HttpUtil getInstance() {
		if (instance == null) {
			instance = new HttpUtil();
		}
		return instance;
	}
	
	private Dialog dialog;
	
	@SuppressWarnings("unchecked")
	public void requestHttp(final Activity activity, Map<String, Object> data, final OnAfterParsedData onAfter){
		AsyncTask<Map<String,Object>, Integer, Boolean> executer = new AsyncTask<Map<String,Object>, Integer, Boolean>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showProgress(activity);
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				showProgress(activity);
			}
			
			
			@Override
			protected Boolean doInBackground(Map<String, Object>... params) {
				boolean isfile = false;
				Map<String, Object> set = params[0];
				if (set == null) {
					return null;							
				}
				
				InputStream is = null;
				try {
					String url = (String) set.get(KEY_URL); 
					set.remove(KEY_URL);
					HttpRequestor http = BrHttpManager.getInstance().getHttpRequestor(url);
					
					if (!set.isEmpty()) {
						for (String key : set.keySet()) {
							if (!key.equals(KEY_URL)) {
								Object value = set.get(key);
								if (value instanceof String) {
									http.addParameter(key, value.toString());
								}else if (value instanceof File) {
									 isfile = true;
									http.addFile(key, (File)value);
								}else {
									if (value == null) {
										continue;
									}
									http.addParameter(key, value.toString());
								}
							}
						}
					}
					
					if(isfile){
						is = http.sendMultipartPost();
					}else{
						is = http.sendPost();
					}
					
					String[] resultvalue = ResultToJson(is);
					if(resultvalue != null){
						onAfter.onResult(Boolean.valueOf(resultvalue[0]), resultvalue[1]);	
					}
					
				} catch (Exception e){
					e.printStackTrace();
				}
				return null;
			}
			
		};
		executer.execute(data);
	}
	
	
	
	
	
	/**
     * 
     * @param is
     * @return
     */
    public String[] ResultToJson(InputStream is) {
    	String[] resultdata = new String[2];
		String data = null;
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;
				builder.append(line);
			}
			br.close();
			try {
				JSONObject obj = new JSONObject(builder.toString());
				if(obj.toString().length()>10){
					resultdata[0] = "true";	
				}
				resultdata[1] = obj.getString("data");
				/*resultdata[0] = obj.getString("result");
				resultdata[1] = obj.getString("message");*/
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultdata;
	}
	
	protected void showProgress(Activity ctx) {
		try {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				return;
			}
			
			dialog = new Dialog(ctx, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(ctx));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();	
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}

	public static final String KEY_URL = "url";
	
	public static final String KEY_DATA = "data";
	public static final String KEY_RESULT = "result";
	public static final String KEY_MSG = "message";
	/** ����¡�� ������ */
	public static final String KEY_PAGE = "paging";
	
	/**
	 * @author ���� ������ �ޱ�
	 */
	public interface OnAfterParsedData{
		void onResult(boolean result, String resultData);
	}
	
	public interface OnAfterParsedData2{
		void onResult2(JSONObject jsonObject) throws JSONException;
		void onResult3();
	}
	
	public class PageVO {
		int totalCnt 	= 0;
		int curPage 	= 0;
		public int getTotalCnt() {
			return totalCnt;
		}
		public void setTotalCnt(int totalCnt) {
			this.totalCnt = totalCnt;
		}
		public int getCurPage() {
			return curPage;
		}
		public void setCurPage(int curPage) {
			this.curPage = curPage;
		}
	}
	
	public JSONObject ResultToJson2(InputStream is) {
    	String[] resultdata = new String[3];
		String data = null;
		JSONObject obj = null;
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;
				builder.append(line);
			}
			br.close();
			try {
				obj = new JSONObject(builder.toString());
//				if(obj.toString().length()>10){
//					resultdata[0] = "true";	
//				}
//				resultdata[1] = obj.getString("data");
//				/*resultdata[0] = obj.getString("result");*/
//				resultdata[2] = obj.getString("message");
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public void requestHttp2(final Activity activity, Map<String, Object> data, final OnAfterParsedData2 onAfter){
		AsyncTask<Map<String,Object>, Integer, Boolean> executer = new AsyncTask<Map<String,Object>, Integer, Boolean>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showProgress(activity);
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				showProgress(activity);
				onAfter.onResult3();
			}
			
			
			@Override
			protected Boolean doInBackground(Map<String, Object>... params) {
				boolean isfile = false;
				Map<String, Object> set = params[0];
				if (set == null) {
					return null;							
				}
				
				InputStream is = null;
				try {
					String url = (String) set.get(KEY_URL); 
					set.remove(KEY_URL);
					HttpRequestor http = BrHttpManager.getInstance().getHttpRequestor(url);
					
					if (!set.isEmpty()) {
						for (String key : set.keySet()) {
							if (!key.equals(KEY_URL)) {
								Object value = set.get(key);
								if (value instanceof String) {
									http.addParameter(key, value.toString());
								}else if (value instanceof File) {
									 isfile = true;
									http.addFile(key, (File)value);
								}else {
									if (value == null) {
										continue;
									}
									http.addParameter(key, value.toString());
								}
							}
						}
					}
					
					if(isfile){
						is = http.sendMultipartPost();
					}else{
						is = http.sendPost();
					}
					onAfter.onResult2(ResultToJson2(is));	
//					String[] resultvalue = ResultToJson2(is);
//					if(resultvalue != null){
//						onAfter.onResult2(ResultToJson2(is));	
//					}
					
				} catch (Exception e){
					e.printStackTrace();
				}
				return null;
			}
			
		};
		executer.execute(data);
	}
}
