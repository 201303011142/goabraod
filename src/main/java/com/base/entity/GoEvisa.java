package com.base.entity;

public class GoEvisa {

	private String gfId;
	private String gfUrl;
	private String gfName;
	private String gfType;
	private String gfDepCity;
	private String gfCreateTime;
	
	public String getGfId() {
		return gfId;
	}
	public void setGfId(String gfId) {
		this.gfId = gfId;
	}
	public String getGfUrl() {
		return gfUrl;
	}
	public void setGfUrl(String gfUrl) {
		this.gfUrl = gfUrl;
	}
	public String getGfName() {
		return gfName;
	}
	public void setGfName(String gfName) {
		this.gfName = gfName;
	}
	public String getGfType() {
		return gfType;
	}
	public void setGfType(String gfType) {
		this.gfType = gfType;
	}
	public String getGfDepCity() {
		return gfDepCity;
	}
	public void setGfDepCity(String gfDepCity) {
		this.gfDepCity = gfDepCity;
	}
	public String getGfCreateTime() {
		return gfCreateTime;
	}
	public void setGfCreateTime(String gfCreateTime) {
		this.gfCreateTime = gfCreateTime;
	}

	@Override
	public String toString() {
		return "GoEvisa{" +
				"gfId='" + gfId + '\'' +
				", gfUrl='" + gfUrl + '\'' +
				", gfName='" + gfName + '\'' +
				", gfType='" + gfType + '\'' +
				", gfDepCity='" + gfDepCity + '\'' +
				", gfCreateTime='" + gfCreateTime + '\'' +
				'}';
	}
}
