package com.br.holding5.ms;

import android.content.Context;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MoreTextView extends TextView{

	private int mMaxLenght;
	private ClickAble mClickAble;
	private boolean mMoreYN = false;
	private final String MORE_STRING = "[ ... ]";
	public MoreTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	public void setMoreText(String text) {
		boolean flag = false;
		String content = text.toString();
		if(mMoreYN == false && content.length() >= mMaxLenght) {
			this.setMovementMethod(LinkMovementMethod.getInstance());
			mClickAble = new ClickAble();
			mClickAble.setContent(content);
			mClickAble.setTextView(this);
			
			content = content.substring(0,mMaxLenght);
			content = content + MORE_STRING;
			this.setText(content, BufferType.SPANNABLE);
			Spannable spans = (Spannable) this.getText(); 
			int startIndex =content.indexOf(MORE_STRING);
			String resultSubString = content.substring(startIndex);
			int endIndex = resultSubString.indexOf("]");
			
			spans.setSpan(mClickAble, startIndex, (endIndex + startIndex)+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			flag = true;
		}
		if(flag == false)
			this.setText(content);	
	}
	
	
	public void setMaxLength(int max) {
		mMaxLenght = max;
	}
	
	class ClickAble extends ClickableSpan {

		private String content;
		private MoreTextView txt;
		
		public void setTextView(MoreTextView txt) {
			this.txt = txt;
		}
		
		public void setContent(String content) {
			this.content = content;
		}
		
		@Override
		public void onClick(View widget) {
			Toast.makeText(getContext(), "�׽�Ʈ", 1).show();
			mMoreYN  = true;
			this.txt.setText(this.content);
		}
	}
}
