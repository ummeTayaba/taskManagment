package com.taskmanager.TaskManager.controller.api.v1;

import com.taskmanager.TaskManager.config.AppUserDetails;
import com.taskmanager.TaskManager.dto.TaskDto;
import com.taskmanager.TaskManager.models.Task;
import com.taskmanager.TaskManager.repositories.TaskRepository;
import com.taskmanager.TaskManager.service.TaskService;
import com.taskmanager.TaskManager.validator.ApiValidationException;
import com.taskmanager.TaskManager.validator.TaskValidator;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskValidator validator;

    @Autowired
    private TaskRepository taskRepository;

    @InitBinder
    void initStudentValidator(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping({"/tasks"})
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public List<Task> getTasks(Principal principal, Authentication auth) {

        if (auth.getAuthorities().stream()
                .anyMatch(p -> p.getAuthority().equals("ROLE_ADMIN"))) {

            return taskService.getTasks();
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        AppUserDetails appUserDetails = (AppUserDetails) token.getPrincipal();

        return taskService.getTasksByUser(appUserDetails.getUser());
    }

    @RequestMapping({"/tasks/user/{userId}"})
    @RolesAllowed({"ROLE_ADMIN"})
    public List<Task> getTasksByUser(@PathVariable("userId") Long userId) {

        return taskService.getTasksByUserId(userId);
    }

    @RequestMapping({"/tasks/search/project/{projectId}"})
    @RolesAllowed({"ROLE_ADMIN"})
    public List<Task> getTasksByProjectId(@PathVariable("projectId") Long projectId,
                                          Principal principal,
                                          Authentication auth) {

        if (auth.getAuthorities().stream()
                .anyMatch(p -> p.getAuthority().equals("ROLE_ADMIN"))) {

            return taskService.getTasks()
                    .stream()
                    .filter(task -> task.getProject().getId() == (projectId))
                    .collect(Collectors.toList());
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        AppUserDetails appUserDetails = (AppUserDetails) token.getPrincipal();

        return taskService.getTasksByUser(appUserDetails.getUser())
                .stream()
                .filter(task -> task.getProject().getId() == (projectId))
                .collect(Collectors.toList());
    }

    @RequestMapping({"/tasks/search/status/{value}"})
    @RolesAllowed({"ROLE_ADMIN"})
    public List<Task> getTasksByStatus(@PathVariable("value") String status,
                                       Principal principal,
                                       Authentication auth) {

        if (auth.getAuthorities().stream()
                .anyMatch(p -> p.getAuthority().equals("ROLE_ADMIN"))) {

            return taskService.getTasks()
                    .stream()
                    .filter(task -> task.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        AppUserDetails appUserDetails = (AppUserDetails) token.getPrincipal();

        return taskService.getTasksByUser(appUserDetails.getUser())
                .stream()
                .filter(task -> task.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    @RequestMapping({"/tasks/search/due"})
    @RolesAllowed({"ROLE_ADMIN"})
    public List<Task> getTasksByDueDate(Principal principal,
                                        Authentication auth) {

        LocalDateTime now = LocalDateTime.now();

        if (auth.getAuthorities().stream()
                .anyMatch(p -> p.getAuthority().equals("ROLE_ADMIN"))) {

            return taskService.getTasks()
                    .stream()
                    .filter(task -> {
                        Instant instant = Instant.ofEpochMilli(task.getDueDate().getTime());
                        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

                        return now.isAfter(ldt);
                    })
                    .collect(Collectors.toList());
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        AppUserDetails appUserDetails = (AppUserDetails) token.getPrincipal();

        return taskService.getTasksByUser(appUserDetails.getUser())
                .stream()
                .filter(task -> {
                    Instant instant = Instant.ofEpochMilli(task.getDueDate().getTime());
                    LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

                    return now.isAfter(ldt);
                })
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Task addTask(@Valid @RequestBody TaskDto task,
                        Principal principal,
                        BindingResult result) {

        if (result.hasErrors()) {

            throw new ApiValidationException(result.getFieldErrors());
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        AppUserDetails appUserDetails = (AppUserDetails) token.getPrincipal();

        return taskService.saveTask(task, appUserDetails.getUser());
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PATCH)
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Task editTask(@Valid @RequestBody TaskDto task,
                         BindingResult result,
                         Principal principal) {

        if (result.hasErrors()) {

            throw new ApiValidationException(result.getFieldErrors());
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        AppUserDetails appUserDetails = (AppUserDetails) token.getPrincipal();

        return taskService.editTask(task, appUserDetails.getUser());
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public Task deleteTask(@PathVariable Long id) {

        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()) {

            taskRepository.deleteById(id);
            return task.get();
        }

        throw new EmptyResultDataAccessException(1);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public Task getTask(@PathVariable("id") Long id) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()) {

            return task.get();
        }

        throw new EmptyResultDataAccessException(1);
    }
}
