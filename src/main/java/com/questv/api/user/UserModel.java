package com.questv.api.user;

import com.questv.api.user.properties.Name;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_table", schema = "questv_schema")
public class UserModel {

  @Id
  @NotNull
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Embedded
  private final Name name;

  @NotEmpty
  @NotNull
  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Password must have 3 characters at least.")
  @Column(name = "password", nullable = false)
  private String password;

  public UserModel(final Name name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name.getFullName();
  }

  public void setName(final String name) {
    this.name.setFirstName(name);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
