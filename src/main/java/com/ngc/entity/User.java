package com.ngc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author 杨斌斌
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1548241812757106215L;
	
	private Integer id;
	private String userName;
	private String passWord;
	private String phoneNumber;
	private String appID;
	private Date createTime;
	private Date updateTime;
	
	public User() {
		super();
	}
	public User(Integer id, String userName, String passWord, String phoneNumber, String appID, Date createTime,
			Date updateTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.phoneNumber = phoneNumber;
		this.appID = appID;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Integer getId() {
		return id;
	}
	public User setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getUserName() {
		return userName;
	}
	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	public String getPassWord() {
		return passWord;
	}
	public User setPassWord(String passWord) {
		this.passWord = passWord;
		return this;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public User setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}
	public String getAppID() {
		return appID;
	}
	public User setAppID(String appID) {
		this.appID = appID;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public User setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public User setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", phoneNumber=" + phoneNumber
				+ ", appID=" + appID + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
}
