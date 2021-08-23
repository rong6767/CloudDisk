package com.ngc.util.hdfs;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.fs.FileStatus;

public interface HDFSUtil {
	public boolean isFile(String path);
	public boolean isDirectory(String path);
	public boolean exists(String path);
	public boolean mkdirs(String path);
	public boolean copyFromLocalFile(InputStream input , String hdfsPath);
	public long copyToLocalFile(String hdfsPath,OutputStream output)throws Exception;
	public String getBasePath();
	public FileStatus getFileStatus(String path);
}
