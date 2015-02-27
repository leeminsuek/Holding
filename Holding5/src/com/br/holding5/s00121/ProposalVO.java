package com.br.holding5.s00121;

public class ProposalVO {

	private int seq=0;
	private String proposalId = "";
	private String sendTime = "";
	private String contents = "";
	private String nickName = "";
	private String happyPhoto = "";
	
	private int sex = 0;
	private int memLvl = 0;
	private String birthDay = "";
	
	private String userId = "";
	
	
	
	
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getMemLvl() {
		return memLvl;
	}
	public void setMemLvl(int memLvl) {
		this.memLvl = memLvl;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getProposalId() {
		return proposalId;
	}
	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHappyPhoto() {
		return happyPhoto;
	}
	public void setHappyPhoto(String happyPhoto) {
		this.happyPhoto = happyPhoto;
	}
	
	
	
}
