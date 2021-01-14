package org.springbootapp.api;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springbootapp.dto.ForgotPasswordRequest;
import org.springbootapp.repository.IUserRepository;
import org.springbootapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailAPI {
	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	IUserService userService;

	@Autowired
	IUserRepository userRepository;

	@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	public boolean sendMail(@RequestBody ForgotPasswordRequest forgotPassword) {

		boolean multipart = true;
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, multipart, "UTF-8");
			if (userRepository.findByEmail(forgotPassword.getEmail()) != null) {

				mimeMessageHelper.setTo(forgotPassword.getEmail());
				String htmlMsg = "<h3>Spring Boot</h3>"
						+ "<img src='https://upload.wikimedia.org/wikipedia/commons/0/07/Flag_of_Vietnam-Animated.gif'>";
				mimeMessage.setSubject("Forgot password: " + forgotPassword.getEmail());
				mimeMessage.setContent(htmlMsg, "text/html");
				this.javaMailSender.send(mimeMessage);
				System.out.println("Successfully");
				return true;
			}
			System.out.println("Error");
			return false;

		} catch (MessagingException e) {
			System.out.println("Error");
			e.printStackTrace();
			return false;
		}

	}

}
