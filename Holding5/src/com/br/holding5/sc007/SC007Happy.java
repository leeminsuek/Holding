package com.br.holding5.sc007;

import java.io.WriteAbortedException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData2;
import com.brainyx.holding5.R;

/**
 * [해피인신청] Activity.
 *
 */
public class SC007Happy extends BaseActivity{

	protected static final String TAG = SC007Happy.class.getSimpleName();
	
//	private static final String sURL = "http://192.168.0.11:8080";
//	private static final String sURL = "http://brainyx711.iptime.org:6384";
	private String sId;
	private static int sType = 1;
	private static String sSeq = "";
	private static String sBirth = "00000000";
	private static String sSchool = "";
	private static String sJob = "";
	private static String sCareer = "";
	private static String sPlan = "";
	private static String sPhone ="";
	private static String sGroup_nm = "";
	private static String sGroup_type = "";
	private static String sGroup_no = "";
	private static String sGroup_leader = "";
	
	/**
	 * KEY : RESULT CODE.
	 */
	static final String KEY_RC = "resultCd";
	static final String SUCCESS_CODE = "00";
	static final int EXIST_DATA = 11;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.s00122);
			sId = myApp.getUserInfo().getUserId();
			setLayout();
			setListener();
			initRequest();	
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	private void setListener() {
		try {
			ibBack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onBackPressed();
					
				}
			});
			
			rdoBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					
					switch (checkedId) {
					case R.id.hapyy_check_1:	//일반
						
						sType = 1;
						
						rdoBtn1.setButtonDrawable(getResources().getDrawable(R.drawable.rgst_check_box_dw));
						rdoBtn2.setButtonDrawable(getResources().getDrawable(R.drawable.rgst_check_box));
						
						text1.setInputType(InputType.TYPE_CLASS_DATETIME);
						text3.setInputType(InputType.TYPE_CLASS_TEXT);
						
						text1.setEnabled(false);
						text2.setEnabled(false);
						
						text1.setText(sBirth);
						text2.setText(sSchool);
						text3.setText(null);
						text4.setText(null);
						text5.setText(null);
						text6.setText(null);
						
						text1.setHint("생년월일");	//생년월일
						text2.setHint("학력");	//학력
						text3.setHint("직업");	//직업
						text4.setHint("이력");	//이력
						text5.setHint("해피인 활동계획");	//해피인활동계획
						text6.setHint("연락처");	//연락처
						break;
					case R.id.hapyy_check_2:	//그룹
						
						sType = 2;
						
						rdoBtn1.setButtonDrawable(getResources().getDrawable(R.drawable.rgst_check_box));
						rdoBtn2.setButtonDrawable(getResources().getDrawable(R.drawable.rgst_check_box_dw));
						
						text1.setInputType(InputType.TYPE_CLASS_TEXT);
						text3.setInputType(InputType.TYPE_CLASS_NUMBER);
						
						text1.setEnabled(true);
						text2.setEnabled(true);
						
						text1.setText(null);
						text2.setText(null);
						text3.setText(null);
						text4.setText(null);
						text5.setText(null);
						text6.setText(null);
						
						text1.setHint("단체 이름");	//단체이름
						text2.setHint("단체 성격");	//단체 성격
						text3.setHint("단체 회원 수");	//단체회원 수
						text4.setHint("단체 책임자 이름");	//단체책임자 이름
						text5.setHint("해피인 활동계획");	//해피인활동계획
						text6.setHint("연락처");	//연락처
						break;
					}
					
				}
			});
			
			submit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					//data check
					if(sType == 1)
					{
						ArrayList data = new ArrayList();
						data.add(text3.getText());
						data.add(text4.getText());
						data.add(text5.getText());
						data.add(text6.getText());
						
						int chk = dataNullchk(data);
						Log.d(TAG, "chk:"+chk);
						if(chk > 0)
						{
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(SC007Happy.this, "정보는 모두 입력하셔야 신청가능합니다.", 15).show();
									
								}
								
							});
						}
						else
						{
							//request
							requestPostList();
						}
					}
					else 
					{
						ArrayList data = new ArrayList();
						data.add(text1.getText());
						data.add(text2.getText());
						data.add(text3.getText());
						data.add(text4.getText());
						data.add(text5.getText());
						data.add(text6.getText());
						
						int chk = dataNullchk(data);
						Log.d(TAG, "chk:"+chk);
						if(chk > 0)
						{
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(SC007Happy.this, "정보는 모두 입력하셔야 신청가능합니다.", 15).show();
									
								}
								
							});
						}
						else
						{
							//request
							requestPostList();
						}
					}
				}
			});
			
			text1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			
			text2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			
			text3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			
			text4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			
			text5.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			
			text6.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
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
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "s00122");
			params.put("seq", sSeq);
			params.put("id", sId);
			params.put("type", sType);
			if(sType == 1)
			{
				params.put("birth", sBirth);
				params.put("school", sSchool);
				params.put("job", text3.getText());
				params.put("career", text4.getText());
				
			}
			else
			{
				params.put("group_nm", text1.getText());
				params.put("group_type", text2.getText());
				params.put("group_no", text3.getText());
				params.put("group_leader", text4.getText());
			}
			
			params.put("plan", text5.getText());
			params.put("phone", text6.getText());
			
			Log.d(TAG, params.toString());
			
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {
				
				@Override
				public void onResult3() {

				}

				@Override
				public void onResult2(JSONObject jsonObject)
						throws JSONException {
					
					Log.d(TAG, jsonObject.toString());
					
					if(SUCCESS_CODE.equals(jsonObject.get(KEY_RC).toString()))
					{
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(SC007Happy.this, "해피인 신청이 완료되었습니다.", 5).show();
								
							}
						});
						
					}
					else
					{
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(SC007Happy.this, "이미 해피인 신청이 완료되었습니다.", 5).show();
								
							}
						});
					}
				}
				
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
	private RadioGroup rdoBtn;
	private RadioButton rdoBtn1;
	private RadioButton rdoBtn2;
	private Button submit;
	private EditText text1;
	private EditText text2;
	private EditText text3;
	private EditText text4;
	private EditText text5;
	private EditText text6;
	
	/**
	 * init layout field.
	 */
	private void setLayout() {
		try {
			ibBack = (ImageButton) findViewById(R.id.ib_s00122_back);
			rdoBtn = (RadioGroup) findViewById(R.id.hapyy_check_radio);
			rdoBtn1 = (RadioButton) findViewById(R.id.hapyy_check_1);
			rdoBtn2 = (RadioButton) findViewById(R.id.hapyy_check_2);
			submit = (Button) findViewById(R.id.ib_s00122_submit);
			text1 = (EditText) findViewById(R.id.ib_s00122_text_1);
			text2 = (EditText) findViewById(R.id.ib_s00122_text_2);
			text3 = (EditText) findViewById(R.id.ib_s00122_text_3);
			text4 = (EditText) findViewById(R.id.ib_s00122_text_4);
			text5 = (EditText) findViewById(R.id.ib_s00122_text_5);
			text6 = (EditText) findViewById(R.id.ib_s00122_text_6);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	private void initRequest() {
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "s00122/init");
			params.put("id", sId);
			
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {
				
				@Override
				public void onResult3() {
					text1.setEnabled(false);
					text2.setEnabled(false);
					
					text1.setText(sBirth);
					text2.setText(sSchool);
				}

				@Override
				public void onResult2(JSONObject jsonObject)
						throws JSONException {
					
					Log.d(TAG, jsonObject.toString());
					
					if(SUCCESS_CODE.equals(jsonObject.get(KEY_RC).toString()))
					{
						Log.d(TAG, jsonObject.get("result").toString());
						JSONObject result = (JSONObject) jsonObject.get("result");
						sBirth = result.get("birth_day").toString();
						sSchool = result.get("school").toString();
						sSeq = result.get("seq").toString();
						Log.d("sBirth", "sBirth"+sBirth);
						Log.d("sSchool", "sSchool"+sSchool);
						
						
					}
					else
					{
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(SC007Happy.this, "해피인 정보요청 실패", 10).show();
							}
						});
					}
				}
				
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	
	}
	
	
	public int dataNullchk(ArrayList chk){
		int nullno = 0;
		for(int i=0; i<chk.size(); i++)
		{
			Log.d("dataNull", "chk:"+chk.get(i));
			if(chk.get(i).toString().length() <= 0)
			{
				nullno = nullno+1;
			}
		}
		
		return nullno;
	}
}
