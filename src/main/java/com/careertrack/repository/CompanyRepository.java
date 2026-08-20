package com.careertrack.repository;

import com.careertrack.entity.Company;
import com.careertrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CompanyRepository
 * What is this class? Repository for companies.
 * Why is it needed? To perform CRUD operations on the 'companies' table.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByUser(User user);
    Optional<Company> findByIdAndUser(Long id, User user);
}
