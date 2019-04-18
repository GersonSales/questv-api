package com.questv.api.security;

public class SecurityConstants {
  public static final String SECRET = "SecretKeyToGenJWTs";
  public static final long EXPIRATION_TIME = 864_000_000; // 10 days
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/users/sign-up";
  public static final String SERIES_URL = "/series/**" ;
  public static final String SEASONS_URL = "/seasons";
  public static final String EPISODES_URL = "/episodes";
    public static final String QUESTIONS_URL = "/questions";
    public static final String HEROKU_URL = "/**";
}
