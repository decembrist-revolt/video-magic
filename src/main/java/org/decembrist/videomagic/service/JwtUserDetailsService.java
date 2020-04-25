package org.decembrist.videomagic.service;

import org.decembrist.videomagic.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public JwtUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Optional<org.decembrist.videomagic.domain.User> user
				= userRepository.findByUsername(username);
		if (user.isPresent()) {
			final String dbUsername = user.get().getUsername();
			final String dbPassword = user.get().getPassword();
			return new User(dbUsername, dbPassword, Collections.emptyList());
		} else {
			throw new UsernameNotFoundException("Nepravilny parol");
		}
	}

}
