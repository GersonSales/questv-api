package com.questv.api.user;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class UserPayload extends User {

  private final String id;


  public UserPayload(final String id,
                     final String username,
                     final String password,
                     final Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.id = id;
  }

  public UserPayload(final String id,
                     final String username,
                     final String password,
                     final boolean enabled,
                     final boolean accountNonExpired,
                     final boolean credentialsNonExpired,
                     final boolean accountNonLocked,
                     final Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
