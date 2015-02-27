package com.br.holding5.sc014;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class BoardVo {

	public int sex;
	public int likeCnt; 
	public String tempName;
	//public String audioName;
	public String writeTime;
	public int memLv;
	public String nickName;
	public String userId;
	
	public String contents;
	public int replyCnt; 
	public int unknown;
	public int reportCnt;
	public int seq;
	public String happyPhoto;
	public int kind;
	
	
	
	
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public String getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}
	public int getMemLv() {
		return memLv;
	}
	public void setMemLv(int memLv) {
		this.memLv = memLv;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getReplyCnt() {
		return replyCnt;
	}
	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}
	public int getUnknown() {
		return unknown;
	}
	public void setUnknown(int unknown) {
		this.unknown = unknown;
	}
	public int getReportCnt() {
		return reportCnt;
	}
	public void setReportCnt(int reportCnt) {
		this.reportCnt = reportCnt;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getHappyPhoto() {
		return happyPhoto;
	}
	public void setHappyPhoto(String happyPhoto) {
		this.happyPhoto = happyPhoto;
	}
	
	public static ArrayList<Object> PasingJson(String json,ArrayList<Object> arraylist){
		try {
			JSONArray data = null;
			data = new JSONArray(json.toString());
			if(data != null){
				for(int i = 0 ; i<data.length();i++){
					JSONObject obj = data.getJSONObject(i);
					BoardVo dto  = new BoardVo();
					dto.setContents(obj.getString("contents"));
					dto.setHappyPhoto(obj.getString("happyPhoto"));
					dto.setLikeCnt(obj.getInt("likeCnt"));
					dto.setMemLv(obj.getInt("memLvl"));
					dto.setNickName(obj.getString("nickName"));
					dto.setReplyCnt(obj.getInt("replyCnt"));
					dto.setReportCnt(obj.getInt("reportCnt"));
					dto.setSeq(obj.getInt("seq"));
					dto.setSex(obj.getInt("sex"));
					dto.setTempName(obj.getString("tempName"));
					dto.setUnknown(obj.getInt("unknown"));
					dto.setUserId(obj.getString("userId"));
					dto.setWriteTime(obj.getString("writeTime"));
					dto.setKind(obj.getInt("kind"));
					
					arraylist.add(dto);
				}
			}
			
			return arraylist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return arraylist;
	}
	
}
