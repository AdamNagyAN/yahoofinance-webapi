package com.adamnagyan.yahoofinancewebapi.api.v1.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {

	private String email;

	private String password;

}
