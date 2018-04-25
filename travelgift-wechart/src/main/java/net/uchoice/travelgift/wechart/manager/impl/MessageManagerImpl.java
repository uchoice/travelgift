package net.uchoice.travelgift.wechart.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import net.uchoice.travelgift.wechart.handler.MessageHandler;
import net.uchoice.travelgift.wechart.manager.MessageManager;
import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;

@Component("messageManager")
public class MessageManagerImpl implements MessageManager, ApplicationContextAware {

	private ApplicationContext ctx;
	private List<MessageHandler> messageHandlers = new ArrayList<MessageHandler>();

	@PostConstruct
	public void init() {
		if (null != ctx) {
			Map<String, MessageHandler> beans = ctx.getBeansOfType(MessageHandler.class);
			messageHandlers.addAll(beans.values());
			Collections.sort(messageHandlers, new Comparator<MessageHandler>() {

				@Override
				public int compare(MessageHandler o1, MessageHandler o2) {
					if (o1.priority() >= o2.priority()) {
						return 1;
					} else {
						return -1;
					}
				}
			});
		}
	}

	@Override
	public BaseMessage process(InputMessage message) {
		BaseMessage result = null;
		for(MessageHandler handler:messageHandlers) {
			if(handler.isEffect(message)) {
				handler.preHandle(message);
				BaseMessage resp = handler.handle(message);
				// 消息优先返回优先级高的
				if(null != resp && null == result) {
					result = resp;
				}
				handler.postHandle(message);
			}
		}
		return result;
	}

	public List<MessageHandler> getMessageHandlers() {
		return messageHandlers;
	}

	public void setMessageHandlers(List<MessageHandler> messageHandlers) {
		this.messageHandlers = messageHandlers;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

}
