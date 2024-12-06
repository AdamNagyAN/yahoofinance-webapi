package com.adamnagyan.yahoofinancewebapi.common.exceptions;

import org.springframework.http.HttpStatus;

public class BaseAppExceptionFactory {

	public static BaseAppException forbidden() {
		throw new BaseAppException(ErrorCode.OO_PERMISSION_DENIED.name(), "Permission denied",
				HttpStatus.FORBIDDEN.value());
	}

	public static BaseAppException externalService(String description, Integer statusCode) {
		throw new BaseAppException("OO_EXTERNAL_SERVICE_ERROR", description, statusCode);
	}

	public static BaseAppException externalService() {
		throw new BaseAppException("OO_EXTERNAL_SERVICE_ERROR", "External service error",
				HttpStatus.SERVICE_UNAVAILABLE.value());
	}

}
