package com.questv.api.security.jwt;

import com.questv.api.exception.InvalidJwtAuthenticationException;
import com.questv.api.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public final class JwtTokenProvider {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER = "Bearer";

  @Value("${security.jwt.token.secret-key:secret}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length:3600000}")
  private long validityInMilliSeconds;

  private final UserService userService;

  public JwtTokenProvider(final UserService userService) {
    this.userService = userService;
  }

  @PostConstruct
  public void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(final String username,
                            final List<String> roles) {
    final Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", roles);

    final Date now = new Date();
    final Date tokenValidity = new Date(now.getTime() + validityInMilliSeconds);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(tokenValidity)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public Authentication getAuthentication(final String token) {
    final UserDetails userDetails = getUserDetails(token);
    return new UsernamePasswordAuthenticationToken(
        userDetails,
        "",
        userDetails.getAuthorities());
  }

  private UserDetails getUserDetails(final String token) {
    return this.userService.loadUserByUsername(getUsername(token));
  }

  private String getUsername(final String token) {
    return getClaims(token)
        .getBody()
        .getSubject();
  }

  private Jws<Claims> getClaims(final String token) {
    return Jwts
        .parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token);
  }

  public String resolveToken(final HttpServletRequest request) {
    String token = null;
    final String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (bearerToken != null && bearerToken.startsWith(BEARER)) {
      token = bearerToken.substring(BEARER.length() + 1);
    }
    return token;
  }

  public boolean isAValidToken(final String token) {
    boolean isAValidToken;
    try {
      isAValidToken = token != null && !isExpired(token);
    } catch (final Exception exception) {
      throw new InvalidJwtAuthenticationException(exception.getMessage());
    }

    return isAValidToken;
  }

  public boolean isExpired(final String token) {
    return getClaims(token).getBody().getExpiration().before(new Date());
  }


  public String resolveToken(final ServletRequest request) {
    return resolveToken((HttpServletRequest) request);
  }
}
