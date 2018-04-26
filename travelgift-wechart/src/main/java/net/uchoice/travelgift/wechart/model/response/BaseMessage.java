package net.uchoice.travelgift.wechart.model.response;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XStreamAlias("ToUserName")
	private String ToUserName;
	
	@XStreamAlias("FromUserName")
	private String FromUserName;
	
	@XStreamAlias("CreateTime")
	private Long CreateTime = System.currentTimeMillis();
	
	@XStreamAlias("MsgType")
	private String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
