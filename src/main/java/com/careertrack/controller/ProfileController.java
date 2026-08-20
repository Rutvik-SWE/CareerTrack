package com.careertrack.controller;

import com.careertrack.entity.Profile;
import com.careertrack.entity.User;
import com.careertrack.service.ProfileService;
import com.careertrack.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;

    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    private User getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public String viewProfile(Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Profile profile = profileService.getOrCreateProfile(user);
        model.addAttribute("profile", profile);
        return "profile";
    }

    @GetMapping("/edit")
    public String editProfile(Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Profile profile = profileService.getOrCreateProfile(user);
        model.addAttribute("profile", profile);
        return "edit-profile";
    }

    @PostMapping("/save")
    public String saveProfile(@ModelAttribute("profile") Profile updatedProfile, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = getCurrentUser(authentication);
        Profile existingProfile = profileService.getOrCreateProfile(user);
        
        // Update fields
        existingProfile.setFullName(updatedProfile.getFullName());
        existingProfile.setEmail(updatedProfile.getEmail());
        existingProfile.setPhone(updatedProfile.getPhone());
        existingProfile.setLocation(updatedProfile.getLocation());
        existingProfile.setLinkedin(updatedProfile.getLinkedin());
        existingProfile.setGithub(updatedProfile.getGithub());
        existingProfile.setSummary(updatedProfile.getSummary());
        existingProfile.setSkills(updatedProfile.getSkills());
        
        profileService.saveProfile(existingProfile);
        
        // Also update User entity if name changed (do not update login email to prevent session issues)
        userService.updateUser(user, existingProfile.getFullName(), user.getEmail(), null);

        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/profile";
    }

    @PostMapping("/resume/upload")
    public String uploadResume(@RequestParam("resumeFile") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = getCurrentUser(authentication);
        Profile profile = profileService.getOrCreateProfile(user);
        
        try {
            profileService.uploadResume(profile, file);
            redirectAttributes.addFlashAttribute("success", "Resume uploaded successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload resume.");
        }
        
        return "redirect:/profile";
    }

    @GetMapping("/resume/download")
    public ResponseEntity<Resource> downloadResume(Authentication authentication) {
        User user = getCurrentUser(authentication);
        Profile profile = profileService.getOrCreateProfile(user);
        
        if (profile.getResumePath() == null) {
            return ResponseEntity.notFound().build();
        }
        
        try {
            Path path = Paths.get(profile.getResumePath());
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profile.getResumeFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/resume/delete")
    public String deleteResume(Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = getCurrentUser(authentication);
        Profile profile = profileService.getOrCreateProfile(user);
        
        profileService.deleteResume(profile);
        redirectAttributes.addFlashAttribute("success", "Resume deleted successfully!");
        
        return "redirect:/profile";
    }
}
