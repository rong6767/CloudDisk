package com.ngc.service.impl;

import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.apache.hadoop.fs.FileStatus;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ngc.dao.BaseDao;
import com.ngc.entity.CloudDiskFileSystem;
import com.ngc.entity.UserTrajectory;
import com.ngc.service.UtilService;
import com.ngc.util.hdfs.HDFSUtil;
import com.ngc.util.redis.CacheUtil;

@Service
@Scope("prototype")
public class UtilServiceImpl implements UtilService{
	@Resource
	private HDFSUtil hdfs;
	@Resource
	private BaseDao baseDao;
	@Override
	public int insertUserTrajectory(UserTrajectory ut) {
		// TODO Auto-generated method stub
		return this.baseDao.insertUserTrajectory(ut);
	}
	@Override
	public CloudDiskFileSystem uploadFileToDFS(String appID, 
				InputStream input, 
					String fileName) {
		CloudDiskFileSystem fs = new CloudDiskFileSystem();
		
		String userPath = hdfs.getBasePath()+"/"+appID;
		String filePath = userPath + "/" + fileName;
		if(!this.hdfs.exists(userPath)){
			this.hdfs.mkdirs(userPath);
		}
		boolean r = this.hdfs.copyFromLocalFile(input, filePath);
		if(r){
			FileStatus status = this.hdfs.getFileStatus(filePath);
			fs.setFileSize(status.getLen());
			return fs;
		}else{
			return null;
		}
	}
	@Override
	public long copyToLocalFile(String hdfsPath, OutputStream output) throws Exception {
		// TODO Auto-generated method stub
		return this.hdfs.copyToLocalFile(hdfsPath, output);
	}
	@Override
	public FileStatus getFileStatus(String path) {
		// TODO Auto-generated method stub
		return this.hdfs.getFileStatus(path);
	}
	@Override
	public String getBasePath(String appID) {
		// TODO Auto-generated method stub
		return hdfs.getBasePath()+"/"+appID;
	}
	
}
