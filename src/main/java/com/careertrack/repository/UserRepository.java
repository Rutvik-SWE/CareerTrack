package com.careertrack.repository;

import com.careertrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 * What is this class? An interface extending JpaRepository.
 * Why is it needed? Provides built-in methods to interact with the 'users' table (save, findById, etc.).
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
