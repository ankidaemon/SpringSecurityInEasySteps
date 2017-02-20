package com.demo.dao;

import java.util.List;

import com.demo.to.User;

public interface UserDao {
	public String save(User u);
    public void update(User u);
    public String authenticateUser(User user);
    public List<User> list();
}
