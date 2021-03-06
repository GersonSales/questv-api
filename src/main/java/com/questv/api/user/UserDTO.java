package com.questv.api.user;

import com.questv.api.answered.question.AnsweredQuestionModel;
import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Identifiable;
import com.questv.api.contracts.Updatable;
import com.questv.api.user.properties.Name;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserDTO implements Convertible<UserModel>, Updatable<UserModel>, Identifiable {


  private String id;

  @NotEmpty
  @Size(min = 3, max = 256, message = "First name should have 3 characters at least.")
  private String firstName;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Last name should have 3 characters at least.")
  private String lastName;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Last name should have 3 characters at least.")
  private String username;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Email must have 3 characters at least.")
  @Email
  private String email;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Password must have 3 characters at least.")
  private String password;

  public UserDTO() {
  }

  public UserDTO(final String id,
                 final String firstName,
                 final String lastName,
                 final String username,
                 final String email,
                 final String password) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public UserModel convert() {
    final Name name = new Name(getFirstName(), getLastName());
    final Set<AnsweredQuestionModel> answeredQuestions = new HashSet<>();
    return new UserModel(name, getUsername(), getEmail(), getPassword());
  }

  @Override
  public void update(final UserModel model) {
    setFirstName(model.getFirstName());
    setUsername(model.getUsername());
    setLastName(model.getLastName());
    setEmail(model.getEmail());
    setPassword(model.getPassword());
  }
}
