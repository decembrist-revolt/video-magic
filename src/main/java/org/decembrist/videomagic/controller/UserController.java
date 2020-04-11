package org.decembrist.videomagic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@PostMapping("/login")
	public String login() {
		return "login";
	}

}
