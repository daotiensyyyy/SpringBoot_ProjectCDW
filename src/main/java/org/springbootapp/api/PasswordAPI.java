package org.springbootapp.api;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springbootapp.entity.UserEntity;
import org.springbootapp.service.IEmailService;
import org.springbootapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController

public class PasswordAPI {
	@Autowired
	private IUserService userService;

	@Autowired
	private IEmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Process form submission from forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity processForgotPasswordForm(@RequestBody Map<String,String> requestPa, HttpServletRequest request) {

		// Lookup user in database by e-mail
		Optional<UserEntity> optional = userService.findUserByEmail(requestPa.get("email"));

		if (!optional.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {

			// Generate random 36-character string token for reset password
			UserEntity user = optional.get();
			user.setResetToken(UUID.randomUUID().toString());

			// Save token to database
			userService.save(user);

			String appUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");

			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + "/reset?token="
					+ user.getResetToken());

			emailService.sendEmail(passwordResetEmail);

			// Add success message to view
			return new ResponseEntity<>(user.getResetToken(), HttpStatus.OK);
		}

	}

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ResponseEntity setNewPassword(@RequestBody Map<String, String> requestParams, RedirectAttributes redir) {

		// Find the user associated with the reset token
		Optional<UserEntity> user = userService.findUserByResetToken(requestParams.get("token"));

		// This should always be non-null but we check just in case
		if (user.isPresent()) {

			UserEntity resetUser = user.get();

			// Set new password
			resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

			// Set the reset token to null so it cannot be used again
			resetUser.setResetToken(null);

			// Save user
			userService.save(resetUser);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

			return new ResponseEntity<>(null, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

		}
	}
}
