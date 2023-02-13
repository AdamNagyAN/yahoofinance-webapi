package com.adamnagyan.yahoofinancewebapi.model.captcha;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record RecaptchaResponse(
        Boolean success,
        String challenge_ts,
        String hostname,
        Double score,
        String action
) {

}
