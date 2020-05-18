package org.decembrist.videomagic.controller;

import org.decembrist.videomagic.configuration.jwt.TokenUtil;
import org.decembrist.videomagic.dto.AuthResultData;
import org.decembrist.videomagic.dto.JwtBody;
import org.decembrist.videomagic.service.JwtUserDetailsService;

import org.decembrist.videomagic.utils.HttpUtils;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
	public AuthResultData createAuthenticationToken(@RequestBody JwtBody authenticationRequest,
													HttpServletResponse response) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = tokenUtil.generateToken(userDetails);
		final var accessToken = new Cookie(HttpUtils.ACCESS_TOKEN_COOKIE_KEY, token);
        accessToken.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(accessToken);
		return new AuthResultData();
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
