package com.br.holding5.ms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.br.holding5.Global;
import com.br.holding5.sc014.SC00117;
import com.brainyx.holding5.R;
import com.brainyxlib.SharedManager;


public class Common {


	private static final String KIND_1 = "가벼운 이야기";
	private static final String KIND_2 = "왕따, 폭력문제";
	private static final String KIND_3 = "성적, 학업문제";
	private static final String KIND_4 = "친구, 이성문제";
	private static final String KIND_5 = "부모님과 갈등";
	private static final String KIND_6 = "선생님과 갈등";
	private static final String KIND_7 = "가정형편, 경제";
	private static final String KIND_8 = "외모문제";
	private static final String KIND_9 = "기타문제";
	private static final String KIND_10 = "극복수기";
	private static final String SERIOUS_LEVEL_10 = "생명SOS";

	public final static int SEC 	= 60;
	public final static int MIN 	= 60;
	public final static int HOUR 	= 24;
	public final static int DAY 	= 7;
	public final static int MONTH = 12;

	public interface getProfile {
		public int getItemSex();
		public String getItemBirth();
		public int getItemSeriousLevel();
		public int getItemMemverLevel();
	}

	public static Bitmap getProfileImage(Context con, int memLvl, int sex, String birthDay) {
		Bitmap bitmap = null;
		Drawable drawable = null;

		if(memLvl == 1) {
			int age = getAgeFromBirthday(birthDay);

			if(sex == 1) {
				if(age <= 12) {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_01_m);
				}
				else if(age <= 15) {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_02_m);
				}
				else if(age <= 18) {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_03_m);
				}
				else if(age <= 39) {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_04_m);
				}
				else {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_05_m);
				}
			}
			else {
				if(age <= 12) {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_01_g);
				}
				else if(age <= 15) {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_02_g);
				}
				else if(age <= 18) {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_03_g);
				}
				else if(age <= 39) {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_04_g);
				}
				else {
					drawable = con.getResources().getDrawable(R.drawable.prf_img_05_g);
				}
			}
		}
		if(drawable == null) drawable = con.getResources().getDrawable(R.drawable.prf_img_05_g);
		bitmap = ((BitmapDrawable)drawable).getBitmap();
		return bitmap;
	}

	public static void setTagName(String content, TextView contentTxt) {
		if(content.indexOf("@") != -1 && content.indexOf("::") != -1 && content.indexOf("@") < content.indexOf("::")) {
			//			String tag = content.replace(content.substring(content.indexOf("@"), content.indexOf("::")+1), "테스트입니다.");
			//			contentTxt.setText(content);
			contentTxt.setText("");
			int start = content.indexOf("@");
			int end = content.indexOf("::");
			content = content.replace("@", " ");
			content = content.replace("::", " ");
			SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder(content);
			mSpannableStringBuilder.setSpan(new ForegroundColorSpan(0xFF04478f), start, end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			contentTxt.append(mSpannableStringBuilder);


			//			contentTxt.setText(tag);	
		}
		else {
			contentTxt.setText(content);
		}
	}

	public static String getProfileImage(Context con, getProfile item) {

		//		Bitmap bitmap = null;
		//		Drawable drawable = null;
		String resultString = "drawable://";
		int seriousLevel = item.getItemSeriousLevel();
		if(seriousLevel >= 10) {
			//			drawable = con.getResources().getDrawable(R.drawable.prf_img_sos);
			resultString += String.valueOf(R.drawable.prf_img_sos);
		}
		else if(item.getItemMemverLevel() == 1) {
			int sex = item.getItemSex();
			int age = getAgeFromBirthday(item.getItemBirth());

			if(sex == 1) {
				if(age <= 12) {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_01_m);
					resultString += String.valueOf(R.drawable.prf_img_01_m);
				}
				else if(age <= 15) {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_02_m);
					resultString += String.valueOf(R.drawable.prf_img_02_m);
				}
				else if(age <= 18) {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_03_m);
					resultString +=String.valueOf( R.drawable.prf_img_03_m);
				}
				else if(age <= 39) {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_04_m);
					resultString += String.valueOf(R.drawable.prf_img_04_m);
				}
				else {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_05_m);
					resultString += String.valueOf(R.drawable.prf_img_05_m);
				}
			}
			else {
				if(age <= 12) {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_01_g);
					resultString += String.valueOf(R.drawable.prf_img_01_g);
				}
				else if(age <= 15) {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_02_g);
					resultString += String.valueOf(R.drawable.prf_img_02_g);
				}
				else if(age <= 18) {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_03_g);
					resultString += String.valueOf(R.drawable.prf_img_03_g);
				}
				else if(age <= 39) {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_04_g);
					resultString += String.valueOf(R.drawable.prf_img_04_g);
				}
				else {
					//					drawable = con.getResources().getDrawable(R.drawable.prf_img_05_g);
					resultString += String.valueOf(R.drawable.prf_img_05_g);
				}
			}
		}
		else if(item.getItemMemverLevel() == 2) {

		}
		else if(item.getItemMemverLevel() == 3) {

		}
		else if(item.getItemMemverLevel() == 4) {

		}
		else if(item.getItemMemverLevel() == 5) {

		}
		//		if(drawable == null) drawable = con.getResources().getDrawable(R.drawable.prf_img_05_g);
		//		bitmap = ((BitmapDrawable)drawable).getBitmap();

		return resultString;
	}

	public static int getAgeFromBirthday(String birthday) {
		try {
			birthday = birthday.substring(0,10);
			DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date tempDate = sdFormat.parse(birthday);

			Calendar birth = new GregorianCalendar();
			Calendar today = new GregorianCalendar();

			birth.setTime(tempDate);
			today.setTime(new Date());

			int factor = 0;
			if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
				factor = -1;
			}
			return today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + factor;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getCategoryKindString( int kind ) {
		String resultString = "";
		switch (kind) {
		case 1:
			resultString = KIND_1;
			break;
		case 2:
			resultString = KIND_2;
			break;
		case 3:
			resultString = KIND_3;
			break;
		case 4:
			resultString = KIND_4;
			break;
		case 5:
			resultString = KIND_5;
			break;
		case 6:
			resultString = KIND_6;
			break;
		case 7:
			resultString = KIND_7;
			break;
		case 8:
			resultString = KIND_8;
			break;
		case 9:
			resultString = KIND_9;
			break;
		case 10:
			resultString = KIND_10;
			break;
		}
		return resultString;
	}
	public static String CreateDataWithCheck(String dataString)
	{
		//		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
		//				"yyyy-MM-dd HH:mm:ss.S");
		java.text.SimpleDateFormat format= null;
		if(dataString.length() != 21) {
			format = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		}
		else {
			format = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);
		}
		java.util.Date date = null;
		try {
			date = format.parse(dataString);


			long curTime = System.currentTimeMillis();
			long regTime = date.getTime();
			long diffTime = (curTime - regTime) / 1000;

			String msg = null;
			if (diffTime < SEC) {
				// sec
				msg = "방금 전";
			} else if ((diffTime /= SEC) < MIN) {
				// min
				msg = diffTime + "분 전";
			} else if ((diffTime /= MIN) < HOUR) {
				// hour
				msg = (diffTime) + "시간 전";
			} else if ((diffTime /= HOUR) < DAY) {
				// day
				msg = (diffTime) + "일 전";
			}
			else { 
				SimpleDateFormat aformat = new SimpleDateFormat("yyyy-MM-dd");
				msg = aformat.format(date);
			}
			return msg;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final char[] KEY_LIST = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		'x', 'y', 'z' };

	private static Random rnd = new Random();
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	private static String getRandomStr() {
		char[] rchar = { KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)],
				KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)],
				KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)], KEY_LIST[rnd.nextInt(36)]
						, KEY_LIST[rnd.nextInt(36)] };
		return String.copyValueOf(rchar);
	}

	public static String getFileKey() {
		return getRandomStr()
				+ dateFormat.format(new Date(System.currentTimeMillis()));
	}		

	public static void clearSharedValue(Context context) {
		SharedManager.getInstance().setBoolean(context, Global.Shared_manner, false);
		SharedManager.getInstance().setBoolean(context, Global.Shared_nopush, false);
		SharedManager.getInstance().setBoolean(context, Global.Shared_nosound, false);
		SharedManager.getInstance().setBoolean(context, Global.Shared_Sound, false);
		SharedManager.getInstance().setBoolean(context, Global.Shared_vib, false);
		SharedManager.getInstance().setString(context, Global.Shared_sttime, "1_12_00");
		SharedManager.getInstance().setString(context, Global.Shared_endtime, "1_06_00");
	}
}
