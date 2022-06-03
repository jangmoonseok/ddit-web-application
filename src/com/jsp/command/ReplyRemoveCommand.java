package com.jsp.command;

public class ReplyRemoveCommand {

	String rno;
	String bno;
	String page;
	
	public void setRno(String rno) {
		this.rno = rno;
	}
	public void setBno(String bno) {
		this.bno = bno;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public int getRno() {
		return Integer.parseInt(rno);
	}
	public int getBno() {
		return Integer.parseInt(bno);
	}
	public int getPage() {
		return Integer.parseInt(page);
	}
	
	
}
