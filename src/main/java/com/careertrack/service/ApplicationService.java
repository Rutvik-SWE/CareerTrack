package com.careertrack.service;

import com.careertrack.entity.JobApplication;
import com.careertrack.entity.User;
import com.careertrack.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ApplicationService
 * What is this class? Service for job application operations.
 * Why is it needed? To handle business logic for job applications, separating it from the controller.
 */
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<JobApplication> findAllByUser(User user) {
        return applicationRepository.findByUserOrderByAppliedDateDesc(user);
    }

    public Optional<JobApplication> findById(Long id) {
        return applicationRepository.findById(id);
    }

    public JobApplication save(JobApplication application) {
        return applicationRepository.save(application);
    }

    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    public List<JobApplication> search(User user, String query) {
        if (query == null || query.isEmpty()) {
            return findAllByUser(user);
        }
        return applicationRepository.findByUserAndJobTitleContainingIgnoreCaseOrUserAndCompany_NameContainingIgnoreCase(
                user, query, user, query);
    }

    public List<JobApplication> filterByStatus(User user, String status) {
        if (status == null || status.isEmpty()) {
            return findAllByUser(user);
        }
        return applicationRepository.findByUserAndStatus(user, status);
    }

    public long countTotal(User user) {
        return applicationRepository.countByUser(user);
    }

    public long countByStatus(User user, String status) {
        return applicationRepository.countByUserAndStatus(user, status);
    }
}
