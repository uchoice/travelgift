package net.uchoice.travelgift.wechart.handler;

import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;

public interface MessageHandler {

	boolean isEffect(InputMessage message);
	
	int priority();

	void preHandle(InputMessage message);

	BaseMessage handle(InputMessage message);

	void postHandle(InputMessage message);
}
