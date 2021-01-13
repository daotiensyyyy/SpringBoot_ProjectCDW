package org.springbootapp.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springbootapp.common.ERole;
import org.springbootapp.dto.JwtResponse;
import org.springbootapp.dto.LoginRequest;
import org.springbootapp.dto.MessageResponse;
import org.springbootapp.dto.SignupRequest;
import org.springbootapp.entity.RoleEntity;
import org.springbootapp.entity.UserEntity;
import org.springbootapp.jwt.JwtUtils;
import org.springbootapp.repository.IRoleRepository;
import org.springbootapp.repository.IUserRepository;
import org.springbootapp.service.IRoleService;
import org.springbootapp.service.IUserService;
import org.springbootapp.service.implement.UserDetailsImpl;

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
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {
		if (userService.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Username is already taken!"));
		}
		if (userService.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Email is already in use!"));
		}
		UserEntity user = new UserEntity(signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()),
				signupRequest.getAddress(), signupRequest.getPhone());
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
		return ResponseEntity.ok(new MessageResponse("Successfull"));
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
}
