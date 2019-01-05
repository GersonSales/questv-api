package com.questv.api.user;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_table")
public class UserModel {

  @Id
  @NotNull
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @NotEmpty
  @NotNull
  @Size(min = 3, max = 256, message = "Name should have 3 characters at least.")
  private String name;

  @NotEmpty
  @NotNull
  @Email
  private String email;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Password must have 3 characters at least.")
  private String password;

  public UserModel() {
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
