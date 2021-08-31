package com.taskmanager.TaskManager.models;

import com.taskmanager.TaskManager.dto.TaskDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private long id;

    @Column
    @NotEmpty
    @Size(min = 1, max = 50)
    private String taskName;

    @Column
    @NotEmpty
    @Size(min = 1, max = 300)
    private String taskDescription;

    @Column
    @NotEmpty
    private String status;

    @NotNull
    @ManyToOne
    private Project project;

    @NotNull
    @ManyToOne
    private AppUser createdBy;

    private Date dueDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AppUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof TaskDto) {

            TaskDto task = (TaskDto) o;

            return id == task.getId()
                    && Objects.equals(taskName, task.getTaskName())
                    && Objects.equals(taskDescription, task.getTaskDescription())
                    && Objects.equals(status, task.getStatus())
                    && Objects.equals(dueDate, task.getDueDate());
        } else {
            if (!(o instanceof Task)) return false;
            Task task = (Task) o;

            return id == task.id && Objects.equals(taskName, task.taskName) && Objects.equals(taskDescription, task.taskDescription) && Objects.equals(status, task.status) && Objects.equals(project, task.project) && Objects.equals(createdBy, task.createdBy) && Objects.equals(dueDate, task.dueDate);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, taskDescription, status, project, createdBy, dueDate);
    }
}
