package com.questv.api.user;

import com.questv.api.Convertible;
import com.questv.api.user.properties.Name;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_table", schema = "questv_schema")
public class UserModel implements Convertible<UserDTO> {

  @Id
  @NotNull
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Embedded
  private Name name;

  @NotEmpty
  @NotNull
  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Password must have 3 characters at least.")
  @Column(name = "password", nullable = false)
  private String password;

  public UserModel() { }

  public UserModel(final Name name, final String email, final String password) {
    this.name = name;
    this.email = email;
    this.password = password;
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

  @Override
  public UserDTO convert() {
    final ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(this, UserDTO.class);
  }
}
