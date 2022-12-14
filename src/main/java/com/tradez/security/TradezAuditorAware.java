package com.tradez.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class TradezAuditorAware implements AuditorAware<String> {

	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		if(authentication.getPrincipal() instanceof String) {
			return Optional.of((String)authentication.getPrincipal());
		}
		UserDetails user = (UserDetails)authentication.getPrincipal();
		return Optional.of(user.getUsername());
	}
}