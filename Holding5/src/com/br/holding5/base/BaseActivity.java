package com.br.holding5.base;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.br.holding5.Holding5;
import com.br.holding5.WriteFileLog;

public class BaseActivity extends Activity {
	protected Holding5 myApp;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			
			myApp = (Holding5) getApplication();
			myApp.loadTypeface();
			myApp.mActivityStack.add(this);
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	
	public void AllFinish() {
		for(int i = 0 ; i < myApp.mActivityStack.size(); i ++ ) {
			myApp.mActivityStack.get(i).finish();
		}
		
		myApp.mActivityStack.clear();
	}
	
	
	@Override
    public void setContentView(int viewId) {
		try {
			View view = LayoutInflater.from(this).inflate(viewId, null);
	        ViewGroup group = (ViewGroup)view;
	        int childCnt = group.getChildCount();
	        for(int i=0; i<childCnt; i++){
	            View v = group.getChildAt(i);
	            if(v instanceof TextView){
	            	if(((TextView) v).getTypeface() == null){
	            		((TextView)v).setTypeface(myApp.getFontNormal());
	            	}else{
	            		if (((TextView) v).getTypeface().equals(Typeface.BOLD)) {
	                		((TextView)v).setTypeface(myApp.getFontBold());
	    				}else {
	    					((TextView)v).setTypeface(myApp.getFontNormal());
	    				}	
	            	}
	            }
	        }
	        super.setContentView(view);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
    }
	
	public void profileView(View v){
		return;
	}
	
}
