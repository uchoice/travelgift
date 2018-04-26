package net.uchoice.travelgift.wechart.model.response;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import net.uchoice.travelgift.wechart.util.MessageUtil;

@XStreamAlias("xml")
public class TextMessage extends BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TextMessage() {
		this.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	}

	@XStreamAlias("Content")
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}
