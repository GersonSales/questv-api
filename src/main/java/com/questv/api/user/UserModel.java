package com.questv.api.user;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;
import com.questv.api.answered.question.AnsweredQuestionModel;
import com.questv.api.user.properties.Name;
import org.hibernate.annotations.ManyToAny;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "user_table", schema = "questv_schema")
public class UserModel implements Convertible<UserDTO>, Updatable<UserModel> {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  private UUID uuid;

  @NotNull
  @Embedded
  private Name name;

  @NotNull
  @NotEmpty
  private String username;

  @NotEmpty
  @NotNull
  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Password must have 3 characters at least.")
  @Column(name = "password", nullable = false)
  private String password;

  @ManyToMany
  @JoinTable(
      name = "user_has_answered_question",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "answered_question_id")})
  private Set<AnsweredQuestionModel> answeredQuestionModels;

  public UserModel() {
    this.uuid = UUID.randomUUID();
    this.answeredQuestionModels = new HashSet<>();
  }

  public UserModel(final Name name,
                   final String username,
                   final String email,
                   final String password) {
    this();
    this.name = name;
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUuid() {
    return uuid.toString();
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getFirstName() {
    return this.name.getFirstName();
  }

  public void setFirstName(final String name) {
    this.name.setFirstName(name);
  }

  public String getLastName() {
    return this.name.getLastName();
  }

  public void setLastName(final String lastName) {
    this.name.setLastName(lastName);
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public UserDTO convert() {
    final Map<Long, Long> answeredQuestions = new HashMap<>();
    for (final AnsweredQuestionModel answeredQuestionModel : this.answeredQuestionModels) {
      answeredQuestions.put(answeredQuestionModel.getQuestionId(), answeredQuestionModel.getAnswerId());
    }

    return new UserDTO(
        getUuid(),
        getFirstName(),
        getLastName(),
        getUsername(),
        getEmail(),
        getPassword(),
        answeredQuestions);
  }

  @Override
  public void update(final UserModel model) {
    setFirstName(model.getFirstName());
    setLastName(model.getLastName());
    setUsername(model.getUsername());
    setEmail(model.getEmail());
    setPassword(model.getPassword());
  }


  /*default*/ void attachAnsweredQuestion(final AnsweredQuestionModel answeredQuestionModel) {
    this.answeredQuestionModels.add(answeredQuestionModel);
  }



}
