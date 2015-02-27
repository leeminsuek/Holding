package com.br.holding5.sc001;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.br.holding5.WriteFileLog;
import com.br.holding5.sc002.S00013;
import com.brainyx.holding5.R;

public class HappyinDialog extends Dialog implements View.OnClickListener {
	
	public final String TAG = "Duks";
	
	static final float DIMENSIONS_DIFF_PORTRAIT = 70;

	private Context mContext;
	private String mName;
	
	public HappyinDialog(Context context, String name) {
        super(context);
        mContext = context;
        mName = name;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	try {
    		   super.onCreate(savedInstanceState);
    	        requestWindowFeature(Window.FEATURE_NO_TITLE);
    			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));	
    			
    			this.setCanceledOnTouchOutside(false);
    			
    			LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			View v = null;
    			v = vi.inflate(R.layout.s00002_happyin_dialog, null);
    			
    			ImageView iv_close = (ImageView)v.findViewById(R.id.iv_close);
    			iv_close.setOnClickListener(this);
    			
    			
    			if (mName != null && mName.length() > 0)
    			{
    				TextView tv_desc = (TextView)v.findViewById(R.id.tv_desc);
    				tv_desc.setText(Html.fromHtml(String.format(mContext.getString(R.string.sc001_popup_happyin_desc), mName)));
    			}
    			
    			Display display = getWindow().getWindowManager().getDefaultDisplay();
    			final float scale = getContext().getResources().getDisplayMetrics().density;
    			float dimensions = DIMENSIONS_DIFF_PORTRAIT;

    			addContentView(v, new LinearLayout.LayoutParams(
    					display.getWidth() - ((int) (dimensions * scale + 0.5f)),
    					LinearLayout.LayoutParams.WRAP_CONTENT));
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
     
        
	}
    
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	}

	@Override
	public void onClick(View v)
	{
		
		switch(v.getId())
		{
			case R.id.iv_close:
				dismiss();
				
				getContext().startActivity(new Intent(getContext(), S00013.class));
				((Activity)mContext).finish();
				break;
			
		}
		
	}
}