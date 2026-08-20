package com.careertrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ApplicationRequestDTO {
    
    @NotNull(message = "Company ID is required")
    private Long companyId;
    
    @NotBlank(message = "Job Title is required")
    private String jobTitle;
    
    private String jobPlatform;
    private String jobUrl;
    private String location;
    private String salary;
    
    @NotNull(message = "Applied Date is required")
    private LocalDate appliedDate;
    
    @NotBlank(message = "Status is required")
    private String status;
    
    private String notes;

    // Getters and Setters
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

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
