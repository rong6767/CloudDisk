package com.ngc.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户行为轨迹
 * @author 杨斌斌
 * <table border="1" width="100%">
 * <tr>
 * 	<td>值</td>
 * 	<td>说明</td>
 * </tr>
 * <tr>
 * 	<td>1</td>
 * 	<td>用户注册</td>
 * </tr>
 * <tr>
 * 	<td>2</td>
 * 	<td>用户登入</td>
 * </tr>
 * <tr>
 * 	<td>3</td>
 * 	<td>用户上传</td>
 * </tr>
 * <tr>
 * 	<td></td>
 * 	<td></td>
 * </tr>
 * <tr>
 * 	<td></td>
 * 	<td></td>
 * </tr>
 * </table>
 * 
 */
public class UserTrajectory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6117141804713146231L;
	
	private Integer id;
	private Integer trajectoryType;
	private String explain;
	private Date createTime;
	private String appID;
	
	public UserTrajectory() {
		super();
	}

	public UserTrajectory(Integer id, Integer trajectoryType, String explain, Date createTime, String appID) {
		super();
		this.id = id;
		this.trajectoryType = trajectoryType;
		this.explain = explain;
		this.createTime = createTime;
		this.appID = appID;
	}

	public Integer getId() {
		return id;
	}

	public UserTrajectory setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getTrajectoryType() {
		return trajectoryType;
	}

	public UserTrajectory setTrajectoryType(Integer trajectoryType) {
		this.trajectoryType = trajectoryType;
		return this;
	}

	public String getExplain() {
		return explain;
	}

	public UserTrajectory setExplain(String explain) {
		this.explain = explain;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public UserTrajectory setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getAppID() {
		return appID;
	}

	public UserTrajectory setAppID(String appID) {
		this.appID = appID;
		return this;
	}

	@Override
	public String toString() {
		return "UserTrajectory [id=" + id + ", trajectoryType=" + trajectoryType + ", explain=" + explain
				+ ", createTime=" + createTime + ", appID=" + appID + "]";
	}
}
