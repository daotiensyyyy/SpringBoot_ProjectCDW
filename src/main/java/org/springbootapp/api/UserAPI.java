package org.springbootapp.api;

import java.util.List;

import javax.validation.Valid;

import org.springbootapp.entity.UserEntity;
import org.springbootapp.security.CustomUserDetails;
import org.springbootapp.security.jwt.JwtTokenProvider;
import org.springbootapp.security.payload.LoginRequest;
import org.springbootapp.security.payload.LoginResponse;
import org.springbootapp.service.implement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAPI {
	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;


	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		// Xác thực từ username và password.
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		// Nếu không xảy ra exception tức là thông tin hợp lệ
		// Set thông tin authentication vào Security Context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Trả về jwt cho người dùng.
		String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
		return new LoginResponse(jwt);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
		try {
			userService.save(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<UserEntity>> getAllUser() {
		List<UserEntity> users = userService.getAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<UserEntity>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateById(@PathVariable("id") Long id, @RequestBody UserEntity newUser) {
		try {
			userService.update(id, newUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
		try {
			userService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
