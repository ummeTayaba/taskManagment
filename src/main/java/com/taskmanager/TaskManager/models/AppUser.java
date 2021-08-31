package com.taskmanager.TaskManager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private long id;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String email;

    @NotEmpty
    @JsonIgnore
    public String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppUserRole> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<AppUserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AppUserRole> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
