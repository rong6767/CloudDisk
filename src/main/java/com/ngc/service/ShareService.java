package com.ngc.service;

import java.util.List;

import com.ngc.entity.CloudDiskFileSystem;
import com.ngc.entity.ShareFileSystem;
import com.ngc.entity.SharePw;

public interface ShareService {
	public int insertShareFileSystem(ShareFileSystem fs);
	public List<ShareFileSystem> queryShareFileSystem(ShareFileSystem fs);
	
	public int insertShareFileSystemPw(SharePw pw);
	public List<SharePw> queryShareFileSystemPw(SharePw pw);
}
