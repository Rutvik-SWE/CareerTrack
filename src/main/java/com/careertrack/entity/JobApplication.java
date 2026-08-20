package com.careertrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * JobApplication Entity
 * What is this class? Represents the 'job_applications' table.
 * Why is it needed? This is the core domain object, linking a user, a company, and application details.
 */
@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @NotNull(message = "Company is required")
    private Company company;

    @NotBlank(message = "Job title is required")
    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "job_platform")
    private String jobPlatform;

    @Column(name = "job_url", length = 2048)
    private String jobUrl;

    @Column(length = 1024)
    private String location;

    private String salary;

    @NotNull(message = "Applied date is required")
    @Column(name = "applied_date", nullable = false)
    private LocalDate appliedDate;

    @NotBlank(message = "Status is required")
    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public JobApplication() {}

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public String getJobPlatform() { return jobPlatform; }
    public void setJobPlatform(String jobPlatform) { this.jobPlatform = jobPlatform; }
    public String getJobUrl() { return jobUrl; }
    public void setJobUrl(String jobUrl) { this.jobUrl = jobUrl; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
