package com.adamnagyan.yahoofinancewebapi.common.services;

import com.adamnagyan.yahoofinancewebapi.common.model.RecaptchaResponse;

public interface RecaptchaService {

	public RecaptchaResponse validateToken(String recaptchaToken);

}
