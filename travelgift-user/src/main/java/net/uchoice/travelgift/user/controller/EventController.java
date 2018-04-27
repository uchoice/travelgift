/**
 * 
 */
package net.uchoice.travelgift.user.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.uchoice.travelgift.user.util.DateUtils;

/**
 * @author ruiliang.mrl
 *
 */
@RestController
@RequestMapping("/user")
public class EventController {

	private static final Logger log = LoggerFactory.getLogger(EventController.class);

	@RequestMapping("/log")
	public Boolean log(@RequestParam(value = "openId", required = false) String openId,
			@RequestParam(value = "event", required = false) String event,
			@RequestParam(value = "info", required = false) String info) {
		log.info(String.format("[%s] [%s] [%s] [%s]", DateUtils.dateFormat(new Date()), openId, event, info));
		return true;
	}
}
