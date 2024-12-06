package com.adamnagyan.yahoofinancewebapi.common.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends Exception {

	private final String argument;

	public BadRequestException(String argument, String message) {
		super(message);
		this.argument = argument;
	}

}
