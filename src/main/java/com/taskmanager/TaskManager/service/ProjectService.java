package com.taskmanager.TaskManager.service;

import com.taskmanager.TaskManager.dto.ProjectDto;
import com.taskmanager.TaskManager.models.AppUser;
import com.taskmanager.TaskManager.models.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getProjects();

    Project saveProject(ProjectDto project, AppUser appUser);

    List<Project> getProjectsByUser(AppUser appUser);

    List<Project> getProjectsByUserId(Long userId);
}
