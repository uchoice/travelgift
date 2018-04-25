package net.uchoice.travelgift.wechart.manager;

import net.uchoice.travelgift.wechart.model.request.InputMessage;
import net.uchoice.travelgift.wechart.model.response.BaseMessage;

public interface MessageManager {

	BaseMessage process(InputMessage message);
}
