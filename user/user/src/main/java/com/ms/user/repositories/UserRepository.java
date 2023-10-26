package com.ms.user.repositories;

import com.ms.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    @Query("SELECT s FROM UserModel s WHERE s.email = ?1")
    Optional<UserModel> findByEmail(String email);
}
