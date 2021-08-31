package com.taskmanager.TaskManager.service;

import com.taskmanager.TaskManager.dto.ProjectDto;
import com.taskmanager.TaskManager.models.AppUser;
import com.taskmanager.TaskManager.models.Project;
import com.taskmanager.TaskManager.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import com.taskmanager.TaskManager.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Project> getProjects() {

        return StreamSupport
                .stream(projectRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Project saveProject(ProjectDto projectDto, AppUser appUser) {

        Project project = new Project();

        project.setProjectName(projectDto.getProjectName());
        project.setCreatedBy(appUser);

        return projectRepository.save(project);
    }

    @Override
    public List<Project> getProjectsByUser(AppUser appUser) {

        return projectRepository.findProjectByCreatedByEmail(appUser.getEmail());
    }

    @Override
    public List<Project> getProjectsByUserId(Long userId) {

        Optional<AppUser> userOptional = userRepository.findById(userId);
        AppUser appUser = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        return getProjectsByUser(appUser);
    }
}
