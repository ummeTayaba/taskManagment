package com.taskmanager.TaskManager.repositories;

import com.taskmanager.TaskManager.models.AppUser;
import com.taskmanager.TaskManager.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findTaskByCreatedByEmail(String user);
}
