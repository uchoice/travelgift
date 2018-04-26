package net.uchoice.travelgift.wechart.handler.message;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import net.uchoice.travelgift.user.domain.UserDO;
import net.uchoice.travelgift.user.exception.UserServiceException;
import net.uchoice.travelgift.user.service.UserService;
import net.uchoice.travelgift.wechart.handler.MessageHandler;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;
import net.uchoice.travelgift.wechart.model.response.NewsMessage;
import net.uchoice.travelgift.wechart.util.DateUtils;
import net.uchoice.travelgift.wechart.util.MessageUtil;

/**
 * 通用消息回复
 * 
 * @author ruiliang.mrl
 *
 */
@Component
public class CommonMessageHandler implements MessageHandler {

	private static final Logger log = LoggerFactory.getLogger(CommonMessageHandler.class);

	@Autowired
	UserService userService;

	@Value("${wechart.portal}")
	String portal;

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
		log.info(String.format("[Common] t[%s] in[%s]", DateUtils.dateFormat(new Date()), JSON.toJSONString(message)));
	}

	@Override
	public BaseMessage handle(InputMessage message) {
		NewsMessage msg = MessageUtil.xmlToBean(portal, NewsMessage.class);
		msg.setFromUserName(message.getToUserName());
		msg.setToUserName(message.getFromUserName());
		msg.setCreateTime(System.currentTimeMillis());
		msg.setArticleCount(msg.getArticles().size());
		msg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		return msg;
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
