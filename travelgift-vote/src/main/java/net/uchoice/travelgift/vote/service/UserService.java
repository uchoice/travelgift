package net.uchoice.travelgift.vote.service;

import net.uchoice.travelgift.vote.entity.User;

public interface UserService {
	
	void addUser(User user);
	
	User get(String userId);
	
}
