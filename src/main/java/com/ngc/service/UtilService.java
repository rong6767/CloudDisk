package com.ngc.service;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.fs.FileStatus;

import com.ngc.entity.CloudDiskFileSystem;
import com.ngc.entity.UserTrajectory;

public interface UtilService {
	public int insertUserTrajectory(UserTrajectory ut);
	public CloudDiskFileSystem uploadFileToDFS(
			String appID,
				InputStream input,
						String fileName);
	public long copyToLocalFile(String hdfsPath,OutputStream output) throws Exception;
	public FileStatus getFileStatus(String path);
	public String getBasePath(String appID);
}
