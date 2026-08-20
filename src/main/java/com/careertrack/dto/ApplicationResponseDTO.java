package com.careertrack.dto;

import com.careertrack.entity.JobApplication;
import java.time.LocalDate;

public class ApplicationResponseDTO {
    private Long id;
    private String jobTitle;
    private String jobPlatform;
    private String jobUrl;
    private String location;
    private String salary;
    private LocalDate appliedDate;
    private String status;
    private String notes;
    private String companyName;

    public ApplicationResponseDTO() {}

    public ApplicationResponseDTO(JobApplication application) {
        this.id = application.getId();
        this.jobTitle = application.getJobTitle();
        this.jobPlatform = application.getJobPlatform();
        this.jobUrl = application.getJobUrl();
        this.location = application.getLocation();
        this.salary = application.getSalary();
        this.appliedDate = application.getAppliedDate();
        this.status = application.getStatus();
        this.notes = application.getNotes();
        if (application.getCompany() != null) {
            this.companyName = application.getCompany().getName();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
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
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
