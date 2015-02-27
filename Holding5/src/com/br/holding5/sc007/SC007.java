package com.br.holding5.sc007;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData2;
import com.brainyx.holding5.R;
import com.brainyxlib.BrDateManager;

/**
 * [이벤트입력] Activity.
 *
 */
public class SC007 extends BaseActivity{

	protected static final String TAG = SC007.class.getSimpleName();

	//	private static final String sURL = "http://192.168.131.111:8080";
	//	private static final String sURL = "http://brainyx711.iptime.org:6384";
	private static String sId = "";

	private static int birthKeySet = 0;
	private static int examKey = 0;

	private static String resYear;
	private static int birthYearKey;
	private static String resMonth;
	private static String resDay;

	private static int yyyy = currentDate("yyyy");
	private static int mm = currentDate("MM");
	private static int dd = currentDate("dd");

	private boolean mRequestYn = false;

	/**
	 * KEY : RESULT CODE.
	 */
	static final String KEY_RC = "resultCd";
	static final String SUCCESS_CODE = "00";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s00119);
			sId = myApp.getUserInfo().getUserId();
			requestDays();
			setLayout();
			setListener();
			initRequest();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	private void requestDays() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(HttpUtil.KEY_URL, Global.EVENT_DAYS);
		params.put("id", sId);

		HttpUtil.getInstance().requestHttp2(this, params,
				new OnAfterParsedData2() {
			JSONObject object; 
			String testDay1="";
			String testDay2="";
			String testDay3="";
			String testDay4="";
					@Override
					public void onResult2(JSONObject jsonObject)
							throws JSONException {
						// TODO Auto-generated method stub
						Log.e("", "");
						JSONObject object = (JSONObject) jsonObject.get("result"); 
						testDay1 = object.getString("testDay1");
						testDay2 = object.getString("testDay2");
						testDay3 = object.getString("testDay3");
						testDay4 = object.getString("testDay4");
						
						testDay1 = formaterDate(testDay1);
						testDay2 = formaterDate(testDay2);
						testDay3 = formaterDate(testDay3);
						testDay4 = formaterDate(testDay4);
						
						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
						
								midterm1exam.setText(testDay1);
								midterm2exam.setText(testDay2);
								final1exam.setText(testDay3);
								final2exam.setText(testDay4);
							}
						});
					}

					@Override
					public void onResult3() {
						// TODO Auto-generated method stub
						Log.e("", "");
					}
		});

	}

	private void setListener() {
		try {

			//			final DatePickerDialog dialog = new DatePickerDialog(this, datepicker, yyyy, mm-1, dd);

			ibBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onBackPressed();

				}
			});

			iPm.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					//					birthKeySet = birthKeySet+1;
					//					Log.d(TAG, "iPm birthKeySet:"+birthKeySet);
					//					if(birthKeySet >4){
					//						String year = iYear.getSelectedItem().toString();
					//						String month = iMonth.getSelectedItem().toString();
					//						String day = iDay.getSelectedItem().toString();
					//						String date = dateReset(year,month,day);
					//						HashMap<String, Object> params = param(date);
					//					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});



			midterm1exam.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					examKey = 1;
					showDatePicker();
					//					dialog.show();
				}
			});

			midterm2exam.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					examKey = 2;
					showDatePicker();
					//					dialog.show();
				}
			});

			final1exam.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					examKey = 3;
					showDatePicker();
					//					dialog.show();
				}
			});

			final2exam.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					examKey = 4;
					showDatePicker();
					//					dialog.show();
				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}

	/**
	 * JSON Data 요청
	 */
	private void requestPostList(HashMap<String, Object> params) {
		try {
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {

				@Override
				public void onResult2(JSONObject jsonObject)
						throws JSONException {

					Log.d(TAG, jsonObject.toString());

					if(SUCCESS_CODE.equals(jsonObject.get(KEY_RC).toString()))
					{
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(SC007.this, "수정이 완료되었습니다.", 10).show();

							}
						});
					}
					else
					{
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(SC007.this, "업데이트 실패", 10).show();

							}
						});
					}


				}

				@Override
				public void onResult3() {

				}

			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}

	}

	/**
	 * JSON Data Param
	 */
	private HashMap<String, Object> param(String day) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", sId);
		params.put("day", day);

		Log.d(TAG, params.toString());

		return params;
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
		birthKeySet = 0;
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initRequest() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "/s00119");
			params.put("id", sId);

			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {

				@Override
				public void onResult3() {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							birthKeySet = birthKeySet+2;
							iYear.setSelection(birthYearKey);
							iMonth.setSelection(Integer.valueOf(resMonth)-1);
							iDay.setSelection(Integer.valueOf(resDay)-1);
							
							iYear.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0, View arg1,
										int arg2, long arg3) {
									birthKeySet = birthKeySet+1;
									Log.d(TAG, "iYear birthKeySet:"+birthKeySet);
									if(birthKeySet >5){
											String year = iYear.getSelectedItem().toString();
											String month = iMonth.getSelectedItem().toString();
											String day = iDay.getSelectedItem().toString();
											String date = dateReset(year,month,day);
											HashMap<String, Object> params = param(date);
											params.put(HttpUtil.KEY_URL, Global.host + "s00119/birth");
											requestPostList(params);
									}
									else
									{
										arg0.setSelection(birthYearKey);
									}
								}

								@Override
								public void onNothingSelected(AdapterView<?> arg0) {

								}
							});

							iMonth.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0, View arg1,
										int arg2, long arg3) {
									birthKeySet = birthKeySet+1;
									Log.d(TAG, "iMonth birthKeySet:"+birthKeySet);
									if(birthKeySet >5){
											String year = iYear.getSelectedItem().toString();
											String month = iMonth.getSelectedItem().toString();
											String day = iDay.getSelectedItem().toString();
											String date = dateReset(year,month,day);
											HashMap<String, Object> params = param(date);
											params.put(HttpUtil.KEY_URL, Global.host + "s00119/birth");
											requestPostList(params);	
									}
									else
									{
										arg0.setSelection(Integer.valueOf(resMonth)-1);
									}
								}

								@Override
								public void onNothingSelected(AdapterView<?> arg0) {

								}
							});

							iDay.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0, View arg1,
										int arg2, long arg3) {
									birthKeySet = birthKeySet+1;
									Log.d(TAG, "iDay birthKeySet:"+birthKeySet);
									if(birthKeySet >5){
											String year = iYear.getSelectedItem().toString();
											String month = iMonth.getSelectedItem().toString();
											String day = iDay.getSelectedItem().toString();
											String date = dateReset(year,month,day);
											HashMap<String, Object> params = param(date);
											params.put(HttpUtil.KEY_URL, Global.host + "s00119/birth");
												requestPostList(params);	
									}
									else
									{
										arg0.setSelection(Integer.valueOf(resDay)-1);
									}
								}

								@Override
								public void onNothingSelected(AdapterView<?> arg0) {

								}
							});
						}
					});
				}

				@Override
				public void onResult2(JSONObject jsonObject)
						throws JSONException {

					Log.d(TAG, jsonObject.toString());

					if(SUCCESS_CODE.equals(jsonObject.get(KEY_RC).toString()))
					{
						Log.d(TAG, jsonObject.get("result").toString());
						JSONObject result = (JSONObject) jsonObject.get("result");
						resYear	= result.get("yearY").toString();;
						resMonth	= result.get("monthM").toString();;
						resDay	= result.get("dayD").toString();;


						runOnUiThread(new Runnable() {
							public void run() {
								setBirthItem(iYear,	 makeY());
								setBirthItem(iMonth, makeM());
								setBirthItem(iDay, 	 makeD());
								setBirthItem(iPm, 	 makeP());
							}
						});
						
					}
					else
					{
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(SC007.this, "정보요청 실패", 10).show();
							}
						});

						setBirthItem(iYear,	 makeY());
						setBirthItem(iMonth, makeM());
						setBirthItem(iDay, 	 makeD());
						setBirthItem(iPm, 	 makeP());
						
					}


				}

			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}

	/**
	 * Spiner setting
	 */
	private void setBirthItem(Spinner data, ArrayList<String> data2) {
		try {
			ArrayList<String> Item = new ArrayList<String>(data2);
			ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, Item);
			Adapter.notifyDataSetChanged();

			Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			data.setAdapter(Adapter);			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}

	private ImageButton ibBack;
	private Spinner iYear;
	private Spinner iMonth;
	private Spinner iDay;
	private Spinner iPm;
	private TextView midterm1exam;
	private TextView midterm2exam;
	private TextView final1exam;
	private TextView final2exam;
	//	private OnDateSetListener datepicker;



	/**
	 * init layout field.
	 */
	private void setLayout() {
		try {
			ibBack = (ImageButton) findViewById(R.id.ib_s00119_back);
			iYear = (Spinner) findViewById(R.id.sp_s00119_yyyy);
			iMonth = (Spinner) findViewById(R.id.sp_s00119_mm);
			iDay = (Spinner) findViewById(R.id.sp_s00119_dd);
			iPm = (Spinner) findViewById(R.id.sp_s00119_pm);

			iYear.setPrompt("해당 년도를 입력해주세요.");
			iMonth.setPrompt("해당 월을 입력해주세요.");
			iDay.setPrompt("해당 일을 입력해주세요.");
			iPm.setPrompt("양력,음력을 입력해주세요.");

			//			setBirthItem(iYear,	 makeY());
			//			setBirthItem(iMonth, makeM());
			//			setBirthItem(iDay, 	 makeD());
			//			setBirthItem(iPm, 	 makeP());

			midterm1exam = (TextView) findViewById(R.id.tv_s00119_midterm1);
			midterm2exam = (TextView) findViewById(R.id.tv_s00119_midterm2);
			final1exam = (TextView) findViewById(R.id.tv_s00119_final1);
			final2exam = (TextView) findViewById(R.id.tv_s00119_final2);

			String dateExam = currentDate("yyyy")+"년"+currentDate("MM")+"월"+currentDate("dd")+"일";

//			midterm1exam.setText(dateExam);
//			midterm2exam.setText(dateExam);
//			final1exam.setText(dateExam);
//			final2exam.setText(dateExam);
			//			datepicker = new OnDateSetListener() {		
			//				@Override
			//				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			//					
			//					int pMm = mm;
			//					String pMms;
			//					if(pMm < 10)
			//					{
			//						pMms = "0"+pMm;
			//					}else{
			//						pMms = pMm+"";
			//					}
			//					int pDd = dd;
			//					String pDds;
			//					if(pDd < 10)
			//					{
			//						pDds = "0"+pDd;
			//					}else{
			//						pDds = pDd+"";
			//					}
			//					
			//					int sMm = monthOfYear+1;
			//					String sMms;
			//					if(sMm < 10)
			//					{
			//						sMms = "0"+sMm;
			//					}else{
			//						sMms = sMm+"";
			//					}
			//					int sDd = dayOfMonth;
			//					String sDds;
			//					if(sDd < 10)
			//					{
			//						sDds = "0"+sDd;
			//					}else{
			//						sDds = sDd+"";
			//					}
			//					
			//					String today = yyyy+""+pMms+""+pDds;
			//					String setday = year+""+sMms+""+sDds;
			//					
			//					Log.d(TAG, setday+"/"+today);
			//					int setToday = Integer.valueOf(today);
			//					int setSetday = Integer.valueOf(setday);
			//					Log.d(TAG, setSetday+"/"+setToday);
			//					Log.d(TAG, "examKey:"+examKey);
			//					if(setSetday < setToday)
			//					{
			//						Toast.makeText(getApplicationContext(), "과거값은 입력하실수 없습니다.", Toast.LENGTH_SHORT).show();
			//					}
			//					else
			//					{
			//						if(examKey == 1)
			//						{
			//							String yyyy = year+"";
			//							String mm = sMms+"";
			//							String dd = sDds+"";
			//							String resetDate = yyyy+mm+dd;
			//							HashMap<String,Object> param = param(resetDate);
			//							param.put(HttpUtil.KEY_URL, Global.host + "s00119/test1");
			//							requestPostList(param);
			//							String dateExam = yyyy+"년"+mm+"월"+dd+"일";
			//							midterm1exam.setText(dateExam);
			//						}
			//						else if(examKey == 2)
			//						{
			//							String yyyy = year+"";
			//							String mm = sMms+"";
			//							String dd = sDds+"";
			//							String resetDate = yyyy+mm+dd;
			//							HashMap<String,Object> param = param(resetDate);
			//							param.put(HttpUtil.KEY_URL, Global.host + "s00119/test2");
			//							requestPostList(param);
			//							String dateExam = yyyy+"년"+mm+"월"+dd+"일";
			//							midterm2exam.setText(dateExam);
			//						}
			//						else if(examKey == 3)
			//						{
			//							String yyyy = year+"";
			//							String mm = sMms+"";
			//							String dd = sDds+"";
			//							String resetDate = yyyy+mm+dd;
			//							HashMap<String,Object> param = param(resetDate);
			//							param.put(HttpUtil.KEY_URL, Global.host + "s00119/test3");
			//							requestPostList(param);
			//							String dateExam = yyyy+"년"+mm+"월"+dd+"일";
			//							final1exam.setText(dateExam);
			//						}
			//						else if(examKey == 4)
			//						{
			//							String yyyy = year+"";
			//							String mm = sMms+"";
			//							String dd = sDds+"";
			//							String resetDate = yyyy+mm+dd;
			//							HashMap<String,Object> param = param(resetDate);
			//							param.put(HttpUtil.KEY_URL, Global.host + "s00119/test4");
			//							requestPostList(param);
			//							String dateExam = yyyy+"년"+mm+"월"+dd+"일";
			//							final2exam.setText(dateExam);
			//						}
			//						else
			//						{
			//							Toast.makeText(getApplicationContext(), year + "년" + (monthOfYear+1) + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
			//						}
			//					}
			//					
			//				}
			//			};
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}


	}


	private void showDatePicker() {
		BrDateManager.getInstance().CallYearMonthPicker(this, new BrDateManager.onDateSelected() {
			@Override
			public void onDateSelectedListner(String year, String month, String day) {
				//				int y = Integer.parseInt(year);
				//				int m = Integer.parseInt(month);
				//				int d = Integer.parseInt(day);

				int pMm = mm;
				String pMms;
				if(pMm < 10)
				{
					pMms = "0"+pMm;
				}else{
					pMms = pMm+"";
				}
				int pDd = dd;
				String pDds;
				if(pDd < 10)
				{
					pDds = "0"+pDd;
				}else{
					pDds = pDd+"";
				}

				//				int sMm = m+1;
				String sMms = month;
				//				if(sMm < 10)
				//				{
				//					sMms = "0"+sMm;
				//				}else{
				//					sMms = sMm+"";
				//				}
				//				int sDd = d;
				String sDds = day;
				//				if(sDd < 10)
				//				{
				//					sDds = "0"+sDd;
				//				}else{
				//					sDds = sDd+"";
				//				}

				String today = yyyy+""+pMms+""+pDds;
				String setday = year+""+sMms+""+sDds;

				Log.d(TAG, setday+"/"+today);
				int setToday = Integer.valueOf(today);
				int setSetday = Integer.valueOf(setday);
				Log.d(TAG, setSetday+"/"+setToday);
				Log.d(TAG, "examKey:"+examKey);
				if(setSetday < setToday)
				{
					Toast.makeText(getApplicationContext(), "과거값은 입력하실수 없습니다.", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(examKey == 1)
					{
						String yyyy = year+"";
						String mm = sMms+"";
						String dd = sDds+"";
						String resetDate = yyyy+mm+dd;
						HashMap<String,Object> param = param(resetDate);
						param.put(HttpUtil.KEY_URL, Global.host + "s00119/test1");
						requestPostList(param);
						String dateExam = yyyy+"년"+mm+"월"+dd+"일";
						midterm1exam.setText(dateExam);
					}
					else if(examKey == 2)
					{
						String yyyy = year+"";
						String mm = sMms+"";
						String dd = sDds+"";
						String resetDate = yyyy+mm+dd;
						HashMap<String,Object> param = param(resetDate);
						param.put(HttpUtil.KEY_URL, Global.host + "s00119/test2");
						requestPostList(param);
						String dateExam = yyyy+"년"+mm+"월"+dd+"일";
						midterm2exam.setText(dateExam);
					}
					else if(examKey == 3)
					{
						String yyyy = year+"";
						String mm = sMms+"";
						String dd = sDds+"";
						String resetDate = yyyy+mm+dd;
						HashMap<String,Object> param = param(resetDate);
						param.put(HttpUtil.KEY_URL, Global.host + "s00119/test3");
						requestPostList(param);
						String dateExam = yyyy+"년"+mm+"월"+dd+"일";
						final1exam.setText(dateExam);
					}
					else if(examKey == 4)
					{
						String yyyy = year+"";
						String mm = sMms+"";
						String dd = sDds+"";
						String resetDate = yyyy+mm+dd;
						HashMap<String,Object> param = param(resetDate);
						param.put(HttpUtil.KEY_URL, Global.host + "s00119/test4");
						requestPostList(param);
						String dateExam = yyyy+"년"+mm+"월"+dd+"일";
						final2exam.setText(dateExam);
					}
					else
					{
						Toast.makeText(getApplicationContext(), year + "년" + month + "월" + day +"일", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	private ArrayList<String> makeY() {		
		ArrayList<String> yItem = new ArrayList<String>();

		for(int i=0; i<100; i++){
			yItem.add(yyyy-i+"년");
			int setData = yyyy-i;
			int chk = Integer.valueOf(resYear);
			if(chk == setData)
			{
				birthYearKey = i;
			}
		}
		return yItem;
	}

	private ArrayList<String> makeM() {
		ArrayList<String> mItem = new ArrayList<String>();
		for(int i=1; i<=12; i++){
			mItem.add(i+"월");
		}
		return mItem;
	}

	private ArrayList<String> makeD() {
		ArrayList<String> dItem = new ArrayList<String>();
		for(int i=1; i<=31; i++){
			dItem.add(i+"일");
		}
		return dItem;
	}

	private ArrayList<String> makeP() {
		ArrayList<String> pItem = new ArrayList<String>(Arrays.asList("양력","음력"));
		return pItem;
	}


	private String dateReset(String year, String month, String day){
		String yyyy = year.replace("년", "");
		int mmInt = Integer.valueOf(month.replace("월", ""));
		String mm;
		if(mmInt<10)
		{
			mm = "0"+mmInt;
		}
		else
		{
			mm = mmInt+"";
		}
		int ddint = Integer.valueOf(day.replace("일", ""));
		String dd;
		if(ddint<10)
		{
			dd = "0"+ddint;
		}
		else
		{
			dd = ddint+"";
		}

		String date = yyyy+""+mm+""+dd;
		return date;
	}

	private String formaterDate(String date) {
		if(date.length() > 10) {
			date = date.substring(0,10);
			date = date.replaceFirst("-", "년");
			date = date.replaceFirst("-", "월");
			date = date.replaceFirst("-", "일");
		}
		return date;
	}
	
	private static int currentDate(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date currentDate = new Date();
		String resetDate = df.format(currentDate);
		int reset = Integer.valueOf(resetDate);
		Log.d(TAG, reset+"");
		return reset;
	}
}
