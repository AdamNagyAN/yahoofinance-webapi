package com.adamnagyan.yahoofinancewebapi.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {

	@NotNull
	@NotBlank
	private String token;

	@NotBlank(message = "Parameter must not be blank")
	@NotNull
	@Size(min = 6, max = 255, message = "Size must be between 6-255 chars")
	private String password;

}
