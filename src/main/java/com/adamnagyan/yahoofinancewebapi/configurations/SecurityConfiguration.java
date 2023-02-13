package com.adamnagyan.yahoofinancewebapi.configurations;

import com.adamnagyan.yahoofinancewebapi.configurations.captcha.RecaptchaFilter;
import com.adamnagyan.yahoofinancewebapi.services.captcha.RecaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  private static final String[] AUTH_WHITELIST = {
          "/swagger-ui/**",
          "/swagger-resources/**",
          "/swagger-ui.html",
          "/v2/api-docs",
          "/webjars/**",
          "/api/v1/auth/**",
          "/api/v1/symbol/**",
          "/h2-console/**"
  };


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, RecaptchaService recaptchaService) throws Exception {
    http
            .cors().and().csrf()
            .disable()
            .authorizeHttpRequests()
            .antMatchers(AUTH_WHITELIST)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(new RecaptchaFilter(recaptchaService), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
