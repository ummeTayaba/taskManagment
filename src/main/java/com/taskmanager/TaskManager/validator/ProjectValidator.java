package com.taskmanager.TaskManager.validator;

import com.taskmanager.TaskManager.dto.ProjectDto;
import com.taskmanager.TaskManager.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ProjectValidator implements Validator {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public boolean supports(Class<?> paramClass) {

        return ProjectDto.class.equals(paramClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ProjectDto project = (ProjectDto) target;

        if (projectRepository.existsProjectByProjectName(project.getProjectName())) {

            errors.rejectValue("projectName", "duplicateValue", new Object[]{"'projectName'"}, "Project name has to be unique");
        }
    }
}
