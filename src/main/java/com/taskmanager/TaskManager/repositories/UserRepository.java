package com.taskmanager.TaskManager.repositories;

import com.taskmanager.TaskManager.models.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.email = :email")
    AppUser getAppUserByEmail(@Param("email") String email);

    Optional<AppUser> findById(Long id);
}
