package com.brainyx.holding5;

import java.io.Serializable;

import android.os.Parcelable;

public class PushVO implements Serializable{
	String copyPost;
	int msgNo;
	String del;
	String sign1;
	String sign2;
	int contentsNo;
	String receiveId;
	String sendTime;
	int msgType;
	int alarmSeq;
	public String getCopyPost() {
		return copyPost;
	}
	public void setCopyPost(String copyPost) {
		this.copyPost = copyPost;
	}
	public int getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(int msgNo) {
		this.msgNo = msgNo;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getSign1() {
		return sign1;
	}
	public void setSign1(String sign1) {
		this.sign1 = sign1;
	}
	public String getSign2() {
		return sign2;
	}
	public void setSign2(String sign2) {
		this.sign2 = sign2;
	}
	public int getContentsNo() {
		return contentsNo;
	}
	public void setContentsNo(int contentsNo) {
		this.contentsNo = contentsNo;
	}
	public String getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public int getAlarmSeq() {
		return alarmSeq;
	}
	public void setAlarmSeq(int alarmSeq) {
		this.alarmSeq = alarmSeq;
	}
	
	
}
