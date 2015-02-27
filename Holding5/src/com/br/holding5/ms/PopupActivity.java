package com.br.holding5.ms;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.br.holding5.WriteFileLog;
import com.brainyx.holding5.R;

public class PopupActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
			layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
			layoutParams.dimAmount= 0.7f;
			getWindow().setAttributes(layoutParams);
			setContentView(R.layout.popup_view);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	
	}
}
