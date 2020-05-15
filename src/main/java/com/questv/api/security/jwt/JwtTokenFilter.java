package com.questv.api.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(final JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(final ServletRequest request,
                       final ServletResponse response,
                       final FilterChain chain) throws IOException,
      ServletException {

    final String token = jwtTokenProvider.resolveToken(request);
    if (token != null && jwtTokenProvider.isAValidToken(token)) {
      final Authentication auth = jwtTokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    chain.doFilter(request, response);
  }
}
