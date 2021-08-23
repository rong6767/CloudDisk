package com.ngc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ngc.dao.CloudDiskFileSystemDao;
import com.ngc.dao.ShareDao;
import com.ngc.entity.CloudDiskFileSystem;
import com.ngc.entity.ShareFileSystem;
import com.ngc.entity.SharePw;
import com.ngc.service.CloudDiskFileSystemService;
import com.ngc.service.ShareService;

@Service
@Scope("prototype")
public class ShareServiceImpl implements ShareService{
	
	@Resource
	private ShareDao dao;

	@Override
	public int insertShareFileSystem(ShareFileSystem fs) {
		// TODO Auto-generated method stub
		return dao.insertShareFileSystem(fs);
	}

	@Override
	public List<ShareFileSystem> queryShareFileSystem(ShareFileSystem fs) {
		// TODO Auto-generated method stub
		return dao.queryShareFileSystem(fs);
	}

	@Override
	public int insertShareFileSystemPw(SharePw pw) {
		// TODO Auto-generated method stub
		return dao.insertShareFileSystemPw(pw);
	}

	@Override
	public List<SharePw> queryShareFileSystemPw(SharePw pw) {
		// TODO Auto-generated method stub
		return dao.queryShareFileSystemPw(pw);
	}
	

}
