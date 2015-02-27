package com.br.holding5.sc003.gallery;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RImageView extends ImageView{

	public RImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		
		
		Drawable d = getDrawable();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
