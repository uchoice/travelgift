package net.uchoice.travelgift.wechart.handler.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import net.uchoice.travelgift.user.domain.UserDO;
import net.uchoice.travelgift.user.exception.UserServiceException;
import net.uchoice.travelgift.user.service.UserService;
import net.uchoice.travelgift.vote.service.ArticleService;
import net.uchoice.travelgift.wechart.handler.MessageHandler;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;
import net.uchoice.travelgift.wechart.model.response.TextMessage;
import net.uchoice.travelgift.wechart.util.MessageUtil;

/**
 * 通用消息回复(发送SH审核列表拉取)
 * 
 * 
 * @author ruiliang.mrl
 *
 */
@Component
public class VoteAuditMessageHandler implements MessageHandler {

	private static final Logger log = LoggerFactory.getLogger(VoteAuditMessageHandler.class);

	@Autowired
	UserService userService;

	@Value("${wechart.portal}")
	String portal;
	
	@Value("${wechart.adminUrl}")
	String adminUrl;

	@Autowired
	ArticleService articleService;

	@Override
	public boolean isEffect(InputMessage message) {
		if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equalsIgnoreCase(message.getMsgType())
				&& !StringUtils.isEmpty(message.getContent()) && "SH".equalsIgnoreCase(message.getContent())) {
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

	@Override
	public BaseMessage handle(InputMessage message) {
		TextMessage txt = null;
		try {
			UserDO user = userService.getUserByOpenId(message.getFromUserName());
			if(user != null && 1 == user.getIsAdmin()) {
				txt = message.transfer(TextMessage.class);
				txt.setContent(adminUrl.replaceAll("%UserId%", message.getFromUserName()));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return txt;
	}

	@Override
	public void postHandle(InputMessage message) {

	}

}
