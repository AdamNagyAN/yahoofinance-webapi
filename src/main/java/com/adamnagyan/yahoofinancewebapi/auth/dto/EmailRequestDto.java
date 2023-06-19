package com.adamnagyan.yahoofinancewebapi.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {

	@NotBlank(message = "Parameter must not be blank")
	@NotNull
	@Email
	@Size(max = 255)
	private String email;

}
