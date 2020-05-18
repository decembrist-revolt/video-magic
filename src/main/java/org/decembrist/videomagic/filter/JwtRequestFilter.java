package org.decembrist.videomagic.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.decembrist.videomagic.configuration.jwt.TokenUtil;
import org.decembrist.videomagic.service.JwtUserDetailsService;
import org.decembrist.videomagic.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private TokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws ServletException, IOException {

		final var accessToken = HttpUtils.getAccessToken(request);
		String username = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (accessToken != null) {
			try {
				username = jwtTokenUtil.getUsernameFromToken(accessToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
		    try {
                userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException ex) {
                HttpUtils.removeAccessToken(response);
            }
			// if token is valid configure Spring Security to manually set authentication
			//TODO: Optimizirovatb
			if (userDetails != null && jwtTokenUtil.validateToken(accessToken, userDetails)) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		chain.doFilter(request, response);
	}

}
