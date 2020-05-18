package org.decembrist.videomagic.controller;

import org.decembrist.videomagic.dto.RegisterResponseData;
import org.decembrist.videomagic.dto.UserAuthData;
import org.decembrist.videomagic.dto.UserDto;
import org.decembrist.videomagic.service.UserService;
import org.decembrist.videomagic.service.exception.UserException;
import org.decembrist.videomagic.utils.HttpUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/whoami")
	public UserDto whoAmI() {
		final var authentication = SecurityContextHolder.getContext().getAuthentication();
		final var principal = authentication.getPrincipal();
		final String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		return userService.getUserByName(username);
	}

	@PostMapping("/logout")
	public void logout(HttpServletResponse response) {
        HttpUtils.removeAccessToken(response);
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisterResponseData> register(@RequestBody UserAuthData userAuthData) {
		try {
			userService.register(userAuthData.getUsername(), userAuthData.getPassword());
		} catch (UserException ex) {
			final RegisterResponseData responseData = RegisterResponseData.fromException(ex);
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(responseData);
		}
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(RegisterResponseData.ok());
	}

}
