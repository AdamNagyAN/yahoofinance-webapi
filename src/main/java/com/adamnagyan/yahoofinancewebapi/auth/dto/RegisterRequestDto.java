package com.adamnagyan.yahoofinancewebapi.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

	@NotBlank(message = "Parameter must not be blank")
	@NotNull
	@Size(min = 2, max = 255, message = "Size must be between 2-255 chars")
	private String firstname;

	@NotBlank(message = "Parameter must not be blank")
	@NotNull
	@Size(min = 2, max = 255, message = "Size must be between 2-255 chars")
	private String lastname;

	@NotBlank(message = "Parameter must not be blank")
	@NotNull
	@Email
	@Size(max = 255)
	private String email;

	@NotBlank(message = "Parameter must not be blank")
	@NotNull
	@Size(min = 6, max = 255, message = "Size must be between 6-255 chars")
	private String password;

}
