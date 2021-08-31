package com.taskmanager.TaskManager.service;


import com.taskmanager.TaskManager.dto.TaskDto;
import com.taskmanager.TaskManager.models.AppUser;
import com.taskmanager.TaskManager.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getTasks();

    Task saveTask(TaskDto taskDto, AppUser appUser);

    Task editTask(TaskDto task, AppUser appUser);

    List<Task> getTasksByUser(AppUser user);

    List<Task> getTasksByUserId(long userId);
}
