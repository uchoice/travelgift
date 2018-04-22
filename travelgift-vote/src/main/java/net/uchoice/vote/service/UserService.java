package net.uchoice.vote.service;

import net.uchoice.vote.entity.User;

public interface UserService {
	
	void addUser(User user);
	
	User get(String userId);
	
}
