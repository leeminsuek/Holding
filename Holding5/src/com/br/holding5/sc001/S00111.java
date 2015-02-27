package com.br.holding5.sc001;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.brainyx.holding5.R;
import com.brainyxlib.SharedManager;

public class S00111 extends BaseActivity implements OnClickListener{

	private final String TAG = "Duks";
	public static final int TYPE_SERVICE_TERM = 1;
	public static final int TYPE_PERSIONAL_TERM = 2;
	private int mType =  TYPE_SERVICE_TERM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			super.onCreate(savedInstanceState);
			
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.setContentView(R.layout.s00111);
			
			mType = getIntent().getIntExtra("type", TYPE_SERVICE_TERM);
			
			findViewById(R.id.iv_back).setOnClickListener(this);
			
			TextView tv_title = (TextView) findViewById(R.id.tv_title);
			TextView tv_desc = (TextView) findViewById(R.id.tv_desc);
			
			if (mType == TYPE_SERVICE_TERM)
			{
				tv_title.setText(R.string.sc00111_title);
				tv_desc.setText(R.string.sc00111_desc);
			}
			else
			{
				tv_title.setText(R.string.sc00112_title);
				tv_desc.setText(R.string.sc00112_desc);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.iv_back:
				{	
					finish();
				}
				break;
			
		}
	}
}
