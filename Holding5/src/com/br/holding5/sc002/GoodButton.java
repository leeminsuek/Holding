package com.br.holding5.sc002;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class GoodButton extends Button {

		public GoodButton(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		private int seq;
		private String userId;
		
		
		public void setSeq (int seq) {
			this.seq = seq;
		}
		
		public void setUserId (String userId) {
			this.userId = userId;
		}
		
		public int getSeq() {
			return seq;
		}
		
		public String getUserId() {
			return userId;
		}
	}