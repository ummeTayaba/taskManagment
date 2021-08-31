package com.taskmanager.TaskManager.dto;

import com.taskmanager.TaskManager.models.AppUser;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ProjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
