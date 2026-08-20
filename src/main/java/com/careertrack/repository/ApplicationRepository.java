package com.careertrack.repository;

import com.careertrack.entity.JobApplication;
import com.careertrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ApplicationRepository
 * What is this class? Repository for job applications.
 * Why is it needed? To perform CRUD operations on the 'job_applications' table.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<JobApplication, Long> {
    
    // Step 6: Find by user
    List<JobApplication> findByUserOrderByAppliedDateDesc(User user);
    
    // Search by title or company
    List<JobApplication> findByUserAndJobTitleContainingIgnoreCaseOrUserAndCompany_NameContainingIgnoreCase(
            User user1, String title, User user2, String companyName);
            
    // Filter by status
    List<JobApplication> findByUserAndStatus(User user, String status);

    // Dashboard stats
    long countByUser(User user);
    long countByUserAndStatus(User user, String status);
}
