package com.br.holding5.sc010;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.sc010.SC010Adapter.ISC010OnClick;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData2;
import com.brainyx.holding5.R;

/**
 * [우체통] Activity.
 *       
 */
public class SC010 extends BaseActivity {

	private final String TAG = SC010.class.getSimpleName();
	
	/** 한번요청으로 불러오는 갯수. 요청 결과가 20개 미만으로 오게되면 '더 보기' 버튼 사라짐. **/
	private static final int MAX_COUNT_PER_PAGE = 20;
	
	String sId = "";

	/**
	 * KEY : RESULT CODE.
	 */
	static final String KEY_RC = "resultCd";
	static final String SUCCESS_CODE = "00";
	
	private ListView lv;
	private SC010Adapter adt;
	private ArrayList<Map<String, Object>> arrList;
	private int pageCount = 1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s00120);
			sId = myApp.getUserInfo().getUserId();
			setInit();
			setLayout();
			setListener();

			// request Posted data.
			requestPostList();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}

	
	private void setListener() {
		try {
			vFooter.findViewById(R.id.btn_s00120_show_more).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					requestPostList();
				}
			});
			ibBack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onBackPressed();
					
				}
			});	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		

	}

	
	/**
	 * JSON Data 요청
	 */
	private void requestPostList() {
		// start http request.
		try {
			removeFooterView();
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "s00120/" + pageCount);
			params.put("id", sId);
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {

						@Override
						public void onResult3() {
							
						}

						@Override
						public void onResult2(JSONObject jsonObject)
								throws JSONException {
							if(jsonObject == null)
							{
								Log.d(TAG, "Occur NETWORK ERROR !!");
								return;
								
							}
							
							//Log.d(TAG, "response = " + jsonObject.toString());
							/*
							 * jsonObject = new JSONObject(\"{
								\"resultCd":\"00\"
								\"resultSize\":\"20\"
								"result\":\[
									"nick_name":"원빈"
									"happy_photo":"ㅇㄴㅁㅇㄴㅁ"
									"msg_type":"1"
									"contents":"안녕하세요 원빈입니다. 가나다라마바사아자차카타파하\n\t\r\p123456789!@#$%^&*()<>?:\"{}\"
									"write_time":"192546850"
								\]	
							}
							");
							 */
							// success.
							if(SUCCESS_CODE.equals(jsonObject.get(KEY_RC).toString()))
							{
								final int iResultSize = Integer.valueOf(jsonObject.get("resultSize").toString());
								if(iResultSize == MAX_COUNT_PER_PAGE)
								{
									lv.addFooterView(vFooter);
									pageCount++;
								}
								else
								{
									// show more button invisible.
									removeFooterView();
									
								}
								parseResult(jsonObject);
								
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										setAdapter();
										
									}
								});
								
							}
							else	// fail.
							{
								// do something on fail state.
								
							}
								
						}
					});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}
	
	private void requestUpdatePost(final int seq, final int postSeq) {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.POST_READ_UPDATE);
			params.put("seq", postSeq);
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {
						@Override
						public void onResult3() {
							
						}

						@Override
						public void onResult2(JSONObject jsonObject)
								throws JSONException {
							if(jsonObject == null)
							{
								Log.d(TAG, "Occur NETWORK ERROR !!");
								return;
							}
							// success.
							if(SUCCESS_CODE.equals(jsonObject.get(KEY_RC).toString()))
							{
								boolean result = jsonObject.getBoolean("result");
								if(result) {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											Intent in = new Intent(SC010.this, SC010Detail.class);
											in.putExtra("seq", seq+"");
											startActivity(in);
										}
									});
								}
								else {
									
								}
							
								
							}
							else	// fail.
							{
							}
								
						}
					});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	
	/**
	 * remove Footer. 
	 */
	private void removeFooterView() {
		if(lv.getFooterViewsCount() > 0)
		{
			lv.removeFooterView(vFooter);
		}
	}
	
	
	private void parseResult(JSONObject jsonObject)
			throws JSONException {
		JSONArray arrJson = jsonObject.getJSONArray("result");
		final int MAX = arrJson.length();
		// parse list. 
		int counter = 0;
		JSONObject jTemp;
		Map<String, Object> map;
		Iterator<?> keys;
		
		while(counter < MAX)
		{
			map = new HashMap<String, Object>();
			jTemp = (JSONObject) arrJson.get(counter);
			keys = jTemp.keys();
			String key;
			while(keys.hasNext())
			{
				key = keys.next().toString();
				map.put(key, jTemp.get(key));
			}
			counter++;
			arrList.add(map);
			
		}
	}
	

	@Override
	protected void onPause() {
		super.onPause();
	}

	
	@Override
	protected void onResume() {
		super.onResume();
	}

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();           
	}

	
	private ImageButton ibBack;
	private View vFooter;
	
	
	/**
	 * init layout field.
	 */
	private void setLayout() {
		lv = (ListView) findViewById(R.id.lv_s00120);
		ibBack = (ImageButton) findViewById(R.id.ib_s00120_back);
		vFooter = getLayoutInflater().inflate(R.layout.s00120_show_more, null);
	}

	
	/**
	 * initialization.
	 */
	private void setInit() {
		arrList = new ArrayList<Map<String, Object>>();

	}

	
	/**
	 * Adatper 와 listview의 연결 및 데이터 갱신.
	 */
	private void setAdapter() {
		try {
			if (adt == null) {
				adt = new SC010Adapter(this, arrList, myApp.imageloder, myApp.options);
				adt.setOnItemClickListener(new ISC010OnClick() {
					
					@Override
					public void onItemClick(int pos) {
						
						
						Map<String, Object> map = arrList.get(pos);
						final String seq = map.get("seq").toString();
						final String postSeq = map.get("post_seq").toString();
						
						requestUpdatePost(Integer.parseInt(seq), Integer.parseInt(postSeq));
						
//						Intent in = new Intent(SC010.this, SC010Detail.class);
//						in.putExtra("seq", seq);
//						startActivity(in);
						
					}
				});
				lv.setAdapter(adt);
				
			} else if (arrList.size() != lv.getAdapter().getCount()) {
				adt.setList(arrList);

			}
			adt.notifyDataSetChanged();
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		

	}
}
