package com.adamnagyan.yahoofinancewebapi.common.configurations.captcha;

import com.adamnagyan.yahoofinancewebapi.common.exceptions.RecaptchaException;
import com.adamnagyan.yahoofinancewebapi.common.model.RecaptchaResponse;
import com.adamnagyan.yahoofinancewebapi.common.services.RecaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class RecaptchaFilter extends OncePerRequestFilter {

	private final RecaptchaService recaptchaService;

	@Override
	@SneakyThrows
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
		String recaptcha = request.getHeader("recaptcha");
		RecaptchaResponse recaptchaResponse = recaptchaService.validateToken(recaptcha);
		log.info("reCaptcha response is:" + recaptchaResponse.toString());
		// recaptcha score check
		if (!recaptchaResponse.success()) {
			log.info("Invalid reCaptcha token");
			throw new RecaptchaException("Invalid reCaptcha token");
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return !(path.startsWith("/api/v1/auth") && request.getMethod().equals("POST"));
	}

}
