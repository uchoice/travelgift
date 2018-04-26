/**
 * 
 */
package net.uchoice.travelgift.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.uchoice.travelgift.user.service.UserService;

/**
 * @author ruiliang.mrl
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping("/checkExists")
	public Boolean addUser(@RequestParam("openId") String openId) {
		return userService.checkExistsByOpenId(openId);
	}
}
