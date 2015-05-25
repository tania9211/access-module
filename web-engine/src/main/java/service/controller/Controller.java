package service.controller;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.access.impl.UserServiceImpl;
import org.access.impl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.EmailService;
import service.SmsService;

@RestController
@RequestMapping({ "/" })
public class Controller {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private EmailService emailService;

	@Autowired
	private SmsService smsService;

	@RequestMapping(value = { "/register" }, method = { RequestMethod.GET })
	public User registerUser(@RequestParam("nickname") String nickname,
			@RequestParam("email") String email) {

		User user = userServiceImpl.create(nickname, email);

		return user;
	}

	@RequestMapping(value = { "/email" }, method = { RequestMethod.GET })
	public String sendEmail(@RequestParam("email") String email)
			throws AddressException, MessagingException {
		emailService.sendEmail(email);

		return "Send email";
	}

	@RequestMapping(value = { "/sms" }, method = { RequestMethod.GET })
	public String sendSMS() {
		smsService.sendSms();
		return "Send sms";
	}
}
