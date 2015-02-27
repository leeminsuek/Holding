package com.br.holding5.sc014;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class HappyVo {

	public String name;
	public String happyPhoto;
	public int seq;
	public int memLvl;
	
	public String birthDay;
	public String happyIntro;
	public String location;
	
	public String school;
	
	
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getHappyIntro() {
		return happyIntro;
	}

	public void setHappyIntro(String happyIntro) {
		this.happyIntro = happyIntro;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getMemLvl() {
		return memLvl;
	}

	public void setMemLvl(int memLvl) {
		this.memLvl = memLvl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHappyPhoto() {
		return happyPhoto;
	}

	public void setHappyPhoto(String happyPhoto) {
		this.happyPhoto = happyPhoto;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public static ArrayList<HappyVo> PasingJson(String json,ArrayList<HappyVo> arraylist){
		try {
			JSONArray data = null;
			data = new JSONArray(json.toString());
			if(data != null){
				for(int i = 0 ; i<data.length();i++){
					JSONObject obj = data.getJSONObject(i);
					HappyVo dto  = new HappyVo();
					dto.setHappyPhoto(obj.getString("happyPhoto"));
					dto.setName(obj.getString("name"));
					dto.setSeq(obj.getInt("seq"));
					dto.setMemLvl(obj.getInt("memLvl"));
					dto.setBirthDay(obj.getString("birthDay"));
					dto.setHappyIntro(obj.getString("happyIntro"));
					dto.setLocation(obj.getString("location"));
					dto.setSchool(obj.getString("school"));
					
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
