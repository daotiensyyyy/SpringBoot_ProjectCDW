package org.springbootapp.service.implement;

import java.util.List;

import javax.transaction.Transactional;

import org.springbootapp.entity.RoleEntity;
import org.springbootapp.entity.UserEntity;
import org.springbootapp.repository.IRoleRepository;
import org.springbootapp.repository.IUserRepository;
import org.springbootapp.security.CustomUserDetails;
import org.springbootapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserEntity userEntity;

	@Autowired
	private RoleEntity role;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(user);
	}
	
	// JWTAuthenticationFilter sẽ sử dụng hàm này
		@Transactional
		public UserDetails loadUserById(Long id) {
//			try {
//				Optional<UserEntity> user = userRepository.findById(id);
//			} catch (Exception e) {
//				return (UserDetails) new UsernameNotFoundException("User not found with id : " + id);
//			}
			UserEntity user = userRepository.findById(id)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
			return new CustomUserDetails(user);
		}

	@Override
	public UserEntity save(UserEntity entity) {

//		entity.setCreatedBy(entity.getCreatedBy());
//		entity.setCreatedDate(entity.getCreatedDate());
//		entity.setModifiedBy(entity.getModifiedBy());
//		entity.setModifiedDate(entity.getModifiedDate());
	
		entity.setUsername(entity.getUsername());
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setUser_email(entity.getUser_email());
		entity.setUser_address(entity.getUser_address());
		entity.setUser_phone(entity.getUser_phone());
		if (roleRepository.findById((long) 1).isEmpty()) {
			entity.addRoles(new RoleEntity("USER"));
		} else {
			entity.addRoles(roleRepository.getOne((long) 1));
		}
		return userRepository.saveAndFlush(entity);
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
			userEntity = userRepository.findById(id).get();
			userEntity.setPassword(user.getPassword());
			userEntity.setUsername(user.getUsername());
			userEntity.setUser_address(user.getUser_address());
			userEntity.setUser_email(user.getUser_email());
			userEntity.setUser_phone(user.getUser_phone());
			
			userRepository.save(userEntity);

		}		
	}

}
