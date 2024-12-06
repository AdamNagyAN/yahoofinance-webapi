package com.adamnagyan.yahoofinancewebapi.common.services;

import com.adamnagyan.yahoofinancewebapi.common.model.RecaptchaResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class RecaptchaServiceMock implements RecaptchaService {

	@Override
	public RecaptchaResponse validateToken(String recaptchaToken) {
		return RecaptchaResponse.builder().success(true).score(0.8).build();
	}

}
