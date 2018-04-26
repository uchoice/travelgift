/**
 * 
 */
package net.uchoice.travelgift.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.uchoice.travelgift.user.domain.UserDO;
import net.uchoice.travelgift.user.exception.UserServiceException;
import net.uchoice.travelgift.user.service.UserService;

/**
 * @author ruiliang.mrl
 *
 */
@Controller
@RequestMapping("/user")
public class TestController {

	@Autowired
	UserService userService;

	@RequestMapping("/add")
	public UserDO addUser(@RequestParam("openId") String openId) {
		UserDO u = null;
		try {
			UserDO user = new UserDO();
			user.setOpenId(openId);
			userService.addUser(user);

			System.out.println(userService.checkExistsByOpenId(openId));

			u = userService.getUserByOpenId(openId);
			
		} catch (UserServiceException e) {
			e.printStackTrace();
		}
		return u;
	}
}
