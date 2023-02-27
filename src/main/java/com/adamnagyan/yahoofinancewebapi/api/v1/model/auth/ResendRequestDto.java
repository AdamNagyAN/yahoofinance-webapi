package com.adamnagyan.yahoofinancewebapi.api.v1.model.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class ResendRequestDto {

	@Email
	private String email;

}
