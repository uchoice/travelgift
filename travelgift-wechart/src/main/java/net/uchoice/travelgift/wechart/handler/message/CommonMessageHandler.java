package net.uchoice.travelgift.wechart.handler.message;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import net.uchoice.travelgift.wechart.handler.MessageHandler;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;
import net.uchoice.travelgift.wechart.model.response.TextMessage;
import net.uchoice.travelgift.wechart.util.DateUtils;

/**
 * 通用消息回复
 * 
 * @author ruiliang.mrl
 *
 */
@Component
public class CommonMessageHandler implements MessageHandler {

	private static final Logger log = LoggerFactory.getLogger(CommonMessageHandler.class);

	@Override
	public boolean isEffect(InputMessage message) {
		return true;
	}

	@Override
	public int priority() {
		return Integer.MIN_VALUE;
	}

	@Override
	public void preHandle(InputMessage message) {
		log.info(String.format("[Common] t[%s] in[%s]", DateUtils.dateFormat(new Date()),
				JSON.toJSONString(message)));
	}

	@Override
	public BaseMessage handle(InputMessage message) {
		TextMessage text = message.transfer(TextMessage.class);
		text.setContent("欢迎关注礼游记，阿礼在这里等你哦！");
		return text;
	}

	@Override
	public void postHandle(InputMessage message) {

	}

}
