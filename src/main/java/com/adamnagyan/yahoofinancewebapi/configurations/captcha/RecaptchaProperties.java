package com.adamnagyan.yahoofinancewebapi.configurations.captcha;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.recaptcha.key")
public record RecaptchaProperties(String verifyUrl, String secretKey) {
}
