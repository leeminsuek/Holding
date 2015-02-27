package com.br.holding5.ms;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.br.holding5.WriteFileLog;
import com.brainyx.holding5.R;

public class CommonDialog extends Dialog{

	private Context mContext;

	public interface onMenuItemSelectListener {
		public void onMenuItemSelected(int position);
	}

	onMenuItemSelectListener mMenuItemSelectListener;

	public CommonDialog(Context context) {
		super(context);
		try {

			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			setCancelable(true);
			setCanceledOnTouchOutside(true);


			mContext = context;
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	
	}
	
//	//확인 1 취소 2
//	public void createConform(onMenuItemSelectListener listener) {
//		mMenuItemSelectListener = listener;
//		
//	}

	public void createBoardMenu(onMenuItemSelectListener listener) {
		try {
			mMenuItemSelectListener = listener;
			setContentView(R.layout.popup_view_list);
			Button btn1 = (Button) findViewById(R.id.button1);
			Button btn2 = (Button) findViewById(R.id.button2);
			btn1.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(mMenuItemSelectListener != null) {
						mMenuItemSelectListener.onMenuItemSelected(1);
					}

				}
			});

			btn2.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(mMenuItemSelectListener != null) {
						mMenuItemSelectListener.onMenuItemSelected(2);
					}

				}
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	
	}
}
