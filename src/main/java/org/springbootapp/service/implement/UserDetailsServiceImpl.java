package org.springbootapp.service.implement;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.CartItem;
import org.springbootapp.entity.ProductEntity;
import org.springbootapp.entity.UserEntity;
import org.springbootapp.repository.IProductRepository;
import org.springbootapp.repository.IRoleRepository;
import org.springbootapp.repository.IUserRepository;
import org.springbootapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, IUserService {

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IProductRepository productRepository;

	@Autowired
	IRoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserEntity user;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		return UserDetailsImpl.build(user);
	}

	@Override
	public UserEntity save(UserEntity user) {
//		if (userRepository.existsByUsername(signupRequest.getUsername())) {
//			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Username is already taken!"));
//		}
//		if (userRepository.existsByEmail(signupRequest.getEmail())) {
//			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Email is already in use!"));
//		}
//		UserEntity user = new UserEntity(signupRequest.getUsername(), signupRequest.getEmail(),
//				encoder.encode(signupRequest.getPassword()));
//		Set<String> strRoles = signupRequest.getRole();
//		Set<RoleEntity> roles = new HashSet<>();
//		if (strRoles == null) {
//			RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("ERR: Role is not found!"));
//			roles.add(userRole);
//		} else {
//
//			strRoles.forEach(role -> {
//				switch (role) {
//				case "admin":
//					RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//							.orElseThrow(() -> new RuntimeException("ERR: Role is not found!"));
//					roles.add(adminRole);
//					break;
//				case "mod":
//					RoleEntity modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//							.orElseThrow(() -> new RuntimeException("ERR: Role is not found!"));
//					roles.add(modRole);
//					break;
//				default:
//				case "user":
//					RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
//							.orElseThrow(() -> new RuntimeException("ERR: Role is not found!"));
//					roles.add(userRole);
//					break;
//				}
//			});
//		}
//		user.setRoles(roles);

		return userRepository.save(user);
	}

	@Override
	public List<UserEntity> getAll() {
		return userRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		if(userRepository.findById(id).isPresent())
			userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void update(Long id, UserEntity user) {
		if (userRepository.findById(id).isPresent()) {
			this.user = userRepository.findById(id).get();
			this.user.setPassword(user.getPassword());
			this.user.setUsername(user.getUsername());
			this.user.setAddress(user.getAddress());
			this.user.setEmail(user.getEmail());
			this.user.setPhone(user.getPhone());
			
			userRepository.save(this.user);

		}		
	}
	
	@Override
	public void updatePassword(String password, Long id) {
		userRepository.updatePassword(password, id);
	}

	@Override
	public Optional findUserByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}
	
	@Override
	public Boolean existsByUsername(String username) {
		if (userRepository.existsByUsername(username)) {
			return true;
		} else
			return false;
	}

	@Override
	public Boolean existsByEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			return true;
		} else
			return false;
	}


	@Override
	@Transactional
	public void addItemToCart(Long userID, CartItem item) {
		ProductEntity mergeProduct = productRepository.getOne(item.getProduct().getId());
		userRepository.findByIdWithItemsGraph(userID)
				.ifPresent(customer -> customer.addCartItem(mergeProduct, item.getQuantity()));
	}

	@Override
	public Optional<UserEntity> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	



}
