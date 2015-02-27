package com.br.holding5.sc014;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReplyVo {
	
	public int sex;
	public int likeCnt;
	public int no;
	public String photoName;
	public String audioName;
	public String movName;
	public String commentContents;
	public String birthDay;
	public int type;
	public String tagId;
	public String commentId;
	public String tagNickName;
	public String commentTime;
	public String nickName;
	public int reportCnt;
	public int seq;
	public String tempName ;
	public String happyPhoto;
	public int memLvl;
	
	
	
	
	
	public String getHappyPhoto() {
		return happyPhoto;
	}
	public void setHappyPhoto(String happyPhoto) {
		this.happyPhoto = happyPhoto;
	}
	public int getMemLvl() {
		return memLvl;
	}
	public void setMemLvl(int memLvl) {
		this.memLvl = memLvl;
	}
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
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
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getAudioName() {
		return audioName;
	}
	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}
	public String getMovName() {
		return movName;
	}
	public void setMovName(String movName) {
		this.movName = movName;
	}
	public String getCommentContents() {
		return commentContents;
	}
	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getTagNickName() {
		return tagNickName;
	}
	public void setTagNickName(String tagNickName) {
		this.tagNickName = tagNickName;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	
	
	public static ArrayList<Object> PasingJson(String json,ArrayList<Object> arraylist){
		try {
			JSONArray data = null;
			data = new JSONArray(json.toString());
			if(data != null){
				for(int i = 0 ; i<data.length();i++){
					JSONObject obj = data.getJSONObject(i);
					ReplyVo dto  = new ReplyVo();
					dto.setSex(obj.getInt("sex"));
					dto.setLikeCnt(obj.getInt("likeCnt"));
					dto.setNo(obj.getInt("no"));
					dto.setPhotoName(obj.getString("photoName"));
					dto.setAudioName(obj.getString("audioName"));
					dto.setMovName(obj.getString("movName"));
					dto.setCommentContents(obj.getString("commentContents"));
					dto.setBirthDay(obj.getString("birthDay"));
					dto.setType(obj.getInt("type"));
					dto.setTagId(obj.getString("tagId"));
					dto.setCommentId(obj.getString("commentId"));
					dto.setTagNickName(obj.getString("tagNickName"));
					dto.setCommentTime(obj.getString("commentTime"));
					dto.setNickName(obj.getString("nickName"));
					dto.setReportCnt(obj.getInt("reportCnt"));
					dto.setSeq(obj.getInt("seq"));
					dto.setTempName(obj.getString("tempName"));
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
