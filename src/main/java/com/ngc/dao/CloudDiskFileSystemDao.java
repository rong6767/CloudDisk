package com.ngc.dao;

import java.util.List;

import com.ngc.entity.CloudDiskFileSystem;

public interface CloudDiskFileSystemDao {
	public List<CloudDiskFileSystem> queryCloudDiskFileSystem(CloudDiskFileSystem fs);
	public int insertCloudDiskFileSystem(CloudDiskFileSystem fs);
	public int updateCloudDiskFileSystem(CloudDiskFileSystem fs);
	
}
