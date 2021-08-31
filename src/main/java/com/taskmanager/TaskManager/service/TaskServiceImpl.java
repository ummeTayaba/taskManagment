package com.taskmanager.TaskManager.service;


import com.taskmanager.TaskManager.dto.TaskDto;
import com.taskmanager.TaskManager.models.AppUser;
import com.taskmanager.TaskManager.models.Project;
import com.taskmanager.TaskManager.models.Task;
import com.taskmanager.TaskManager.repositories.ProjectRepository;
import com.taskmanager.TaskManager.repositories.TaskRepository;
import com.taskmanager.TaskManager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        return tasks;
    }

    @Override
    @Transactional
    public Task saveTask(TaskDto taskDto, AppUser appUser) {

        Task task = new Task();
        Optional<Project> projectOptional = projectRepository.findById(taskDto.getProjectId());

        Project project = projectOptional.orElseThrow(() -> new EmptyResultDataAccessException(1));

        task.setTaskName(taskDto.getTaskName());
        task.setDueDate(taskDto.getDueDate());
        task.setTaskDescription(taskDto.getTaskDescription());
        task.setCreatedBy(appUser);
        task.setStatus(taskDto.getStatus());

        task.setProject(project);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task editTask(TaskDto taskDto, AppUser appUser) {

        Optional<Task> taskOptional = taskRepository.findById(taskDto.getId());

        if (taskOptional.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        Task task = taskOptional.get();

        task.setTaskName(taskDto.getTaskName());
        task.setTaskDescription(taskDto.getTaskDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setStatus(taskDto.getStatus());
        task.setCreatedBy(appUser);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksByUser(AppUser appUser) {

        return taskRepository.findTaskByCreatedByEmail(appUser.getEmail());
    }

    @Override
    public List<Task> getTasksByUserId(long userId) {

        Optional<AppUser> appUserOptional = userRepository.findById(userId);
        AppUser appUser = appUserOptional
                            .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userId));

        return getTasksByUser(appUser);
    }

    private Project getProject(TaskDto taskDto) {

        Optional<Project> projectOptional = projectRepository.findById(taskDto.getId());

        if (projectOptional.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return projectOptional.get();
    }
}
