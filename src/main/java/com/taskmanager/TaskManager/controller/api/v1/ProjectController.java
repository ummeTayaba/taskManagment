package com.taskmanager.TaskManager.controller.api.v1;

import com.taskmanager.TaskManager.config.AppUserDetails;
import com.taskmanager.TaskManager.dto.ProjectDto;
import com.taskmanager.TaskManager.models.Project;
import com.taskmanager.TaskManager.repositories.ProjectRepository;
import com.taskmanager.TaskManager.service.ProjectService;
import com.taskmanager.TaskManager.validator.ApiValidationException;
import com.taskmanager.TaskManager.validator.ProjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectValidator validator;

    @Autowired
    private ProjectRepository projectRepository;


    @InitBinder
    void initStudentValidator(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping({"/projects"})
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public List<Project> getProjects(Principal principal, Authentication auth) {

        if (auth.getAuthorities().stream()
                .anyMatch(p -> p.getAuthority().equals("ROLE_ADMIN"))) {

            return projectService.getProjects();
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        AppUserDetails appUserDetails = (AppUserDetails) token.getPrincipal();

        return projectService.getProjectsByUser(appUserDetails.getUser());
    }

    @RequestMapping({"/projects/user/{userId}"})
    @RolesAllowed({"ROLE_ADMIN"})
    public List<Project> getProjectsByUser(@PathVariable("userId") Long userId) {

        return projectService.getProjectsByUserId(userId);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Project addProject(@Valid @RequestBody ProjectDto projectDto,
                              BindingResult result, Principal principal) {

        if (result.hasErrors()) {

            throw new ApiValidationException(result.getFieldErrors());
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        AppUserDetails appUserDetails = (AppUserDetails) token.getPrincipal();

        return projectService.saveProject(projectDto, appUserDetails.getUser());
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Project deleteProject(@PathVariable Long id) {

        Optional<Project> project = projectRepository.findById(id);

        if (project.isPresent()) {

            projectRepository.deleteById(id);

            return project.get();
        }

        throw new EmptyResultDataAccessException(1);
    }
}
