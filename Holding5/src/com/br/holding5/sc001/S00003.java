package com.br.holding5.sc001;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.base.BaseActivity;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData2;
import com.brainyx.holding5.R;

public class S00003 extends BaseActivity implements OnClickListener{

	private final String TAG = "Duks";
	
	private EditText et_email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.setContentView(R.layout.s00003);
			
			et_email = (EditText) findViewById(R.id.et_email);
			et_email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			
			findViewById(R.id.iv_back).setOnClickListener(this);
			findViewById(R.id.iv_ok).setOnClickListener(this);
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
			case R.id.iv_ok:
				{
					String email = et_email.getText().toString();
					if (TextUtils.isEmpty(email))
					{
						Toast.makeText(S00003.this, "이메일을 입력해 주세요.", Toast.LENGTH_LONG).show();
						return;
					}
					sendfindPwd(email);
				}
				break;
		}
	}
	
	private void sendfindPwd(final String userId)
	{
		try {
			final Dialog dialog = new Dialog(this, R.style.TransDialog);
			dialog.setContentView(new ProgressBar(this));
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, Global.host + "member/findPwd");
			params.put( "userId", userId );
			
			HttpUtil.getInstance().requestHttp2(this, params,
					new OnAfterParsedData2() {

						@Override
						public void onResult2(JSONObject jsonObject)
								throws JSONException {
							
							Log.d(TAG, jsonObject.toString());
							
							if(jsonObject.optBoolean("result"))
							{
								runOnUiThread(new Runnable() {
									public void run() {
										dialog.dismiss();
										Toast.makeText(S00003.this, "등록하신 ID(이메일)로 임시비밀번호를 발송했습니다.", Toast.LENGTH_LONG).show();
									}
								});
							}
							else
							{
								final String result_text = jsonObject.optString("result_text");
								runOnUiThread(new Runnable() {
									public void run() {
										if (!TextUtils.isEmpty(result_text))
											Toast.makeText(S00003.this, result_text, Toast.LENGTH_LONG).show();
										else
											Toast.makeText(S00003.this, "해당하는 ID가 없습니다.", Toast.LENGTH_LONG).show();
										dialog.dismiss();
									}
								});
							}
						}

						@Override
						public void onResult3() {
							
						}
				
			});
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		
		
	}
}
