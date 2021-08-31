package com.taskmanager.TaskManager.dto.response;

import com.taskmanager.TaskManager.models.AppUser;
import com.taskmanager.TaskManager.models.AppUserRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserViewResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private long userId;
    private String email;
    private List<String> roles;

    public UserViewResponse() {
        this.roles = new ArrayList<>();
    }

    public UserViewResponse(AppUser appUser) {

        this.userId = appUser.getId();
        this.email = appUser.getEmail();
        this.roles = appUser
                        .getRoles()
                        .stream()
                        .map(AppUserRole::getRoleName)
                        .collect(Collectors.toList());
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
