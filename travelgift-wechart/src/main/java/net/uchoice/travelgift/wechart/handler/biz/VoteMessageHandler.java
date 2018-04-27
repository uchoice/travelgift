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
import net.uchoice.travelgift.vote.vo.ArticleDetail;
import net.uchoice.travelgift.wechart.handler.MessageHandler;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;
import net.uchoice.travelgift.wechart.model.response.TextMessage;
import net.uchoice.travelgift.wechart.util.MessageUtil;

/**
 * 通用消息回复(回复数字进行投票)
 * 
 * @author ruiliang.mrl
 *
 */
@Component
public class VoteMessageHandler implements MessageHandler {

	private static final Logger log = LoggerFactory.getLogger(VoteMessageHandler.class);

	@Autowired
	UserService userService;

	@Value("${wechart.portal}")
	String portal;

	@Autowired
	ArticleService articleService;

	@Override
	public boolean isEffect(InputMessage message) {
		if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equalsIgnoreCase(message.getMsgType())
				&& !StringUtils.isEmpty(message.getContent()) && isNumeric(message.getContent())) {
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
		TextMessage txt = message.transfer(TextMessage.class);
		try {
			articleService.vote(Integer.valueOf(message.getContent()), message.getFromUserName());
			ArticleDetail article = articleService.getArticle(Integer.valueOf(message.getContent()),
					message.getFromUserName());
			txt.setContent(String.format("恭喜您为[%s]号作品投票成功，当前得票：[%s]。 \r\n 发送‘活动’二字可马上参与活动哦～", message.getContent(),
					String.valueOf(article.getArticle().getVotes())));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			txt.setContent("投票没有成功，请确认作品编号正确或当天未投票后稍后再试～  \r\n 发送‘活动’二字可马上参与活动哦～");
		}
		return txt;
	}

	@Override
	public void postHandle(InputMessage message) {

	}

	public boolean isNumeric(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
