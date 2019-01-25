package com.questv.api.user;

import com.questv.api.util.Convertible;
import com.questv.api.user.properties.Name;
import com.questv.api.util.Updatable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO implements Convertible<UserModel>, Updatable<UserModel> {


    private int id;

    @NotEmpty
    @Size(min = 3, max = 256, message = "First name should have 3 characters at least.")
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 256, message = "Last name should have 3 characters at least.")
    private String lastName;

    @NotEmpty
    @Size(min = 3, max = 256, message = "Email must have 3 characters at least.")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 3, max = 256, message = "Password must have 3 characters at least.")
    private String password;


    public UserDTO() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public UserModel convert() {
        final Name name = new Name(getFirstName(), getLastName());
        return new UserModel(name, getEmail(), getPassword());
    }

    @Override
    public void update(final UserModel model) {
        setFirstName(model.getFirstName());
        setLastName(model.getLastName());
        setEmail(model.getEmail());
        setPassword(model.getPassword());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
