package net.uchoice.travelgift.wechart.handler.event;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.uchoice.travelgift.wechart.handler.MessageHandler;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;
import net.uchoice.travelgift.wechart.util.DateUtils;
import net.uchoice.travelgift.wechart.util.MessageUtil;

/**
 * 用户定位事件
 * 
 * @author ruiliang.mrl
 *
 */
@Component
public class LocationEventHandler implements MessageHandler {

	private static final Logger log = LoggerFactory.getLogger(LocationEventHandler.class);

	@Override
	public boolean isEffect(InputMessage message) {
		if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equalsIgnoreCase(message.getMsgType())
				&& MessageUtil.EVENT_TYPE_LOCATION.equalsIgnoreCase(message.getEvent())) {
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
		log.info(String.format("[LOCATION] t[%s] u[%s] lon[%s] lat[%s] scale[%s]",
				DateUtils.dateFormat(new Date()), message.getFromUserName(),
				message.getLocation_X(), message.getLocation_Y(), String.valueOf(message.getScale())));
	}

	@Override
	public BaseMessage handle(InputMessage message) {
		return null;
	}

	@Override
	public void postHandle(InputMessage message) {

	}

}
