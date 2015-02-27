package com.br.holding5.ms;

import com.br.holding5.WriteFileLog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView{

	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		try {
			Drawable d = getDrawable();
			if(d != null) {
				int drawableHeight = d.getIntrinsicHeight();
				int drawableWidth = d.getIntrinsicWidth();
				
				super.onMeasure(drawableWidth, drawableHeight);
			}
			else {
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		
		
	}
}
