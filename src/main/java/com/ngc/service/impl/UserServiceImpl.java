package com.ngc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ngc.dao.UserDao;
import com.ngc.entity.User;
import com.ngc.service.UserService;

@Service
@Scope("prototype")
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;

	@Override
	public List<User> queryUserByPar(User user) {
		return userDao.queryUserByPar(user);
	}

	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		return userDao.insertUser(user);
	}
}
