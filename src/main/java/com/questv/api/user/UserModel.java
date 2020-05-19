package com.questv.api.user;

import com.questv.api.contracts.Convertible;
import com.questv.api.contracts.Updatable;
import com.questv.api.user.properties.Name;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class UserModel
    implements Convertible<UserDTO>,
    Updatable<UserModel>,
    UserDetails {

  private static final long serialVersionUID = 5738099810802742867L;

  @Id
  @NotNull
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @Column(name = "id")
  private String id;

  @NotNull
  @Embedded
  private Name name;

  @NotNull
  @NotEmpty
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @NotEmpty
  @NotNull
  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotEmpty
  @Size(min = 3, max = 256, message = "Password must have 3 characters at " +
      "least.")
  @Column(name = "password", nullable = false)
  private String password;


  @Column(name = "account_non_expired")
  private final boolean isAccountNonExpired;

  @Column(name = "account_non_locked")
  private final boolean isAccountNonLocked;

  @Column(name = "credentials_non_expired")
  private final boolean isCredentialsNonExpired;

  @Column(name = "enabled")
  private final boolean isEnabled;


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_permission",
      joinColumns = {@JoinColumn(name = "id_user")},
      inverseJoinColumns = {@JoinColumn(name = "id_permission")}
  )
  private List<Permission> permissions;


  public UserModel() {
    this.isAccountNonExpired = true;
    this.isAccountNonLocked = true;
    this.isCredentialsNonExpired = true;
    this.isEnabled = true;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
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

  @Override
  public boolean isAccountNonExpired() {
    return this.isAccountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.isAccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.isCredentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }

  public List<String> getRoles() {
    final List<String> roles = new ArrayList<>();
    for (final Permission permission : this.permissions) {
      roles.add(permission.getAuthority());
    }

    return roles;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  @Override
  public UserDTO convert() {
    return new UserDTO(
        getId(),
        getFirstName(),
        getLastName(),
        getUsername(),
        getEmail(),
        getPassword());
  }

  @Override
  public void update(final UserModel model) {
    setFirstName(model.getFirstName());
    setLastName(model.getLastName());
    setUsername(model.getUsername());
    setEmail(model.getEmail());
    setPassword(model.getPassword());
  }
}
