package com.careertrack.dto;

import com.careertrack.entity.Profile;

public class ProfileResponseDTO {
    private String fullName;
    private String email;
    private String phone;
    private String location;
    private String linkedin;
    private String github;
    private String summary;
    private String skills;

    public ProfileResponseDTO() {}

    public ProfileResponseDTO(Profile profile) {
        this.fullName = profile.getFullName();
        this.email = profile.getEmail();
        this.phone = profile.getPhone();
        this.location = profile.getLocation();
        this.linkedin = profile.getLinkedin();
        this.github = profile.getGithub();
        this.summary = profile.getSummary();
        this.skills = profile.getSkills();
    }

    // Getters and Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getLinkedin() { return linkedin; }
    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }

    public String getGithub() { return github; }
    public void setGithub(String github) { this.github = github; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
}
