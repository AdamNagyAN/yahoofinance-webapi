package com.adamnagyan.yahoofinancewebapi.controllers.v1;

import com.adamnagyan.yahoofinancewebapi.api.v1.model.auth.RegisterRequestDto;
import com.adamnagyan.yahoofinancewebapi.services.auth.AuthenticationService;
import com.adamnagyan.yahoofinancewebapi.services.auth.AuthenticationServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

class AuthenticationControllerTest {

	WebTestClient webTestClient;

	AuthenticationController authenticationController;

	AuthenticationService authenticationService;

	@Before
	public void setUp() throws Exception {
		authenticationService = Mockito.mock(AuthenticationServiceImpl.class);
		authenticationController = new AuthenticationController(authenticationService);
		webTestClient = WebTestClient.bindToController(authenticationController).build();
	}

	@Test
	void register() {
		RegisterRequestDto registerRequestDto = new RegisterRequestDto();
		registerRequestDto.setEmail("test@gmail.com");
		registerRequestDto.setFirstname("Adam");
		registerRequestDto.setLastname("Nagy");
		registerRequestDto.setPassword("test123");

	}

	@Test
	void login() {
	}

	@Test
	void confirm() {
	}

}