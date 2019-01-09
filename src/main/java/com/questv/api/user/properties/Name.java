package com.questv.api.user.properties;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class Name {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 256, message = "First name should have 3 characters at least.")
    @Column(name = "firstName", nullable = false)
    private String firstName;


    @NotNull
    @NotEmpty
    @Size(min = 3, max = 256, message = "Last name should have 3 characters at least.")
    @Column(name = "lastName", nullable = false)
    private String lastName;

    public Name() { }

    public Name(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFullName() {
        return getFirstName().concat(" ").concat(getLastName());
    }
}
