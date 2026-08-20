package com.careertrack.service;

import com.careertrack.entity.Company;
import com.careertrack.entity.JobApplication;
import com.careertrack.entity.User;
import com.careertrack.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveJobApplication() {
        // Test 3: Job application creation
        // This test verifies that we can successfully save a job application
        // and link it to both a User and a Company.
        
        User user = new User("Test User", "test@test.com", "pass");
        user.setId(1L);
        
        Company company = new Company("Google", "google.com", "Remote");
        company.setId(1L);
        company.setUser(user);
        
        JobApplication application = new JobApplication();
        application.setUser(user);
        application.setCompany(company);
        application.setJobTitle("Software Engineer");
        application.setStatus("Applied");
        application.setAppliedDate(LocalDate.now());
        
        when(applicationRepository.save(any(JobApplication.class))).thenReturn(application);
        
        JobApplication saved = applicationService.save(application);
        
        assertNotNull(saved);
        assertEquals("Software Engineer", saved.getJobTitle());
        assertEquals(user, saved.getUser());
        assertEquals(company, saved.getCompany());
        
        verify(applicationRepository, times(1)).save(application);
    }
}
