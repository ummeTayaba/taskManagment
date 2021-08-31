package com.taskmanager.TaskManager.repositories;

import com.taskmanager.TaskManager.models.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    boolean existsProjectByProjectName(String projectName);

    List<Project> findProjectByCreatedByEmail(String user);
}
