package org.springbootapp.config;

import org.springbootapp.entity.CategoryEntity;
import org.springbootapp.entity.ProductEntity;
import org.springbootapp.entity.RoleEntity;
import org.springbootapp.entity.UserEntity;
import org.springbootapp.security.jwt.JwtAuthenticationFilter;
import org.springbootapp.service.implement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// Get AuthenticationManager bean
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserEntity user() {
		return new UserEntity();
	}

	@Bean
	public ProductEntity product() {
		return new ProductEntity();
	}

	@Bean
	public RoleEntity role() {
		return new RoleEntity();
	}

	@Bean
	public CategoryEntity category() {
		return new CategoryEntity();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService) // Cung cáp userservice cho spring security
				.passwordEncoder(passwordEncoder()); // cung cấp password encoder
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			csrf().disable()
			.authorizeRequests().antMatchers("/login").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers(HttpMethod.GET,"/users").authenticated()
          		.antMatchers(HttpMethod.PUT,"/users").authenticated()
          		.antMatchers(HttpMethod.DELETE,"/users/").authenticated()

				.antMatchers(HttpMethod.GET, "/products").permitAll()
			.anyRequest().permitAll();
		// Thêm một lớp Filter kiểm tra jwt
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
