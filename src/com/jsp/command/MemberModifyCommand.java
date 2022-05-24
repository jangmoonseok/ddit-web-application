package com.jsp.command;

import java.util.Date;

import com.jsp.dto.MemberVO;

public class MemberModifyCommand {

	private String id        ;
	private String pwd       ;
	private String email     ;
	private String tempPicture   ; // 사진파일 경로/파일명
	private String phone     ;
	private String name = "---"      ;
	private String authority; // 권한
	
	
	public String getTempPicture() {
		return tempPicture;
	}
	public void setTempPicture(String tempPicture) {
		this.tempPicture = tempPicture;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public MemberVO toMemberVO() {
		// MemberVO setting
		MemberVO member = new MemberVO();
		member.setId(id);
		member.setPwd(pwd);
		member.setPhone(phone);
		member.setEmail(email);
		member.setPicture(tempPicture);
		member.setAuthority(authority);
		member.setName(name);
		member.setRegdate(new Date());
		
		return member;
	}
}
