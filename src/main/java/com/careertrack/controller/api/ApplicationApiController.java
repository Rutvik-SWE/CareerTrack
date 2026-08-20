package com.careertrack.controller.api;

import com.careertrack.dto.ApplicationRequestDTO;
import com.careertrack.dto.ApplicationResponseDTO;
import com.careertrack.entity.Company;
import com.careertrack.entity.JobApplication;
import com.careertrack.entity.User;
import com.careertrack.exception.ResourceNotFoundException;
import com.careertrack.service.ApplicationService;
import com.careertrack.service.CompanyService;
import com.careertrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
public class ApplicationApiController {

    private final ApplicationService applicationService;
    private final CompanyService companyService;
    private final UserService userService;

    public ApplicationApiController(ApplicationService applicationService, CompanyService companyService, UserService userService) {
        this.applicationService = applicationService;
        this.companyService = companyService;
        this.userService = userService;
    }

    private User getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> getAllApplications(Authentication authentication) {
        User user = getCurrentUser(authentication);
        List<ApplicationResponseDTO> applications = applicationService.findAllByUser(user).stream()
                .map(ApplicationResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getApplication(@PathVariable Long id, Authentication authentication) {
        User user = getCurrentUser(authentication);
        JobApplication application = applicationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }

        return ResponseEntity.ok(new ApplicationResponseDTO(application));
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> createApplication(@Valid @RequestBody ApplicationRequestDTO requestDTO, Authentication authentication) {
        User user = getCurrentUser(authentication);
        
        Company company = companyService.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + requestDTO.getCompanyId()));
        
        JobApplication application = new JobApplication();
        application.setUser(user);
        application.setCompany(company);
        application.setJobTitle(requestDTO.getJobTitle());
        application.setJobPlatform(requestDTO.getJobPlatform());
        application.setJobUrl(requestDTO.getJobUrl());
        application.setLocation(requestDTO.getLocation());
        application.setSalary(requestDTO.getSalary());
        application.setAppliedDate(requestDTO.getAppliedDate());
        application.setStatus(requestDTO.getStatus());
        application.setNotes(requestDTO.getNotes());

        JobApplication saved = applicationService.save(application);
        return new ResponseEntity<>(new ApplicationResponseDTO(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateApplication(@PathVariable Long id, @Valid @RequestBody ApplicationRequestDTO requestDTO, Authentication authentication) {
        User user = getCurrentUser(authentication);
        JobApplication application = applicationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }
        
        Company company = companyService.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + requestDTO.getCompanyId()));

        application.setCompany(company);
        application.setJobTitle(requestDTO.getJobTitle());
        application.setJobPlatform(requestDTO.getJobPlatform());
        application.setJobUrl(requestDTO.getJobUrl());
        application.setLocation(requestDTO.getLocation());
        application.setSalary(requestDTO.getSalary());
        application.setAppliedDate(requestDTO.getAppliedDate());
        application.setStatus(requestDTO.getStatus());
        application.setNotes(requestDTO.getNotes());

        JobApplication updated = applicationService.save(application);
        return ResponseEntity.ok(new ApplicationResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id, Authentication authentication) {
        User user = getCurrentUser(authentication);
        JobApplication application = applicationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }

        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
