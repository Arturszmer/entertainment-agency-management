package com.agency.user.model;

import java.util.ArrayList;
import java.util.List;

public class UserProfileBuilder {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private boolean accountNonLocked = true;

    public static UserProfileBuilder aUserProfile() {
        return new UserProfileBuilder();
    }

    public UserProfileBuilder withBasicData(){
        username = "username";
        firstName = "firstName";
        lastName = "lastName";
        email = "email";
        password = "password";
        return this;
    }

    public UserProfileBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserProfileBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserProfileBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserProfileBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserProfileBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserProfileBuilder withRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserProfile build() {
        return new UserProfile(username, firstName, lastName, email, password, roles, accountNonLocked);
    }
}
