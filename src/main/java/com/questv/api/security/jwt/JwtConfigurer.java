package com.questv.api.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public final class JwtConfigurer
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,
    HttpSecurity> {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtConfigurer(final JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void configure(final HttpSecurity httpSecurity) {
    final JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
    httpSecurity
        .addFilterBefore(
            jwtTokenFilter,
            UsernamePasswordAuthenticationFilter.class
        );
  }


}
