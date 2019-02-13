package com.base.entity;

public class GoOrderTravel {

	private String gotId;
	private String gvId;
	private String goId;
	private String gotcreatetime;
	public String getGotId() {
		return gotId;
	}
	public void setGotId(String gotId) {
		this.gotId = gotId;
	}
	public String getGvId() {
		return gvId;
	}
	public void setGvId(String gvId) {
		this.gvId = gvId;
	}
	public String getGoId() {
		return goId;
	}
	public void setGoId(String goId) {
		this.goId = goId;
	}
	public String getGotcreatetime() {
		return gotcreatetime;
	}
	public void setGotcreatetime(String gotcreatetime) {
		this.gotcreatetime = gotcreatetime;
	}
	@Override
	public String toString() {
		return "GoOrderTravel [gotId=" + gotId + ", gvId=" + gvId + ", goId=" + goId + ", gotcreatetime="
				+ gotcreatetime + "]";
	}
}
