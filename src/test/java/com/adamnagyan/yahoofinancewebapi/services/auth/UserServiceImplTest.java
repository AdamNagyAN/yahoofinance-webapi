package com.adamnagyan.yahoofinancewebapi.services.auth;

import com.adamnagyan.yahoofinancewebapi.auth.model.User;
import com.adamnagyan.yahoofinancewebapi.auth.respository.UserRepository;
import com.adamnagyan.yahoofinancewebapi.auth.service.UserService;
import com.adamnagyan.yahoofinancewebapi.auth.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	UserService userService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		userService = new UserServiceImpl(userRepository);
	}

	@Test(expected = UsernameNotFoundException.class)
  public void getUserByEmailNotFound() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
    userService.getUserByEmail("test@gmail.com");
  }

	@Test
  public void getUserByEmail() {
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(User.builder().build()));
    userService.getUserByEmail("test@gmail.com");
    verify(userRepository).findByEmail("test@gmail.com");
  }

	@Test
	public void updateUser() {
		User user = User.builder().build();
		userService.updateUser(user);
		verify(userRepository).save(user);
	}

	@Test
  public void existsByEmailTrue() {
    when(userRepository.existsByEmail(anyString())).thenReturn(true);
    Assert.assertEquals(userService.existsByEmail("test@gmail.com"), true);
    verify(userRepository).existsByEmail("test@gmail.com");
  }

	@Test
  public void existsByEmailFalse() {
    when(userRepository.existsByEmail(anyString())).thenReturn(false);
    Assert.assertEquals(userService.existsByEmail("test@gmail.com"), false);
    verify(userRepository).existsByEmail("test@gmail.com");
  }

}