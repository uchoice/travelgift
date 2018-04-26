/**
 * 
 */
package net.uchoice.travelgift.user.service;

import net.uchoice.travelgift.user.domain.UserDO;
import net.uchoice.travelgift.user.exception.UserServiceException;

/**
 * @author ruiliang.mrl
 *
 */
public interface UserService {

	void addUser(UserDO user) throws UserServiceException;

	boolean checkExistsByOpenId(String openId);

	UserDO getUserByOpenId(String openId);

	void updateUserByOpenId(UserDO user) throws UserServiceException;

}
