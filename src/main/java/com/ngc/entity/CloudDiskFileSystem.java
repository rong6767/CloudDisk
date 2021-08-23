package com.ngc.entity;

import java.io.Serializable;
import java.util.Date;

public class CloudDiskFileSystem implements Serializable , Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5613339931645948228L;
	private Integer id;
	private String fileName;
	private String fileNameMap;
	private String fileFullName;
	private String fileFullNameMap;
	private Integer fileIsHidden;
	private Integer fileType;
	private Integer parentId;
	private String parentPath;
	private String currentPath;
	private Integer fileStatus;
	private Long fileSize;
	private String appID;
	private Date createTime;
	private Date updateTime;
	private String uuid;
	
	private String fileFullNameLike;
	private Integer isFileNameDesc;
	
	public CloudDiskFileSystem() {
		super();
	}
	public CloudDiskFileSystem(Integer id, String fileName, String fileNameMap, String fileFullName,
			String fileFullNameMap, Integer fileIsHidden, Integer fileType, String parentPath, String currentPath,
			Integer fileStatus, Long fileSize, String appID, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileNameMap = fileNameMap;
		this.fileFullName = fileFullName;
		this.fileFullNameMap = fileFullNameMap;
		this.fileIsHidden = fileIsHidden;
		this.fileType = fileType;
		this.parentPath = parentPath;
		this.currentPath = currentPath;
		this.fileStatus = fileStatus;
		this.fileSize = fileSize;
		this.appID = appID;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	
	
	public String getUuid() {
		return uuid;
	}
	public CloudDiskFileSystem setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}
	public String getFileFullNameLike() {
		return fileFullNameLike;
	}
	public CloudDiskFileSystem setFileFullNameLike(String fileFullNameLike) {
		this.fileFullNameLike = fileFullNameLike;
		return this;
	}
	public Integer getParentId() {
		return parentId;
	}
	public CloudDiskFileSystem setParentId(Integer parentId) {
		this.parentId = parentId;
		return this;
	}
	public Integer getFileIsHidden() {
		return fileIsHidden;
	}
	public CloudDiskFileSystem setFileIsHidden(Integer fileIsHidden) {
		this.fileIsHidden = fileIsHidden;
		return this;
	}
	public Integer getId() {
		return id;
	}
	public CloudDiskFileSystem setId(Integer id) {
		this.id = id;
		return this;
	}
	public String getFileName() {
		return fileName;
	}
	public CloudDiskFileSystem setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
	public String getFileNameMap() {
		return fileNameMap;
	}
	public CloudDiskFileSystem setFileNameMap(String fileNameMap) {
		this.fileNameMap = fileNameMap;
		return this;
	}
	public String getFileFullName() {
		return fileFullName;
	}
	public CloudDiskFileSystem setFileFullName(String fileFullName) {
		this.fileFullName = fileFullName;
		return this;
	}
	public String getFileFullNameMap() {
		return fileFullNameMap;
	}
	public CloudDiskFileSystem setFileFullNameMap(String fileFullNameMap) {
		this.fileFullNameMap = fileFullNameMap;
		return this;
	}
	public Integer getFileType() {
		return fileType;
	}
	public CloudDiskFileSystem setFileType(Integer fileType) {
		this.fileType = fileType;
		return this;
	}
	public String getParentPath() {
		return parentPath;
	}
	public CloudDiskFileSystem setParentPath(String parentPath) {
		this.parentPath = parentPath;
		return this;
	}
	public String getCurrentPath() {
		return currentPath;
	}
	public CloudDiskFileSystem setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
		return this;
	}
	public Integer getFileStatus() {
		return fileStatus;
	}
	public CloudDiskFileSystem setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
		return this;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public CloudDiskFileSystem setFileSize(Long fileSize) {
		this.fileSize = fileSize;
		return this;
	}
	public String getAppID() {
		return appID;
	}
	public CloudDiskFileSystem setAppID(String appID) {
		this.appID = appID;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public CloudDiskFileSystem setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public CloudDiskFileSystem setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}
	
	public Integer getIsFileNameDesc() {
		return isFileNameDesc;
	}
	public CloudDiskFileSystem setIsFileNameDesc(Integer isFileNameDesc) {
		this.isFileNameDesc = isFileNameDesc;
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this == obj){
			return true;
		}
		if(obj instanceof CloudDiskFileSystem){
			CloudDiskFileSystem fs = (CloudDiskFileSystem)obj;
			if(this.getId() != null && this.getId() == fs.getId()){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int compareTo(Object arg0) {
		if(this == arg0){
			return 0;
		}else{
			if(arg0 instanceof CloudDiskFileSystem){
				CloudDiskFileSystem fs = (CloudDiskFileSystem)arg0;
				if(this.getParentId() < fs.getParentId()){
					return -1;
				}else if(this.getParentId() > fs.getParentId()){
					return 1;
				}else{
					return 0;
				}
			}
		}
		return 0;
	}
	@Override
	public String toString() {
		return "CloudDiskFileSystem [id=" + id + ", fileName=" + fileName + ", fileNameMap=" + fileNameMap
				+ ", fileFullName=" + fileFullName + ", fileFullNameMap=" + fileFullNameMap + ", fileIsHidden="
				+ fileIsHidden + ", fileType=" + fileType + ", parentId=" + parentId + ", parentPath=" + parentPath
				+ ", currentPath=" + currentPath + ", fileStatus=" + fileStatus + ", fileSize=" + fileSize + ", appID="
				+ appID + ", createTime=" + createTime + ", updateTime=" + updateTime + ", fileFullNameLike="
				+ fileFullNameLike + ", isFileNameDesc=" + isFileNameDesc + "]";
	}
}
