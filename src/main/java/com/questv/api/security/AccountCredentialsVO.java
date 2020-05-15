package com.questv.api.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsVO implements Serializable {

  private static final long serialVersionUID = 4325636326695785764L;


  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccountCredentialsVO)) return false;
    AccountCredentialsVO that = (AccountCredentialsVO) o;
    return Objects.equals(getUsername(), that.getUsername()) &&
        Objects.equals(getPassword(), that.getPassword());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUsername(), getPassword());
  }
}
