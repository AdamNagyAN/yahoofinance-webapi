package com.adamnagyan.yahoofinancewebapi.services.captcha;

import com.adamnagyan.yahoofinancewebapi.model.captcha.RecaptchaResponse;

public interface RecaptchaService {
  public RecaptchaResponse validateToken(String recaptchaToken);
}
