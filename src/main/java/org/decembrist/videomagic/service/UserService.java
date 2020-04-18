package org.decembrist.videomagic.service;

import org.decembrist.videomagic.domain.User;
import org.decembrist.videomagic.repository.UserRepository;
import org.decembrist.videomagic.service.exception.UserException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private PasswordEncoder encoder;

	public UserService(UserRepository userRepository,
					   PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	@Transactional
	public void register(String username, String password) throws UserException {
		final boolean nameExists = userRepository.existsByUsername(username);
		if (nameExists) {
			throw new UserException("Username exists");
		}
		final User user = new User(username, encoder.encode(password));
		userRepository.save(user);
	}

}
