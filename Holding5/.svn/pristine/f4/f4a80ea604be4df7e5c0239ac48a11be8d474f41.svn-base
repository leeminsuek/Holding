package com.brainyx.holding5;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.br.holding5.Global;
import com.brainyxlib.SharedManager;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	private static final String tag = "GCM_TEST";
	public static final String SEND_ID = "95280102841";

	public GCMIntentService(){ this(SEND_ID); }
	
	public GCMIntentService(String senderId) { super(senderId); }

	@Override
	protected void onMessage(final Context context, Intent intent) {
//		Bundle b = intent.getExtras();
		String startTime = SharedManager.getInstance().getString(this, Global.Shared_sttime);
		String endTime = SharedManager.getInstance().getString(this, Global.Shared_endtime);
		boolean alarmCheck = SharedManager.getInstance().getBoolean(this, Global.Shared_nopush);
		boolean sound =  SharedManager.getInstance().getBoolean(this, Global.Shared_Sound);
		boolean maner = SharedManager.getInstance().getBoolean(this, Global.Shared_manner);
		boolean vib = SharedManager.getInstance().getBoolean(this, Global.Shared_vib);
		boolean noSound = SharedManager.getInstance().getBoolean(this, Global.Shared_nosound);
		
		if(startTime.equals("")) {
			SharedManager.getInstance().setString(this, Global.Shared_sttime, "1_12_00");
		}
		if(endTime.equals("")) {
			SharedManager.getInstance().setString(this, Global.Shared_endtime, "1_06_00");
		}
//		if(alarmCheck.equals("")) {
//			SharedManager.getInstance().setBoolean(this, Global.Shared_nopush, false);
//		}
//		if(sound.equals("")) {
//			SharedManager.getInstance().setBoolean(this, Global.Shared_Sound, false);
//		}
//		if(maner.equals("")) {
//			SharedManager.getInstance().setBoolean(this, Global.Shared_manner, false);
//		}
//		if(vib.equals("")) {
//			SharedManager.getInstance().setBoolean(this, Global.Shared_vib, false);
//		}
//		if(noSound.equals("")) {
//			SharedManager.getInstance().setBoolean(this, Global.Shared_nosound, false);
//		}
		
		
		PushVO pushVo = new PushVO();
//		String isSend = intent.getStringExtra("isSend");
		try {
			String copyPost = intent.getStringExtra("copyPost");
			String del = URLDecoder.decode(intent.getStringExtra("del"), "UTF-8");
			int msgNo = Integer.parseInt(intent.getStringExtra("msgNo"));//			String from = intent.getStringExtra("from");
			String sign1 = URLDecoder.decode(intent.getStringExtra("sign1"), "UTF-8");
			String sign2 = URLDecoder.decode(intent.getStringExtra("sign2"), "UTF-8");
			int contentsNo = Integer.parseInt(intent.getStringExtra("contentsNo"));
			String receiveId = URLDecoder.decode(intent.getStringExtra("receiveId"), "UTF-8");
			String sendTime = URLDecoder.decode(intent.getStringExtra("sendTime"), "UTF-8");
			int msgType = Integer.parseInt(intent.getStringExtra("msgType"));
			int alarmSeq = Integer.parseInt(intent.getStringExtra("alarmSeq"));
			
			pushVo.setContentsNo(contentsNo);
			pushVo.setCopyPost(copyPost);
			pushVo.setDel(del);
			pushVo.setSign1(sign1);
			pushVo.setSign2(sign2);
			pushVo.setReceiveId(receiveId);
			pushVo.setSendTime(sendTime);
			pushVo.setMsgType(msgType);
			pushVo.setAlarmSeq(alarmSeq);
			pushVo.setMsgNo(msgNo);
			
			GCMPushProc pushProc = new GCMPushProc();
			pushProc.onReceivePushMsg(context,pushVo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	protected void onError(Context context, String errorId) {
		Log.d(tag, "onError. errorId : "+errorId);
	}

	@Override
	protected void onRegistered(Context context, String regId) {
		Log.d(tag, "onRegistered. regId : "+regId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.d(tag, "onUnregistered. regId : "+regId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		Log.d(tag, "onRecoverableError. errorId : "+errorId);
		return super.onRecoverableError(context, errorId);
	}
}