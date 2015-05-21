package service.controller;

import org.access.impl.entity.User;
import org.access.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/" })
public class Controller {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = { "/register" }, method = { RequestMethod.GET })
	public String registerUser1() {

		return "Hello";
	}

	@RequestMapping(value = { "/get" }, method = { RequestMethod.GET })
	public String getPersonDetail1() {
		return "Hello";
	}

	@RequestMapping(value = { "/register1" }, method = { RequestMethod.GET })
	public User registerUser() {
		User user = new User();

		return user;
	}
}
