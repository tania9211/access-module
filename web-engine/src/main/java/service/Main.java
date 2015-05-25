package service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


public class Main {

	public static void main(String[] args) throws AddressException, MessagingException{
		//EmailService emailService = new EmailService();
		//emailService.sendEmail("aleshkotaniaa@mail.ru");
		
		SmsService service = new SmsService();
		service.sendSms();
	}

}
