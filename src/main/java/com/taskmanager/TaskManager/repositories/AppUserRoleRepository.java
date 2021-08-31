package com.taskmanager.TaskManager.repositories;

import com.taskmanager.TaskManager.models.AppUserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRoleRepository extends CrudRepository<AppUserRole, Long> {

    Optional<AppUserRole> findAppUserRoleByRoleName(String roleName);
}
