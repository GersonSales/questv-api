package com.questv.api.security;


import com.questv.api.exception.credential.CustomizedBadCredentialsException;
import com.questv.api.exception.user.AccountCreationException;
import com.questv.api.user.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(final AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/sign-in")
  public ResponseEntity<Map<String, String>> signIn(
      @RequestBody final AccountCredentialsVO credentialsVO) {

    try {
      return ResponseEntity.ok(authService.generateTokenMap(credentialsVO));
    } catch (final AuthenticationException exception) {
      throw new CustomizedBadCredentialsException(exception);
    }
  }

  @PostMapping("/sign-up")
  public ResponseEntity<UserDTO> signUp(@RequestBody final UserDTO userDTO) {
    try {
      final UserDTO createdUser = this.authService.createAnAccount(userDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    } catch (final Exception exception) {
      throw new AccountCreationException(exception);
    }

  }
}
