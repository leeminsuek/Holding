package com.br.holding5;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.br.holding5.sc001.SC001;
import com.br.holding5.util.HttpUtil;
import com.br.holding5.util.HttpUtil.OnAfterParsedData2;
import com.brainyxlib.SharedManager;

public class Global {
	
	//아마존 파일서버 키값 , 버킷네
	public static String AMAZON_A3_SECRET_KEY = "63C3rl8HgqcSEWlsFUV2AZSxxL4hSSWOkE3lehaD";
	public static String AMAZON_A3_ACCESS_KEY = "AKIAJ2RDJBL2AQVO67FA";
	public static String AMAZON_A3_BUCKET_NAME = "brainyx";
	
	public static String Shared_UserId = "user_id";
	public static String Shared_UserPwd = "user_pwd";
	public static String Shared_UserPushKey = "user_pushkey";
	public static String Shared_IsLogin = "islogin";
	
	
	public static String Shared_Sound = "hd_sound";// 소리설정
	public static String Shared_vib = "hd_vib";// 진동설정
	public static String Shared_nopush = "hd_nopush";// 푸쉬설정
	public static String Shared_manner = "hd_manner";// 매너모드설정
	/**
	 * 매너모드 시작,종료 시간설정 예)1_5_24 < _로잘라서 맨앞은 오전,오후 1이오전 2가오후
	 * 중간은 시간
	 * 마지막은 분
	 * 종료시간도 동일
	 */
	public static String Shared_nosound = "hd_nosound";
	public static String Shared_sttime = "hd_sttime";// 매너모드 시작시간설정
	public static String Shared_endtime = "hd_endtime";// 매너모드 종료시간설정
//	public static String Shared_sttime_ap = "hd_sttime_ap";// 매너모드 시작시간 오전오후
//	public static String Shared_endtime_ap = "hd_endtime_ap";// 매너모드 종료시간 오전오후

//	public static String host = "http://brainyx711.iptime.org:5943/";
	public static String host = "http://54.64.173.138:8080/";
//	public static String host = "http://brainyx711.iptime.org:6384/";
	public static String imgHost = host;
	
	public static boolean mMainRefreshProfile = false;
	public static boolean mDetailRefreshProfile = false;
	public static boolean mMoreRefreshProfile = false;
	// URL
	//	public final static String BASE_URL = "http://brainyx711.iptime.org:6385"
	
	//	public final static String BASE_URL = "http://brainyx711.iptime.org:6384";
	public final static String URL_GETPROFILE = host + "profile/getProfile";
	public final static String URL_MODIFYNICK = host + "profile/editNickName";
	public final static String URL_BOARDLIST = host + "profile/getBoardList";
	public final static String URL_REPLYLIST = host + "profile/getReplyList";
	public final static String URL_HAPPYMEMBER = host + "profile/getHappyMember";
	public final static String URL_USERNOREADCOUNT = host + "profile/getNoReadCount";
	
	public final static String EVENT_DAYS = host + "event/days";

	public final static String URL_MEMBERDEL = host + "profile/memberDel";
	public final static String URL_LOGOUT = host + "profile/logout";
	public final static String URL_CHANGEPW= host + "profile/changePw";
	public final static String URL_CHANGEBIRTH= host + "profile/editBirthDay";
	public final static String URL_CHANGESCHOOL= host + "profile/editSchool";
	public final static String URL_CHANGELOCATION= host + "profile/editLocation";
	public final static String PROFILE_IMAGE_UPLOAD = host + "profile/updateProfile";


	public static final String BOARD_LIST = host + "board/list";
	public static final String BOARD_LIKE = host + "board/like";
	public static final String BOARD_DETAIL = host + "board/detail";
	public static final String BOARD_DELETE = host + "board/delete";
	public static final String BOARD_REPORT = host + "board/report";
	public static final String BOARD_BOARD_ADD = host + "board/addBoard";
	public static final String BOARD_REPLY = host + "board/reply";
	public static final String BOARD_REPLY_LIKE = host + "board/replyLike";
	public static final String BOARD_ADD_REPLY = host + "board/addReply";
	public static final String BOARD_REPLY_REPORT = host + "board/replyReport";
	public static final String BOARD_TEMP_LIST = host + "board/tempList";
	public static final String BOARD_EDIT = host + "board/editBoard";
	public static final String BOARD_EDIT_REPLY = host + "board/editReply";
	public static final String BOARD_DELETE_REPLY = host + "board/deleteReply";

	public static final String POST_READ_UPDATE = host + "post/update";
	
	public static final String NOTICE_LIST = host + "notice/list";
	public static final String NOTICE_DETAIL = host + "notice/detail";
	public static final String PROPOSAL_LIST = host + "proposal/list";
	public static final String PROPOSAL_DETAIL = host + "proposal/detail";
	public static final String PROPOSAL_INSERT = host + "proposal/insert";
	public static final String PROPOSAL_UPDATE = host + "proposal/update";
	public static final String PROPOSAL_DELETE = host + "proposal/delete";
	
	public static final String HAPPYIN_CHECK = host + "happy/list";
	public static final String HAPPYIN_ISMESSAGE = host + "message/isMessage";
	public static final String HAPPYIN_GETMESSAGE = host + "message/getMessage";
	public static final String HAPPYIN_INSERTMESSAGE = host + "message/insertMessage";
	public static final String HAPPYIN_UPDATEMESSAGE = host + "message/updateMessage";
	public static final String HAPPYIN_DELETEMESSAGE = host + "message/deleteMessage";
	public static final String HAPPYIN_HOPEMESSAGE = host + "message/getHopeMessageList";


	public interface httpCallBack {
		public void JsonCallback(JSONObject jsonObject, String api)
				throws JSONException;
	}

	public static httpCallBack mHttpCallBack;

	public static void excuteHttp(Activity context, final String api,
			Map<String, Object> keyMap, httpCallBack callback) {
		try {
			mHttpCallBack = callback;
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put(HttpUtil.KEY_URL, api);

			Iterator<String> iterator = keyMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = keyMap.get(key);
				params.put(key, value);
			}

			HttpUtil.getInstance().requestHttp2(context, params,
					new OnAfterParsedData2() {
				@Override
				public void onResult2(JSONObject jsonObject)
						throws JSONException {
					// boolean yn = result;
					// try {
					// Log.d("TAG_JSON", resultData.toString());
					//
					// } catch (Exception e) {
					// e.printStackTrace();
					// }finally{ /// 끝나면 핸들러로 화면셋팅
					// if(yn) {
					mHttpCallBack.JsonCallback(jsonObject, api);
					// }
					// }
				}

				@Override
				public void onResult3() {
					// mHttpCallBack.
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
