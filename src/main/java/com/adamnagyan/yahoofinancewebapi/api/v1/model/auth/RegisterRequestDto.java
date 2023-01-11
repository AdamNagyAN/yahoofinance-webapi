package com.adamnagyan.yahoofinancewebapi.api.v1.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
  private String firstname;
  private String lastname;
  private String email;
  private String password;
}
