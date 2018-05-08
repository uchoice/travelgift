package net.uchoice.travelgift.wechart.handler.biz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import net.uchoice.travelgift.wechart.handler.MessageHandler;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;
import net.uchoice.travelgift.wechart.model.response.Image;
import net.uchoice.travelgift.wechart.model.response.ImageMessage;
import net.uchoice.travelgift.wechart.util.MessageUtil;

/**
 * 通用消息回复(回复"车厘子"或者"樱桃"时发送)
 * 
 * @author xbyang
 *
 */
@Component
public class CherryMessageHandler implements MessageHandler {

	private static final Logger log = LoggerFactory.getLogger(CherryMessageHandler.class);

	@Value("${wechart.cherry.mediaId}")
	String cherryMediaId;

	Image image = null;

	@Override
	public boolean isEffect(InputMessage message) {
		if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equalsIgnoreCase(message.getMsgType())
				&& !StringUtils.isEmpty(message.getContent()) && isCherry(message.getContent())) {
			return true;
		}
		return false;
	}

	@Override
	public int priority() {
		return 99999;
	}

	@Override
	public void preHandle(InputMessage message) {
		if(image == null) {
			image = new Image();
			image.setMediaId(cherryMediaId);
		}
		log.info(String.format("[Cherry] {}", JSON.toJSONString(message)));
	}

	@Override
	public BaseMessage handle(InputMessage message) {
		ImageMessage txt = message.transfer(ImageMessage.class);
		txt.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
		txt.setCreateTime(System.currentTimeMillis());
		txt.setImage(image);
		return txt;
	}

	@Override
	public void postHandle(InputMessage message) {

	}

	public boolean isCherry(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		return str.contains("车厘子") || str.contains("樱桃");
	}
}
