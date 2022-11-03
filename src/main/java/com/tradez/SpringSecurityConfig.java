package com.tradez;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.tradez.security.CustomUserDetailsService;
import com.tradez.security.TradezAuditorAware;

@Configuration
public class SpringSecurityConfig {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new TradezAuditorAware();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests().antMatchers("/dashboard/**").authenticated().anyRequest().permitAll().and();
		http.formLogin().loginPage("/login").usernameParameter("username").defaultSuccessUrl("/dashboard").failureUrl("/login?error").permitAll();
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
		
		return http.build();
	}
}
