package net.uchoice.travelgift.wechart.handler.event;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.uchoice.travelgift.user.domain.UserDO;
import net.uchoice.travelgift.user.exception.UserServiceException;
import net.uchoice.travelgift.user.service.UserService;
import net.uchoice.travelgift.wechart.handler.MessageHandler;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;
import net.uchoice.travelgift.wechart.util.DateUtils;
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

	@Autowired
	UserService userService;

	@Override
	public boolean isEffect(InputMessage message) {
		if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equalsIgnoreCase(message.getMsgType())
				&& MessageUtil.EVENT_TYPE_SUBSCRIBE.equalsIgnoreCase(message.getEvent())) {
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
		log.info(String.format("[Subscribe] t[%s] u[%s]", DateUtils.dateFormat(new Date()), message.getFromUserName()));
	}

	@Override
	public BaseMessage handle(InputMessage message) {
		return null;
	}

	@Override
	public void postHandle(InputMessage message) {
		String openId = message.getFromUserName();
		UserDO user = new UserDO();
		user.setOpenId(openId);
		if (!userService.checkExistsByOpenId(openId)) {
			try {
				userService.addUser(user);
			} catch (UserServiceException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
