package net.uchoice.travelgift.wechart.handler.event;

import java.util.Date;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.uchoice.travelgift.wechart.handler.MessageHandler;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;
import net.uchoice.travelgift.wechart.util.MessageUtil;

/**
 * 用户关注事件
 * 
 * @author ruiliang.mrl
 *
 */
@Component
public class SubscribeEventHandler implements MessageHandler {

	private static final Logger log = LoggerFactory.getLogger(SubscribeEventHandler.class);

	@Override
	public boolean isEffect(InputMessage message) {
		if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(message.getMsgType())
				&& MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(message.getEvent())) {
			return true;
		}
		return false;
	}

	@Override
	public int priority() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void preHandle(InputMessage message) {
		log.info(String.format("[Subscribe] t[%s] u[%s]", DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"),
				message.getFromUserName()));
	}

	@Override
	public BaseMessage handle(InputMessage message) {
		return null;
	}

	@Override
	public void postHandle(InputMessage message) {

	}

}
