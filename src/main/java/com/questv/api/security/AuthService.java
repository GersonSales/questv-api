package com.questv.api.security;

import com.questv.api.exception.user.UserNotFoundException;
import com.questv.api.security.jwt.JwtTokenProvider;
import com.questv.api.user.UserDTO;
import com.questv.api.user.UserModel;
import com.questv.api.user.UserRepository;
import com.questv.api.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;
  private final UserRepository userRepository;

  public AuthService(final AuthenticationManager authenticationManager,
                     final JwtTokenProvider jwtTokenProvider,
                     final UserService userService,
                     final UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userService = userService;
    this.userRepository = userRepository;
  }

  public Map<String, String> generateTokenMap(
      final AccountCredentialsVO credentialsVO) {
    final String username = credentialsVO.getUsername();

    authenticate(credentialsVO);
    final String token = generateToken(username);

    return getTokenMap(username, token);
  }

  private Map<String, String> getTokenMap(final String username,
                                          final String token) {
    final Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("username", username);
    tokenMap.put("token", token);
    return tokenMap;
  }

  private String generateToken(final String username) {
    final UserModel userModel = getByUsername(username);

    if (userModel != null) {
      return jwtTokenProvider.createToken(username, userModel.getRoles());
    } else {
      throw new UserNotFoundException();
    }

  }

  private UserModel getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  private void authenticate(final AccountCredentialsVO credentialsVO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            credentialsVO.getUsername(),
            credentialsVO.getPassword())
    );
  }

  public UserDTO createAnAccount(final UserDTO userDTO) {
    return userService.create(userDTO);
  }
}
