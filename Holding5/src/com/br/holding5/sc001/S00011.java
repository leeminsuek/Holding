package com.br.holding5.sc001;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData2;
import com.brainyx.holding5.R;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.BrUtilManager.setOnItemChoice;
import com.google.android.gcm.GCMRegistrar;

public class S00011 extends BaseActivity implements OnClickListener{

	private final String TAG = "Duks";
	
	CheckBox cb_check;
	
	EditText et_nick;
	
	EditText et_pwd;
	EditText et_pwd_re;
	EditText et_name;
	TextView tv_sex;
	TextView tv_birth_year;
	TextView tv_birth_month;
	TextView tv_birth_day;
	
	EditText et_phone;
	
	TextView tv_location;
	EditText et_school;
	
	CheckBox cb_agree;
	
	private boolean mCheckEmail;
	private boolean mCheckNickName;
	private int mSex = -1;
	
	private int mCurrentYear = -1;
	private int mCurrentMonth = -1;
	private int mCurrentDay = -1;
	private boolean mIsSelectBirth;
	
	private EditText et_id, et_domain;
	private TextView btn_domain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.setContentView(R.layout.s00011);
			
			cb_check = (CheckBox) findViewById(R.id.cb_check);
			
			et_id = (EditText)findViewById(R.id.et_id);
			et_id.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			
			TextView tv_middle = (TextView) findViewById(R.id.tv_middle);
			tv_middle.setText("@");
			
			et_domain = (EditText)findViewById(R.id.et_domain);
			et_domain.setVisibility(View.VISIBLE);
			et_domain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			
			btn_domain = (TextView) findViewById(R.id.btn_domain);
			btn_domain.setOnClickListener(this);
			et_domain.setVisibility(View.INVISIBLE);
			btn_domain.setVisibility(View.VISIBLE);
			
			et_nick = (EditText) findViewById(R.id.et_nick);
			et_nick.setFilters(new InputFilter[] { editFilter,new InputFilter.LengthFilter(10) });
			
			et_pwd = (EditText) findViewById(R.id.et_pwd);
			et_pwd_re = (EditText) findViewById(R.id.et_pwd_re);
			et_name = (EditText) findViewById(R.id.et_name);
			et_name.setFilters(new InputFilter[] { editFilter,new InputFilter.LengthFilter(10) });
			
			findViewById(R.id.iv_checkemail).setOnClickListener(this);
			findViewById(R.id.iv_checknick).setOnClickListener(this);
			tv_sex = (TextView) findViewById(R.id.tv_sex);
			tv_sex.setOnClickListener(this);
			findViewById(R.id.iv_sex).setOnClickListener(this);
			
			tv_birth_year = (TextView) findViewById(R.id.tv_birth_year);
			tv_birth_year.setOnClickListener(this);
			tv_birth_month = (TextView) findViewById(R.id.tv_birth_month);
			tv_birth_month.setOnClickListener(this);
			tv_birth_day = (TextView) findViewById(R.id.tv_birth_day);
			tv_birth_day.setOnClickListener(this);
			
			et_phone = (EditText) findViewById(R.id.et_phone);
			String phonenumber = BrUtilManager.getInstance().getPhoneNumber(this);
			et_phone.setText(phonenumber);
			et_phone.setEnabled(false);
			
			tv_location = (TextView) findViewById(R.id.tv_location);
			tv_location.setOnClickListener(this);
			findViewById(R.id.iv_location).setOnClickListener(this);
			et_school = (EditText) findViewById(R.id.et_school);
			
			cb_agree = (CheckBox) findViewById(R.id.cb_agree);
			
			TextView tv_agree1 = (TextView) findViewById(R.id.tv_agree1);
			tv_agree1.setText(Html.fromHtml(getString(R.string.sc001_register_bottom_desc1)));
			tv_agree1.setOnClickListener(this);
			
			TextView tv_agree2 = (TextView) findViewById(R.id.tv_agree2);
			tv_agree2.setText(Html.fromHtml(getString(R.string.sc001_register_bottom_desc2)));
			tv_agree2.setOnClickListener(this);
			
			findViewById(R.id.iv_join).setOnClickListener(this);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	private final String[] EMAIL_ADDRS = {"직접입력","cyworld.com",
			"chol.com",
			"daum.net",
			"dreamwiz.com",
			"empal.com",
			"empas.com",
			"freechal.com",
			"gmail.com",
			"hanmail.net",
			"hotmail.com",
			"korea.com",
			"lycos.co.kr",
			"naver.com",
			"nate.com",
			"netsgo.com",
			"yahoo.co.kr"};
	
