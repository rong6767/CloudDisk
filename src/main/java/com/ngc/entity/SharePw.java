package com.ngc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author 杨斌斌
 */
public class SharePw implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5241011404838144910L;
	
	private Integer id;
	private String token;
	private String pw;
	private String appID;
	
	public SharePw() {
		super();
	}
	public SharePw(Integer id, String token, String pw, String appID) {
		super();
		this.id = id;
		this.token = token;
		this.pw = pw;
		this.appID = appID;
	}
	
	public Integer getId() {
		return id;
	}
	public SharePw setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getToken() {
		return token;
	}
	public SharePw setToken(String token) {
		this.token = token;
		return this;
	}
	public String getPw() {
		return pw;
	}
	public SharePw setPw(String pw) {
		this.pw = pw;
		return this;
	}
	public String getAppID() {
		return appID;
	}
	public SharePw setAppID(String appID) {
		this.appID = appID;
		return this;
	}
	@Override
	public String toString() {
		return "SharePw [id=" + id + ", token=" + token + ", pw=" + pw + ", appID=" + appID + "]";
	}
}
