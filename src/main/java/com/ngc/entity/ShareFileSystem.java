package com.ngc.entity;

import java.io.Serializable;
import java.util.Date;

public class ShareFileSystem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5429773425424689362L;
	
	private Integer id;
	private String token;
	private String fileName;
	private String fileNameMap;
	private String fileFullName;
	private String fileFullNameMap;
	private Integer fileType;
	private Integer parentId;
	private String parentPath;
	private String currentPath;
	private Integer fileStatus;
	private Long fileSize;
	private String shareAppId;
	private Date createTime;
	
	public ShareFileSystem() {
		super();
	}
	public ShareFileSystem(Integer id, String token, String fileName, String fileNameMap, String fileFullName,
			String fileFullNameMap, Integer fileType, Integer parentId, String parentPath, String currentPath,
			Integer fileStatus, Long fileSize, String shareAppId, Date createTime) {
		super();
		this.id = id;
		this.token = token;
		this.fileName = fileName;
		this.fileNameMap = fileNameMap;
		this.fileFullName = fileFullName;
		this.fileFullNameMap = fileFullNameMap;
		this.fileType = fileType;
		this.parentId = parentId;
		this.parentPath = parentPath;
		this.currentPath = currentPath;
		this.fileStatus = fileStatus;
		this.fileSize = fileSize;
		this.shareAppId = shareAppId;
		this.createTime = createTime;
	}
	public String getToken() {
		return token;
	}
	public ShareFileSystem setToken(String token) {
		this.token = token;
		return this;
	}
	public String getShareAppId() {
		return shareAppId;
	}
	public ShareFileSystem setShareAppId(String shareAppId) {
		this.shareAppId = shareAppId;
		return this;
	}
	public Integer getParentId() {
		return parentId;
	}
	public ShareFileSystem setParentId(Integer parentId) {
		this.parentId = parentId;
		return this;
	}
	public Integer getId() {
		return id;
	}
	public ShareFileSystem setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getFileName() {
		return fileName;
	}
	public ShareFileSystem setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
	public String getFileNameMap() {
		return fileNameMap;
	}
	public ShareFileSystem setFileNameMap(String fileNameMap) {
		this.fileNameMap = fileNameMap;
		return this;
	}
	public String getFileFullName() {
		return fileFullName;
	}
	public ShareFileSystem setFileFullName(String fileFullName) {
		this.fileFullName = fileFullName;
		return this;
	}
	public String getFileFullNameMap() {
		return fileFullNameMap;
	}
	public ShareFileSystem setFileFullNameMap(String fileFullNameMap) {
		this.fileFullNameMap = fileFullNameMap;
		return this;
	}
	public Integer getFileType() {
		return fileType;
	}
	public ShareFileSystem setFileType(Integer fileType) {
		this.fileType = fileType;
		return this;
	}
	public String getParentPath() {
		return parentPath;
	}
	public ShareFileSystem setParentPath(String parentPath) {
		this.parentPath = parentPath;
		return this;
	}
	public String getCurrentPath() {
		return currentPath;
	}
	public ShareFileSystem setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
		return this;
	}
	public Integer getFileStatus() {
		return fileStatus;
	}
	public ShareFileSystem setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
		return this;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public ShareFileSystem setFileSize(Long fileSize) {
		this.fileSize = fileSize;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public ShareFileSystem setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj){
			return true;
		}
		if(obj instanceof ShareFileSystem){
			ShareFileSystem fs = (ShareFileSystem)obj;
			if(this.getId() != null && this.getId() == fs.getId()){
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "ShareFileSystem [id=" + id + ", token=" + token + ", fileName=" + fileName + ", fileNameMap="
				+ fileNameMap + ", fileFullName=" + fileFullName + ", fileFullNameMap=" + fileFullNameMap
				+ ", fileType=" + fileType + ", parentId=" + parentId + ", parentPath=" + parentPath + ", currentPath="
				+ currentPath + ", fileStatus=" + fileStatus + ", fileSize=" + fileSize + ", shareAppId=" + shareAppId
				+ ", createTime=" + createTime + "]";
	}
}
