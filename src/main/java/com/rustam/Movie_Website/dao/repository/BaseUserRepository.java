package com.rustam.Movie_Website.dao.repository;

import com.rustam.Movie_Website.dao.entity.BaseUser;

import com.rustam.Movie_Website.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, UUID> {
    @Query(value = "SELECT * FROM base_users WHERE user_type = 'USER' AND username = :username", nativeQuery = true)
    Optional<User> findByUsername(String username);

    List<User> findAllBy();
}
