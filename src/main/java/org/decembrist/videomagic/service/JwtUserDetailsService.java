package org.decembrist.videomagic.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("user".equals(username)) {
			return new User("user", "$2y$12$guRtzOqCPbQhDcMawqCNReuNuQgvNvZVfk3bgX4OaQdL5BOsHsbOi", Collections.emptyList());
		} else {
			throw new UsernameNotFoundException("Nepravilny parol");
		}
	}

}
