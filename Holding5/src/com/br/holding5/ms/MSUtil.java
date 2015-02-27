package com.br.holding5.ms;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;

public class MSUtil {
	private static MSUtil _instance = null;
	private Context mContext;
	public static MSUtil getInstance()
	{
		if( _instance == null )
		{
			_instance = new MSUtil();
		}
		return _instance;
	}

	public void setContext( Context instance )
	{
		mContext = instance;
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

	public int intTodp(int num)
	{
		//분기처리
		if(Build.MODEL.equals("SHO")) {
			return 0;
		}
		else { 
			return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num,mContext.getResources().getDisplayMetrics());	
		}
	}
}
