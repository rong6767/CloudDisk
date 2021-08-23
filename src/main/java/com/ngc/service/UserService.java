package com.ngc.service;

import java.util.List;

import com.ngc.entity.User;

public interface UserService {
	public List<User> queryUserByPar(User user);
	public int insertUser(User user);
}
