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
public class LocationController {

	private static final Logger log = LoggerFactory
			.getLogger("net.uchoice.travelgift.wechart.handler.event.LocationEventHandler");

	@RequestMapping("/location")
	public Boolean log(@RequestParam(value = "openId", required = false) String openId,
			@RequestParam(value = "longitude", required = false) String longitude,
			@RequestParam(value = "latitude", required = false) String latitude,
			@RequestParam(value = "accuracy", required = false) String accuracy) {
		log.info(String.format("[LOCATION] t[%s] u[%s] lon[%s] lat[%s] p[%s]", DateUtils.dateFormat(new Date()), openId,
				longitude, latitude, accuracy));
		return true;
	}
}
