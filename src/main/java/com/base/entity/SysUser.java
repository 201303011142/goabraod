package com.base.entity;


/**
 * adminBean.
 *
 * @author mouxiao 
 * 
 */
public class SysUser implements  java.io.Serializable {
	private static final long serialVersionUID = 1L; 
	private String id;
	private String userName;
	private String passWord;
	private Integer disableFlag;
	private String trueName;
	private String telephoneNo;
	private String type;
	//非po
	private String url ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Integer getDisableFlag() {
		return disableFlag;
	}
	public void setDisableFlag(Integer disableFlag) {
		this.disableFlag = disableFlag;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "SysUser [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", disableFlag="
				+ disableFlag + ", trueName=" + trueName + ", telephoneNo=" + telephoneNo + "]";
	}
	
	
}
