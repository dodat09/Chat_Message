package com.demo.chatMessage.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.demo.chatMessage.Service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	
	public WebSecurityConfig(UserDetailServiceImpl userDetailService) {
		
		this.userDetailService = userDetailService;
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	   @Override
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//	   auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
//  }
//	
	
		@Bean 
		public SecurityFilterChain configure(HttpSecurity http) throws Exception{
			 http
				.csrf().disable()
				.authorizeRequests()
				   .requestMatchers("/css/main","/css/mainLogin","/js/**").permitAll()
				   .requestMatchers("/login","/sighup").permitAll()
			       .requestMatchers("/index").authenticated()
			       .anyRequest().permitAll()
			    .and()
			    .formLogin()
			       .loginProcessingUrl("/j_spring_security_check")
			       .loginPage("/login").permitAll()
			       .usernameParameter("username")
			       .passwordParameter("password")
			       .defaultSuccessUrl("/",true)
			       .failureUrl("/login?success=fail")
			       .permitAll()
			    .and()
			    .logout()
			      .logoutUrl("/logout")
			      .logoutSuccessUrl("/login")
			      .permitAll();
			     
			    http.userDetailsService(userDetailService);
			    //http.passwordManagement(authenticationProvider());
			    http.authenticationProvider(authenticationProvider());
			    return http.build();
		}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
			
}
