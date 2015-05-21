package service.controller;

import org.access.impl.UserServiceImpl;
import org.access.impl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/" })
public class Controller {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@RequestMapping(value = { "/register" }, method = { RequestMethod.GET })
	public User registerUser1(@RequestParam("nickname") String nickname,
			@RequestParam("email") String email) {

		User user = userServiceImpl.create(nickname, email);

		return user;
	}

	@RequestMapping(value = { "/register1" }, method = { RequestMethod.GET })
	public User registerUser() {
		User user = new User();

		return user;
	}
}
