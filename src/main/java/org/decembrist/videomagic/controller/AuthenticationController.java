package org.decembrist.videomagic.controller;

import org.decembrist.videomagic.configuration.jwt.TokenUtil;
import org.decembrist.videomagic.dto.JwtBody;
import org.decembrist.videomagic.dto.JwtToken;
import org.decembrist.videomagic.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final TokenUtil tokenUtil;
	private final JwtUserDetailsService userDetailsService;

	public AuthenticationController(AuthenticationManager authenticationManager,
									TokenUtil tokenUtil,
									JwtUserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.tokenUtil = tokenUtil;
		this.userDetailsService = userDetailsService;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public JwtToken createAuthenticationToken(@RequestBody JwtBody authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = tokenUtil.generateToken(userDetails);
		return new JwtToken(token);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

	}

}
