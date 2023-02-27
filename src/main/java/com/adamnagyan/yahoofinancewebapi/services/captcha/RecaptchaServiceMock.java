package com.adamnagyan.yahoofinancewebapi.services.captcha;

import com.adamnagyan.yahoofinancewebapi.model.captcha.RecaptchaResponse;
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
