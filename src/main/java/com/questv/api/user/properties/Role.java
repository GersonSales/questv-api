package com.questv.api.user.properties;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {



  @Override
  public String getAuthority() {
    return null;
  }
}
