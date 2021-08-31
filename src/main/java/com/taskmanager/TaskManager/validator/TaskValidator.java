package com.taskmanager.TaskManager.validator;

import com.taskmanager.TaskManager.dto.TaskDto;
import com.taskmanager.TaskManager.models.Project;
import com.taskmanager.TaskManager.models.Task;
import com.taskmanager.TaskManager.repositories.ProjectRepository;
import com.taskmanager.TaskManager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class TaskValidator implements Validator {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public boolean supports(Class<?> paramClass) {
        return TaskDto.class.equals(paramClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        TaskDto taskDto = (TaskDto) target;

        Optional<Project> projectOptional = projectRepository.findById(taskDto.getProjectId());
        Optional<Task> taskOptional = taskRepository.findById(taskDto.getId());

        if (projectOptional.isEmpty()) {

            errors.rejectValue("id", "Project with this ID does not exist",
                    "Project with this ID does not exist");

            return;
        }

        if (taskOptional.isPresent()) {

            if (taskOptional.get().getStatus().equalsIgnoreCase("closed")) {

                if (!taskOptional.get().equals(taskDto)) {
                    errors.rejectValue("status", "ClosedTask",
                            "Closed Task cannot be edited");
                }
            }
        }

    }
}