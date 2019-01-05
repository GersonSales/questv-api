package com.questv.api.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  private String name;
  private String email;
  private String password;

  public User() {
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
}
