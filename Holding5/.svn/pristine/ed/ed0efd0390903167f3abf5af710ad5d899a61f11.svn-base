package com.br.holding5.sc014;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.brainyx.holding5.R;
import com.brainyxlib.BrDateManager;

public class SC01119 extends BaseActivity{

	ImageView profileimg,frameimg;
	TextView edittext1,edittext2,edittext3,textview4;
	TextView header_title ;
	static HappyVo mVO;
	public static void setSeq(HappyVo happy){
		mVO = happy;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.s01119);
		
		init();
		
		
	}
	
	private void init(){
		try {
			findViewById(R.id.header_back_button).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			
			edittext1 = (TextView)findViewById(R.id.edittext1);
			edittext2 = (TextView)findViewById(R.id.edittext2);
			edittext3 = (TextView)findViewById(R.id.edittext3);
			textview4 = (TextView)findViewById(R.id.textview4);
			header_title = (TextView) findViewById(R.id.header_title);
			profileimg = (ImageView)findViewById(R.id.profileimg);
			frameimg = (ImageView)findViewById(R.id.frameimg);
			
			String[] birth = mVO.getBirthDay().split(" ");
			String[] birth2 = birth[0].split("-");
			edittext1.setText(birth2[0]+"년"+BrDateManager.getInstance().ReturnZero(Integer.valueOf(birth2[1]))+"월"+ BrDateManager.getInstance().ReturnZero(Integer.valueOf(birth2[2]))+ "일");
			header_title.setText(mVO.getName());
			edittext3.setText(mVO.getLocation());
			textview4.setText(mVO.getHappyIntro());
			edittext2.setText(mVO.getSchool());
			if(mVO.getHappyPhoto().startsWith("http")) {
				myApp.imageloder.displayImage(mVO.getHappyPhoto(), profileimg);
			}
			else {
				myApp.imageloder.displayImage(Global.host + mVO.getHappyPhoto(), profileimg);				
			}

			frameimg.setBackgroundResource(getFrame(mVO.getMemLvl()));
			
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
	

	public int getFrame(int lv){
		switch(lv){
		case 2 :
			return R.drawable.happy_prf_bg_01;
		case 3:
			return R.drawable.happy_prf_bg_02;
		case 4:
			return R.drawable.happy_prf_bg_03;
		}
		
		return 0;
	}
}
