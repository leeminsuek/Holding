package com.br.holding5.base;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.br.holding5.Global;
import com.br.holding5.sc001.SC001;
import com.br.holding5.sc002.S00013;
import com.br.holding5.sc003.S00113;
import com.br.holding5.sc004.SC004;
import com.br.holding5.sc005.SC005;
import com.br.holding5.sc006.SC006;
import com.br.holding5.sc007.SC007;
import com.br.holding5.sc007.SC007Happy;
import com.br.holding5.sc008.SC008;
import com.br.holding5.sc009.SC009;
import com.br.holding5.sc010.SC010;
import com.br.holding5.sc011.SC011;
import com.br.holding5.sc014.SC014;
import com.br.holding5.sc116.S00116;
import com.brainyx.holding5.R;

public class MainActivity extends BaseActivity implements OnClickListener {

	private int[] rsid = new int[] { R.id.btn1, R.id.btn2, R.id.btn3,
			R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
			R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13 ,R.id.btn14};

	private String[] classname = new String[] { SC001.class.getName(),
			S00013.class.getName(), S00113.class.getName(),
			SC004.class.getName(), SC005.class.getName(),
			SC006.class.getName(), SC007.class.getName(),
			SC008.class.getName(), SC009.class.getName(),
			SC010.class.getName(), SC011.class.getName(),  SC007Happy.class.getName(),S00116.class.getName(),SC014.class.getName()};
	//			SC010.class.getName(), SC011.class.getName(), SC012.class.getName(),
	//			SC007Happy.class.getName()
	//			};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mainactivity);

		init();
		requestTempList();
	}

	private void init() {

		for (int i = 0; i < rsid.length; i++) {
			View v = findViewById(rsid[i]);
			v.setOnClickListener(this);
			v.setTag(i);
		}
	}

	@Override
	public void onClick(View v) {

		//		for (int i = 0; i < rsid.length; i++) {
		//			if (v.getId() == rsid[i]) {
		//				NextActivity(i);
		//				break;
		//			}
		//		}
		final int INDEX = Integer.valueOf(v.getTag() + "");
		NextActivity(INDEX);

	}

	private void NextActivity(int activity) {
		Intent intent = new Intent();
		intent.setClassName(this, classname[activity]);
		startActivity(intent);
		// getBaseActivity().overridePendingTransition(R.anim.start_enter,
		// R.anim.start_exit);
	}

	private void requestTempList() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		AQuery aquery = new AQuery(this);

		Dialog dialog = new Dialog(this, R.style.TransDialog);
		dialog.setContentView(new ProgressBar(this));
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		//		dialog.show();	

		aquery.progress(dialog).ajax(Global.BOARD_TEMP_LIST, params, JSONObject.class, this, "tempListCallBack");


	}
	public void tempListCallBack(String url, JSONObject jsonObject, AjaxStatus status) throws JSONException {

		if(!jsonObject.isNull("data")) {
			
			String temp = jsonObject.getString("data");
			String[] tempArr;
			tempArr = temp.split(",");
		
			for(int i = 0 ; i < tempArr.length ; i++) {
				tempArr[i] = tempArr[i].replace("[", "");
				tempArr[i] = tempArr[i].replace("]", "");
				
				myApp.tempResource.add(tempArr[i]);
			}
			
//			myApp.tempResource
		}
	}
}
