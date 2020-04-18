package org.decembrist.videomagic.controller;

import org.decembrist.videomagic.dto.RegisterResponseData;
import org.decembrist.videomagic.dto.UserAuthData;
import org.decembrist.videomagic.service.UserService;
import org.decembrist.videomagic.service.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.datatransfer.MimeTypeParseException;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@PostMapping("/login")
	public String login() {
		return "login";
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
