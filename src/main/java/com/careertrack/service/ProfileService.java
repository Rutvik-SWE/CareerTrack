package com.careertrack.service;

import com.careertrack.entity.Profile;
import com.careertrack.entity.User;
import com.careertrack.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final String uploadDir = "uploads/resumes/";

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        // Ensure upload directory exists
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize upload directory", e);
        }
    }

    public Profile getOrCreateProfile(User user) {
        return profileRepository.findByUser(user).orElseGet(() -> {
            Profile newProfile = new Profile(user);
            newProfile.setFullName(user.getName());
            newProfile.setEmail(user.getEmail());
            return profileRepository.save(newProfile);
        });
    }

    public void saveProfile(Profile profile) {
        profileRepository.save(profile);
    }

    public void uploadResume(Profile profile, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file");
        }
        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        // Delete old resume if exists
        deleteResume(profile);

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        Path filePath = Paths.get(uploadDir + uniqueFilename);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        profile.setResumeFilename(originalFilename);
        profile.setResumePath(filePath.toString());
        profileRepository.save(profile);
    }

    public void deleteResume(Profile profile) {
        if (profile.getResumePath() != null) {
            try {
                Path filePath = Paths.get(profile.getResumePath());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Ignore delete errors for now, or log them
            }
            profile.setResumeFilename(null);
            profile.setResumePath(null);
            profileRepository.save(profile);
        }
    }
}
