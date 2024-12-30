package com.rustam.Movie_Website.dao.repository;

import com.rustam.Movie_Website.dao.entity.BaseUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, UUID> {
    @Query(value = "SELECT * FROM base_users WHERE (user_type = 'USER' OR user_type = 'ADMIN') AND username = :username", nativeQuery = true)
    Optional<BaseUser> findByUsername(String username);
}
