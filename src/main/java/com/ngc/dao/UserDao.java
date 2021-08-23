package com.ngc.dao;

import java.util.List;
import com.ngc.entity.User;

public interface UserDao {
	public List<User> queryUserByPar(User user);
	public int insertUser(User user);
}
