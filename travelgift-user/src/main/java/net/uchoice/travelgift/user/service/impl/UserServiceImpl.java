/**
 * 
 */
package net.uchoice.travelgift.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.uchoice.travelgift.user.dao.UserMapper;
import net.uchoice.travelgift.user.domain.UserDO;
import net.uchoice.travelgift.user.exception.UserServiceException;
import net.uchoice.travelgift.user.service.UserService;

/**
 * @author ruiliang.mrl
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static List<String> cachedUserOpenIds = new ArrayList<String>();

	@Autowired
	UserMapper userMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.uchoice.travelgift.user.service.UserService#addUser(net.uchoice.
	 * travelgift.user.domain.UserDO)
	 */
	@Override
	public void addUser(UserDO user) throws UserServiceException {
		if (null != user) {
			if (!StringUtils.isEmpty(user.getOpenId()) && checkExistsByOpenId(user.getOpenId())) {
				throw new UserServiceException("微信id已存在");
			}
			user.setGmtCreate(new Date());
			user.setGmtModified(new Date());
			user.setIsDeleted(0);
			userMapper.insert(user);
			cachedUserOpenIds.add(user.getOpenId());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.uchoice.travelgift.user.service.UserService#checkExistsByOpenId(java.lang
	 * .String)
	 */
	@Override
	public boolean checkExistsByOpenId(String openId) {
		if(!StringUtils.isEmpty(openId) && cachedUserOpenIds.contains(openId)) {
			return true;
		}
		return null != getUserByOpenId(openId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.uchoice.travelgift.user.service.UserService#getUserByOpenId(java.lang.
	 * String)
	 */
	@Override
	public UserDO getUserByOpenId(String openId) {
		UserDO user = null;
		if (!StringUtils.isEmpty(openId)) {
			UserDO param = new UserDO();
			param.setOpenId(openId);
			List<UserDO> users = userMapper.queryList(param);
			if (null != users && !users.isEmpty()) {
				user = users.get(0);
			}
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.uchoice.travelgift.user.service.UserService#updateUserByOpenId(java.lang.
	 * String)
	 */
	@Override
	public void updateUserByOpenId(UserDO user) throws UserServiceException {
		if (null != user && !StringUtils.isEmpty(user.getOpenId())) {
			UserDO userOld = getUserByOpenId(user.getOpenId());
			if (null == userOld) {
				throw new UserServiceException("用户不存在");
			}
			user.setId(userOld.getId());
			user.setGmtModified(new Date());
			userMapper.updateByPrimaryKey(user);
		}
	}

}
