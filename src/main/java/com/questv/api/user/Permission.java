package com.questv.api.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permissions")
public final class Permission implements Serializable, GrantedAuthority {
  private static final long serialVersionUID = 1629243664808000454L;


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "authority")
  private String authority;

  @Override
  public String getAuthority() {
    return this.authority;
  }
}
