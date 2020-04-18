package org.decembrist.videomagic.dto;

import org.decembrist.videomagic.service.exception.UserException;

public class RegisterResponseData {

	private String message;

	public RegisterResponseData() {
	}

	private RegisterResponseData(String message) {
		this.message = message;
	}

	public static RegisterResponseData ok() {
		return new RegisterResponseData("OK");
	}

	public static RegisterResponseData fromException(UserException ex) {
		return new RegisterResponseData(ex.getMessage());
	}

	public String getMessage() {
		return message;
	}
}
