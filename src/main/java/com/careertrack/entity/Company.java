package com.careertrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Company Entity
 * What is this class? Represents the 'companies' table.
 * Why is it needed? To store information about companies the user is applying to.
 */
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Company name is required")
    @Column(nullable = false)
    private String name;

    @Column(length = 2048)
    private String website;

    @Column(length = 1024)
    private String location;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<JobApplication> jobApplications = new ArrayList<>();

    public Company() {}

    public Company(String name, String website, String location) {
        this.name = name;
        this.website = website;
        this.location = location;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public List<JobApplication> getJobApplications() { return jobApplications; }
    public void setJobApplications(List<JobApplication> jobApplications) { this.jobApplications = jobApplications; }
}
