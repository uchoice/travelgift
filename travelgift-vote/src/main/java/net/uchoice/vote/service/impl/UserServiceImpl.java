package net.uchoice.vote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.uchoice.vote.dao.UserMapper;
import net.uchoice.vote.entity.User;
import net.uchoice.vote.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	public void addUser(User user) {
		userMapper.insert(user);
	}

	@Override
	public User get(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

}
