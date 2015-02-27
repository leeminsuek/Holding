package com.br.holding5.sc003;

import com.br.holding5.ms.Common.getProfile;

public class ReplyVO implements getProfile{

	private int sex;
	private int likeCnt;
	private int no;
	private String photoName;
	private String audioName;
	private String videoName;
	private String movName;
	private String commentContents;
	private String birthDay;
	private int type;
	private String tagId;
	private String commentId;
	private String tagNickName;
	private String commentTime;
	private String nickName;
	private int reportCnt;
	private int seq;
	private int unknown;
	private String tempName;
	private int memLvl;
	private String happyPhoto;
	private int isReport;
	
	
	public int getIsReport() {
		return isReport;
	}


	public void setIsReport(int isReport) {
		this.isReport = isReport;
	}


	public String getHappyPhoto() {
		return happyPhoto;
	}


	public void setHappyPhoto(String happyPhoto) {
		this.happyPhoto = happyPhoto;
	}


	public String getVideoName() {
		return videoName;
	}


	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}


	public int getMemLvl() {
		return memLvl;
	}


	public void setMemLvl(int memLvl) {
		this.memLvl = memLvl;
	}


	public ReplyVO() {
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


	public int getUnknown() {
		return unknown;
	}


	public void setUnknown(int unknown) {
		this.unknown = unknown;
	}


	public String getTempName() {
		return tempName;
	}


	public void setTempName(String tempName) {
		this.tempName = tempName;
	}


	@Override
	public int getItemSex() {
		return this.sex;
	}


	@Override
	public String getItemBirth() {
		return this.birthDay;
	}


	@Override
	public int getItemMemverLevel() {
		// TODO Auto-generated method stub
		return 3;
	}


	@Override
	public int getItemSeriousLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}