	@Override
	public void onClick(View v) {
		try {
			switch(v.getId())
			{
				case R.id.btn_domain:
					{
						BrUtilManager.getInstance().ShowArrayDialog(this, getString(R.string.app_name), EMAIL_ADDRS, -1, new BrUtilManager.setOnItemChoice() {
							@Override
							public void setOnItemClick(int index) {
								if (index == 0)
								{
									btn_domain.setVisibility(View.GONE);
									
									et_domain.setVisibility(View.VISIBLE);
									et_domain.requestFocus();
								}
								else
								{						
									btn_domain.setText(EMAIL_ADDRS[index]);
								}
							}
						});
						/*AlertDialog.Builder d = new AlertDialog.Builder(this);
						
						String[] BgMenu = new String[EMAIL_ADDRS.length + 2];
						for (int i=0; i < EMAIL_ADDRS.length + 1; i++)
						{
							if (i == 0)
								BgMenu[i] = "직접입력";
//							else if (i == EMAIL_ADDRS.length + 1)
//								BgMenu[i] = getString(R.string.domain_none);
							else
								BgMenu[i] = EMAIL_ADDRS[i - 1];
						}
						
						d.setTitle("계정선택");
						
						d.setSingleChoiceItems(BgMenu, -1, new DialogInterface.OnClickListener(){
							public void onClick(DialogInterface dialog, int item){
								if (item == 0)
								{
									btn_domain.setVisibility(View.GONE);
									
									et_domain.setVisibility(View.VISIBLE);
									et_domain.requestFocus();
								}
								else
								{						
									btn_domain.setText(EMAIL_ADDRS[item-1]);
								}
								dialog.cancel();
							}
						});
						d.show();*/
					}
					break;
				case R.id.iv_checkemail:  //이메일 중복확인
					{	
						String id = et_id.getText().toString();
						String domain = et_domain.getText().toString();
						if (domain == null || domain.length() == 0)
							domain = btn_domain.getText().toString();
						
						if (id == null || id.length() == 0 || domain == null || domain.length() == 0)
						{
							Toast.makeText(this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						String email = id + "@" + domain;
						if (TextUtils.isEmpty(email))
						{
							Toast.makeText(this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
						{
							Toast.makeText(this, "이메일 주소가 잘못 되었습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						sendCheckEmail(email);
					}
					break;
				case R.id.iv_checknick:  //닉네임 중복확인
					{
						String nick = et_nick.getText().toString();
						if (TextUtils.isEmpty(nick))
						{
							Toast.makeText(this, "닉네임을 입력해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						if( nick.length() < 2 ) {
							BrUtilManager.getInstance().showToast(this, "닉네임은 2~10자의 영문 또는 한글만 입력가능합니다.");
							return;
						}
						sendCheckNickName(nick);
					}
					break;
				case R.id.tv_sex: //성별
				case R.id.iv_sex:
					{
						final String[] menu = new String[2];
						menu[0] = "남성";
						menu[1] = "여성";
						BrUtilManager.getInstance().ShowArrayDialog(this, getResources().getString(R.string.app_name), menu, mSex-1, new setOnItemChoice() {
							
							@Override
							public void setOnItemClick(int which) {
								if (which == 0)
									mSex = 1;
								else
									mSex = 2;
								
								tv_sex.setText(menu[which]);
							}
						});
//						AlertDialog.Builder d = new AlertDialog.Builder(this);
//					
//						d.setSingleChoiceItems(menu, -1, new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//							
//						if (which == 0)
//							mSex = 1;
//						else
//							mSex = 2;
//						
//						tv_sex.setText(menu[which]);
//								dialog.dismiss();
//							}
//						});
//						d.show();
					}
					break;
					
				case R.id.tv_birth_year: //생년월일
				case R.id.tv_birth_month:
				case R.id.tv_birth_day:
					{
						if (mCurrentYear == -1)
						{
							Calendar date = Calendar.getInstance();
							mCurrentYear = date.get(Calendar.YEAR);
							mCurrentMonth = date.get(Calendar.MONTH);
							mCurrentDay = date.get(Calendar.DAY_OF_MONTH);
						}
						DatePickerDialog dialog = new DatePickerDialog(this, mDatePickerCallback, mCurrentYear, mCurrentMonth, mCurrentDay);
						dialog.show();
					}
					break;
				case R.id.tv_location: //지역
				case R.id.iv_location:
					{
//						AlertDialog.Builder d = new AlertDialog.Builder(this);
						final String[] menu = {"서울","경기도","인천", "강원도", "충청남도", "충청북도", "대전", "경상남도", "경상북도", "대구", "세종특별자치시", 
								"울산", "전라남도", "전라북도", "광주", "부산","제주특별자치도","해외"};
						BrUtilManager.getInstance().ShowArrayDialog(this, getResources().getString(R.string.app_name), menu, -1, new setOnItemChoice() {
							@Override
							public void setOnItemClick(int which) {
								tv_location.setText(menu[which]);
							}
						});
//						d.setSingleChoiceItems(menu, -1, new DialogInterface.OnClickListener() {
//							
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								
//								tv_location.setText(menu[which]);
//								dialog.dismiss();
//							}
//						});
//						d.show();
					}
					break;
				case R.id.tv_agree1: //서비스 이용약관
					{
						Intent i = new Intent(this, S00111.class);
						i.putExtra("type", S00111.TYPE_SERVICE_TERM);
						startActivity(i);
					}
					break;
				case R.id.tv_agree2: //개인정보보호정책
					{
						Intent i = new Intent(this, S00111.class);
						i.putExtra("type", S00111.TYPE_PERSIONAL_TERM);
						startActivity(i);
					}
					break;
				case R.id.iv_join: //가입하기
					{
						int birthY = Integer.parseInt(tv_birth_year.getText().toString());
						int birthM = Integer.parseInt(tv_birth_month.getText().toString());
						int birthD = Integer.parseInt(tv_birth_day.getText().toString());
						java.util.Calendar cal1 = java.util.Calendar.getInstance();
						java.util.Calendar cal2 = java.util.Calendar.getInstance();
						int year = cal2.get ( cal2.YEAR );
						int month = cal2.get ( cal2.MONTH ) + 1 ;
						int date = cal2.get ( cal2.DATE ) ;
						
						cal1.set(birthY, birthM, birthD);
						cal2.set(year, month, date);
						
						if(cal1.after(cal2)) {
							BrUtilManager.getInstance().showToast(getBaseContext(), "이전 날짜를 선택해 주세요.");
							return;
						}
						
						if (!mCheckEmail)
						{
							Toast.makeText(this, "이메일 중복확인을 해주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						if (!mCheckNickName)
						{
							Toast.makeText(this, "닉네임 중복확인을 해주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						if (!cb_check.isChecked())
						{
							Toast.makeText(this, "생명 존중 및 선플 서약서에 서약을 해주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						if (!cb_agree.isChecked())
						{
							Toast.makeText(this, "서비스 이용 약관 및 개인정보보호정책에 동의 해주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						//mSex
						String id = et_id.getText().toString();
						String domain = et_domain.getText().toString();
						if (domain == null || domain.length() == 0)
							domain = btn_domain.getText().toString();
						
						if (id == null || id.length() == 0 || domain == null || domain.length() == 0)
						{
							Toast.makeText(this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						String userId = id + "@" + domain;
						if (TextUtils.isEmpty(userId))
						{
							Toast.makeText(this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						String pswd = et_pwd.getText().toString();
						String pswd_re = et_pwd_re.getText().toString();
						if (TextUtils.isEmpty(pswd) || TextUtils.isEmpty(pswd_re) || !pswd.equals(pswd_re))
						{
							Toast.makeText(this, "비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						if (pswd.length() < 6)
						{
							Toast.makeText(this, "비밀번호는 6자이상 입력해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						String nickName = et_nick.getText().toString();
						if (TextUtils.isEmpty(nickName))
						{
							Toast.makeText(this, "닉네임을 입력해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						String name = et_name.getText().toString();
						if (TextUtils.isEmpty(name))
						{
							Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						if( name.length() < 2 ) {
							BrUtilManager.getInstance().showToast(this, "이름은 2~10자의 영문 또는 한글만 입력가능합니다.");
							return;
						}
						
						
						if (mSex == -1)
						{
							Toast.makeText(this, "성별을 선택해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						
						if (!mIsSelectBirth)
						{
							Toast.makeText(this, "생년월일을 선택해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						

						//현재 년도, 월, 일
					
						
//						if(birthY > year || (birthY <= year && birthM > month)) {
//							BrUtilManager.getInstance().showToast(getBaseContext(), "이전 날짜를 선택해 주세요.");
//							return;
//						}
						
						String birthDay = tv_birth_year.getText().toString() + "-" + tv_birth_month.getText().toString() + "-" + tv_birth_day.getText().toString();
						String phone = et_phone.getText().toString();
						String location = tv_location.getText().toString();
						if (TextUtils.isEmpty(location))
						{
							Toast.makeText(this, "지역을 선택해 주세요.", Toast.LENGTH_SHORT).show();
							return;
						}
						String school = et_school.getText().toString();
						String pushKey = GCMRegistrar.getRegistrationId(this);
						this.sendRegister(userId, pswd, nickName, name, birthDay, phone, location, school, mSex, pushKey);
					}
					break;
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	private OnDateSetListener mDatePickerCallback = new OnDateSetListener(){

		@Override
		public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
			mIsSelectBirth = true;
			mCurrentYear = year;
			mCurrentMonth = monthOfYear;
			mCurrentDay = dayOfMonth;
			
			tv_birth_year.setText(String.valueOf(year));
			tv_birth_month.setText(String.valueOf(monthOfYear + 1));
			tv_birth_day.setText(String.valueOf(dayOfMonth));
		}
		
	};
	
	private void sendCheckEmail(final String userId)
	{
		try {
			final Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "member/checkUserId");
			params.put( "userId", userId );
			
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {

						@Override
						public void onResult2(JSONObject jsonObject)
								throws JSONException {
							
							Log.d(TAG, jsonObject.toString());
							
							if(jsonObject.optBoolean("result"))
							{
								mCheckEmail = true;
								runOnUiThread(new Runnable() {
									public void run() {
										dialog.dismiss();
										Toast.makeText(S00011.this, "사용할 수 있는 이메일 입니다.", Toast.LENGTH_LONG).show();
									}
								});
							}
							else
							{
								final String result_text = jsonObject.optString("result_text");
								runOnUiThread(new Runnable() {
									public void run() {
										if (!TextUtils.isEmpty(result_text))
											Toast.makeText(S00011.this, result_text, Toast.LENGTH_LONG).show();
										else
											Toast.makeText(S00011.this, "이메일이 중복되었습니다. 다시 입력해 주세요", Toast.LENGTH_LONG).show();
										dialog.dismiss();
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
	
	private void sendCheckNickName(final String nickName)
	{
		try {
		/*	final Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();
			*/
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "member/checkNickName");
			params.put( "nickName", nickName );
			
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {

						@Override
						public void onResult2(JSONObject jsonObject)
								throws JSONException {
							
							Log.d(TAG, jsonObject.toString());
							
							if(jsonObject.optBoolean("result"))
							{
								mCheckNickName = true;
								runOnUiThread(new Runnable() {
									public void run() {
										//dialog.dismiss();
										Toast.makeText(S00011.this, "사용할 수 있는 닉네임 입니다.", Toast.LENGTH_LONG).show();
									}
								});
							}
							else
							{
								final String result_text = jsonObject.optString("result_text");
								runOnUiThread(new Runnable() {
									public void run() {
										if (!TextUtils.isEmpty(result_text))
											Toast.makeText(S00011.this, result_text, Toast.LENGTH_LONG).show();
										else
											Toast.makeText(S00011.this, "닉네임이 중복되었습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
										//dialog.dismiss();
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
	
	private void sendRegister(final String userId, final String pswd, final String nickName, 
			final String name, final String birthDay, final String phone, final String location, 
			final String school, final int sex, final String pushKey)
	{
		try {
			final Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "member/insertMember");
			params.put( "userId", userId );
			params.put( "pswd", pswd );
			params.put( "nickName", nickName );
			params.put( "name", name );
			params.put( "birthDay", birthDay );
			params.put( "phone", phone );
			params.put( "location", location );
			params.put( "school", school );
			params.put( "sex", sex );
			params.put( "pushKey", pushKey );
			
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {

						@Override
						public void onResult2(JSONObject jsonObject)
								throws JSONException {
							
							Log.d(TAG, jsonObject.toString());
							
							if(jsonObject.optBoolean("result"))
							{
								runOnUiThread(new Runnable() {
									public void run() {
										dialog.dismiss();
										
										Toast.makeText(S00011.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
										
										String id = et_id.getText().toString();
										String domain = et_domain.getText().toString();
										if (domain == null || domain.length() == 0)
											domain = btn_domain.getText().toString();
										
										String userId = id + "@" + domain;
										
										Intent i = getIntent();
										i.putExtra("id", userId);
										setResult(RESULT_OK, i);
										finish();
									}
								});
							}
							else
							{
								final String result_text = jsonObject.optString("result_text");
								runOnUiThread(new Runnable() {
									public void run() {
										if (!TextUtils.isEmpty(result_text))
											Toast.makeText(S00011.this, result_text, Toast.LENGTH_LONG).show();
										else
											Toast.makeText(S00011.this, "회원가입 실패.", Toast.LENGTH_SHORT).show();
										dialog.dismiss();
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
	
	protected InputFilter editFilter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//            Pattern pattern = Pattern.compile("^[A-Z]+$");        // 영문대문자
            // Pattern pattern = Pattern.compile("^[a-zA-Z]+$");        // 영문
            // Pattern pattern = Pattern.compile("^[ㄱ-ㅎ가-힣]+$");        // 한글
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$");        // 영문,숫자,한글
            if(!pattern.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };
}
