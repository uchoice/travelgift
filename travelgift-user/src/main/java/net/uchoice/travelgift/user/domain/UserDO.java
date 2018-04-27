package net.uchoice.travelgift.user.domain;

import java.io.Serializable;

/**
 * 用户基础信息表
 * @author ruiliang.mrl
 *
 */
public class UserDO extends BaseDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  用户昵称 **/
	private String userNick;
	
	/** 用户姓名  **/
	private String userName;
	
	/** 手机号码  **/
	private String mobile;
	
	/** 联系地址  **/
	private String address;
	
	/** 微信id  **/
	private String openId;
	
	private Integer isAdmin;
	
	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
}
