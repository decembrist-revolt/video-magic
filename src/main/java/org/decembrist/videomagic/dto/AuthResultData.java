package org.decembrist.videomagic.dto;

public class AuthResultData {

	private final String message;

	public AuthResultData(String token) {
		this.message = token;
	}

	public AuthResultData() {
		this.message = "OK";
	}

	public String getMessage() {
		return message;
	}

}
