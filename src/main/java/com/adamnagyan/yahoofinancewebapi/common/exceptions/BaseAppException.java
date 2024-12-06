package com.adamnagyan.yahoofinancewebapi.common.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BaseAppException extends RuntimeException {

	private String code;

	private Integer httpStatus;

	private transient Map<String, Object> attributes = new HashMap<>();

	protected BaseAppException() {
	}

	public BaseAppException(String code, String message) {
		this(code, message, 400);
	}

	public BaseAppException(String code, String message, Integer httpStatus) {
		super(message);
		this.code = code;
		this.httpStatus = httpStatus;
	}

	protected BaseAppException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	protected BaseAppException(String code, String message, Map<String, Object> attributes) {
		this(code, message, null, attributes);
	}

	protected BaseAppException(String code, String message, Integer httpStatus, Map<String, Object> attributes) {
		super(message);
		this.code = code;
		this.httpStatus = httpStatus;
		this.attributes = attributes;
	}

	protected BaseAppException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	protected BaseAppException(String code, String message, Integer httpStatus, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.httpStatus = httpStatus;
	}

}
