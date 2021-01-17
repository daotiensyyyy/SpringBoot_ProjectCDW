package org.springbootapp.api;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springbootapp.common.ERole;
import org.springbootapp.dto.JwtResponse;
import org.springbootapp.dto.LoginRequest;
import org.springbootapp.dto.MessageResponse;
import org.springbootapp.dto.SignupRequest;
import org.springbootapp.entity.CartItem;
import org.springbootapp.entity.ConfirmationToken;
import org.springbootapp.entity.RoleEntity;
import org.springbootapp.entity.UserEntity;
import org.springbootapp.jwt.JwtUtils;
import org.springbootapp.repository.IConfirmationTokenRepository;
import org.springbootapp.repository.IRoleRepository;
import org.springbootapp.repository.IUserRepository;
import org.springbootapp.service.IEmailService;
import org.springbootapp.service.IRoleService;
import org.springbootapp.service.IUserService;
import org.springbootapp.service.implement.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class UserAPI {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IUserService userService;

	@Autowired
	IRoleRepository roleRepository;

	@Autowired
	IRoleService roleService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	IConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	IEmailService emailService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getPassword(), roles));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest,
			HttpServletRequest request) {
		if (userService.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Username is already taken!"));
		}
		if (userService.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Email is already in use!"));
		}
		UserEntity user = new UserEntity(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()), signupRequest.getAddress(), signupRequest.getPhone());
		Set<String> strRoles = signupRequest.getRole();
		Set<RoleEntity> roles = new HashSet<>();
		if (strRoles == null) {
			RoleEntity userRole = roleService.findRoleByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("ERR: Role is not found!"));
			roles.add(userRole);
		} else {

			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					RoleEntity adminRole = roleService.findRoleByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("ERR: Role is not found!"));
					roles.add(adminRole);
					break;
				default:
				case "user":
					RoleEntity userRole = roleService.findRoleByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("ERR: Role is not found!"));
					roles.add(userRole);
					break;
				}
			});
		}
		user.setRoles(roles);
		userService.save(user);

		ConfirmationToken confirmationToken = new ConfirmationToken(user);

		confirmationTokenRepository.save(confirmationToken);

		String appUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("lacduong953@gmail.com");
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setText("To confirm your account, please click here : " + appUrl + "/confirm-account?token="
				+ confirmationToken.getConfirmationToken());
		emailService.sendEmail(mailMessage);
		return new ResponseEntity<>(confirmationToken.getConfirmationToken(),HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserEntity>> getAllUser() {
		List<UserEntity> users = userService.getAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<UserEntity>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> updateById(@PathVariable("id") Long id, @RequestBody UserEntity newUser) {
		try {
			userService.update(id, newUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
		try {
			userService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/users/{cid}/cart-items")
	public void addItemToCart(@PathVariable("cid") Long customerID, @RequestBody CartItem item) {
		userService.addItemToCart(customerID, item);
	}

	@RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity confirmUserAccount(@RequestBody Map<String, String> requestPa) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(requestPa.get("token"));

		if (token != null) {
			Optional<UserEntity> user = userRepository.findByEmail(token.getUser().getEmail());
			user.get().setEnabled(true);
			userService.save(user.get());
			return new ResponseEntity<>(null, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
