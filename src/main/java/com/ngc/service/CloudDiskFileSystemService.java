package com.ngc.service;

import java.util.List;

import com.ngc.entity.CloudDiskFileSystem;

public interface CloudDiskFileSystemService {
	public List<CloudDiskFileSystem> queryCloudDiskFileSystem(CloudDiskFileSystem fs);
	public int updateCloudDiskFileSystem(CloudDiskFileSystem fileSystem);
	public int insertCloudDiskFileSystem(CloudDiskFileSystem fs);
	public int moveFsList(List<CloudDiskFileSystem> fsList);
	public int cpFsList(List<CloudDiskFileSystem> fsList);
}
