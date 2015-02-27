package com.brainyx.holding5;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import com.br.holding5.Global;
import com.br.holding5.WriteFileLog;
import com.br.holding5.sc001.SC001;
import com.brainyxlib.SharedManager;

public class GCMPushProc {
	
	public static int count = 1;
	public void onReceivePushMsg(Context context, PushVO pushVo){
		try {
			int msgType = pushVo.getMsgType();
//			String sendFlag = pushVo.getIsSend();
			Intent intent = new Intent(context, SC001.class);
			intent.putExtra("push", pushVo);
			intent.putExtra("pushYn", true);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			boolean alarmCheck = SharedManager.getInstance().getBoolean(context, Global.Shared_nopush);
			boolean sound =  SharedManager.getInstance().getBoolean(context, Global.Shared_Sound);
			boolean maner = SharedManager.getInstance().getBoolean(context, Global.Shared_manner);
			boolean vib = SharedManager.getInstance().getBoolean(context, Global.Shared_vib);
			boolean noSound = SharedManager.getInstance().getBoolean(context, Global.Shared_nosound);
			String startTime = SharedManager.getInstance().getString(context, Global.Shared_sttime);
			String endTime = SharedManager.getInstance().getString(context, Global.Shared_endtime);
			
			
			if(!alarmCheck) {
				PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); 
				Notification.Builder builder = new Notification.Builder(context); 
				builder.setSmallIcon(R.drawable.app_icon_72); 
				builder.setTicker(pushVo.getSign1()); 
				builder.setWhen(System.currentTimeMillis()); 
				builder.setNumber(count);
				count++;
				builder.setContentTitle(context.getResources().getString(R.string.app_name)); 
				builder.setContentText(pushVo.getSign1()); 
				builder.setContentIntent(pendingIntent); 
				builder.setAutoCancel(true);
				//무음이 아닐떄
				if(!noSound) {
					//매너모드
					if(maner) {
						builder.setDefaults(Notification.DEFAULT_VIBRATE);
						
						if(!startTime.equals("") && !endTime.equals("")) {
							String[] startSplit = startTime.split("_");
							String[] endSplit = endTime.split("_");
							
							try {
								int sHour, eHour; //시작
								int sMinute, eMinute; //종료
								int nHour, nMinute; //현재
								long now = System.currentTimeMillis();
								Date date = new Date(now);
								SimpleDateFormat format = new SimpleDateFormat("HH");
								SimpleDateFormat format2 = new SimpleDateFormat("mm");
								nHour = Integer.parseInt(format.format(date));
								nMinute = Integer.parseInt(format2.format(date));
								//1  오전 2 오후
								sHour = Integer.parseInt(startSplit[1]);
								sMinute = Integer.parseInt(startSplit[2]);
								eHour = Integer.parseInt(endSplit[1]);
								eMinute = Integer.parseInt(endSplit[2]);
								
								if(startSplit[0].equals("1")) {
									
								}
								else if(startSplit[0].equals("2")) {
									sHour += 12;
								}
								
								if(endSplit[0].equals("1")) {
									
								}
								else if(endSplit[0].equals("2")) {
									eHour += 12;
								}
								
								boolean flag = false;
								if(nHour > sHour) {
									if(nHour < eHour) {
										flag = true;
									}
									else if(nHour == eHour) {
										if(nMinute <= eMinute) {
											flag = true;
										}
									}
								}
								else if(nHour == sHour) {
									if(nMinute >= sMinute) {
										if(nHour == eHour) {
											if(nMinute <= eMinute) {
												flag = true;
											}
										}
									}
								}
								else {
									flag = false;
								}
								
								if(flag) {
									Log.e("", "");
									builder.setSound(null);
									builder.setDefaults(0);
								}
								else {
									if(sound) {
										builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));	
									}
									//진동 체크
									if(vib) {
										builder.setDefaults(Notification.DEFAULT_VIBRATE);	
									}
								}
							} catch (Exception e) {
								WriteFileLog.writeException(e);
							}
							
						}
					}
					else {
						//소리 체크
						if(sound) {
							builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));	
						}
						//진동 체크
						if(vib) {
							builder.setDefaults(Notification.DEFAULT_VIBRATE);	
						}
					}
				}
				
				Notification notification = builder.getNotification(); 
				NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); 
				mNotificationManager.notify(1, notification);
			}
			
//			Intent intent = null;
//			if(type.equals("4")) {
////				int postNo = data.getPostNo();
//				intent = new Intent(context,Scenario5_S00117.class);
//				intent.putExtra("postno", Integer.parseInt(postno));
////				startActivity(intent);
//				
//			}
//			else if( type.equals("6")){
//				
//			}
//			else if( type.equals("5")) {
//				
//			}
//			else {
////				int postNo = data.getPostNo();
//				intent = new Intent(context,ContentDetailView.class);
//				intent.putExtra("postNo", Integer.parseInt(postno));
//				intent.putExtra("viewtype", type);
////				Intent intent = new Intent(Setting00119.this, ContentDetailView.class);
////				intent.putExtra("postNo", postNo);
////				intent.putExtra("viewtype", type);
////				startActivity(intent);
//			}
//			PendingIntent pendingIntent = PendingIntent.getActivity(context, 
//					0, intent, PendingIntent.FLAG_ONE_SHOT);
//			NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//			
////			 When the loop is finished, updates the notification
////			 setContentText - 현재 Notification의 제목을 작성
//			builder.setContentText(title)
//			                // Notification이 표시되면서 화면에 잠시 노출되는 내용.
//					.setTicker( "새로운 " + getTypeTitle(type) + " 글이 등록되었습니다.")
//					.setProgress(0, 0, false) // Removes the progress bar
//			                // Notification을 누를 경우 자동으로 닫히도록 처리(여기서 Action을 눌렀을때는 닫히지 않습니다.)
//					.setAutoCancel(true)
//					.setSmallIcon(R.drawable.ic_launcher)
//			                // Notification을 눌렀을 경우 처리되는 Intent
//					.setContentIntent(pendingIntent);
////			                // BitPictureStyle을 적용하는 코드
////					.setStyle(new NotificationCompat.BigPictureStyle()
////			                           // bigPicture의 이미지 파일은 Bitmap으로 처리되어야 합니다.
////			                           .bigPicture(photo)
////			                           // 화면이 펼쳐진 상태에서 보여질 아이콘을 Bitmap으로 처리해줍니다.
////			                           .bigLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_gallery))
////			                 // 하단의 공유 버튼
////			                 .addAction(android.R.drawable.ic_menu_share, getResources().getString(R.string.share), sendPendingIntent);
////					
					
//			NotificationBuilder builder = new NotificationBuilder(context);
//			builder.ShowNotificationTextBuilder(R.drawable.ic_launcher, "제목테스트", "새로운 ",SC001.class, true);
//			DBManager db = DBManager.getDBManager(context);
//			db.insertNotification(Integer.valueOf(postno), Integer.valueOf(type), contents, System.currentTimeMillis(), contents);
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
	}
}
