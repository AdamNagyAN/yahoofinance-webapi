package com.adamnagyan.yahoofinancewebapi.common.services;

import com.adamnagyan.yahoofinancewebapi.common.model.RecaptchaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("!dev")
public class RecaptchaServiceImpl implements RecaptchaService {

	@Value("${google.recaptcha.key.secret-key}")
	private String secretKey;

	@Value("${google.recaptcha.key.verify-url}")
	private String verifyUrl;

	private final RestTemplate restTemplate;

	public RecaptchaResponse validateToken(String recaptchaToken) {

		// https://www.google.com/recaptcha/api/siteverify METHOD: POST
		// secret Required. The shared key between your site and reCAPTCHA.
		// response Required. The user response token provided by the reCAPTCHA
		// client-side integration on your site.

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("secret", secretKey);
		map.add("response", recaptchaToken);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		log.info("Sending reCaptcha request to: " + verifyUrl);
		ResponseEntity<RecaptchaResponse> response = restTemplate.exchange(verifyUrl, HttpMethod.POST, entity,
				RecaptchaResponse.class);

		return response.getBody();
	}

}
