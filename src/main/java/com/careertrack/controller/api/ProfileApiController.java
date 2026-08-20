package com.careertrack.controller.api;

import com.careertrack.dto.ProfileResponseDTO;
import com.careertrack.entity.Profile;
import com.careertrack.entity.User;
import com.careertrack.exception.ResourceNotFoundException;
import com.careertrack.service.ProfileService;
import com.careertrack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileApiController {

    private final ProfileService profileService;
    private final UserService userService;

    public ProfileApiController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    private User getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @GetMapping
    public ResponseEntity<ProfileResponseDTO> getProfile(Authentication authentication) {
        User user = getCurrentUser(authentication);
        Profile profile = profileService.getOrCreateProfile(user);
        
        return ResponseEntity.ok(new ProfileResponseDTO(profile));
    }
}
