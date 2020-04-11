package org.decembrist.videomagic.dto;

public class JwtToken {

	private final String token;

	public JwtToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}
