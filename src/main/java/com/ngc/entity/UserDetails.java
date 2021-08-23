package com.ngc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户扩展信息
 * @author 杨斌斌
 */
public class UserDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6383677102871472413L;
	
	private Integer id;
	private String realName;
	private String imgPath;
	private String appID;
	
	public UserDetails() {
		super();
	}

	public UserDetails(Integer id, String realName, String imgPath, String appID) {
		super();
		this.id = id;
		this.realName = realName;
		this.imgPath = imgPath;
		this.appID = appID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", realName=" + realName + ", imgPath=" + imgPath + ", appID=" + appID + "]";
	}
}
