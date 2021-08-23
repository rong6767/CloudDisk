package com.ngc.dao;

import java.util.List;

import com.ngc.entity.ShareFileSystem;
import com.ngc.entity.SharePw;

public interface ShareDao {
	public int insertShareFileSystem(ShareFileSystem fs);
	public List<ShareFileSystem> queryShareFileSystem(ShareFileSystem fs);
	
	public int insertShareFileSystemPw(SharePw pw);
	public List<SharePw> queryShareFileSystemPw(SharePw pw);
}
