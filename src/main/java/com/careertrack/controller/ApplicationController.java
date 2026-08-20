package com.careertrack.controller;

import com.careertrack.entity.JobApplication;
import com.careertrack.entity.User;
import com.careertrack.exception.ResourceNotFoundException;
import com.careertrack.service.ApplicationService;
import com.careertrack.service.CompanyService;
import com.careertrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CompanyService companyService;
    private final UserService userService;

    public ApplicationController(ApplicationService applicationService, CompanyService companyService, UserService userService) {
        this.applicationService = applicationService;
        this.companyService = companyService;
        this.userService = userService;
    }

    private User getCurrentUser(Authentication authentication) {
        return userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        
        model.addAttribute("total", applicationService.countTotal(user));
        model.addAttribute("applied", applicationService.countByStatus(user, "Applied"));
        model.addAttribute("interview", applicationService.countByStatus(user, "Interview"));
        model.addAttribute("offer", applicationService.countByStatus(user, "Offer"));
        model.addAttribute("rejected", applicationService.countByStatus(user, "Rejected"));
        
        List<JobApplication> recent = applicationService.findAllByUser(user);
        if (recent.size() > 5) {
            recent = recent.subList(0, 5);
        }
        model.addAttribute("recentApplications", recent);
        
        return "dashboard";
    }

    @GetMapping("/applications")
    public String listApplications(Model model, Authentication authentication,
                                   @RequestParam(required = false) String search,
                                   @RequestParam(required = false) String status) {
        User user = getCurrentUser(authentication);
        List<JobApplication> applications;

        if (search != null && !search.isEmpty()) {
            applications = applicationService.search(user, search);
            model.addAttribute("search", search);
        } else if (status != null && !status.isEmpty()) {
            applications = applicationService.filterByStatus(user, status);
            model.addAttribute("selectedStatus", status);
        } else {
            applications = applicationService.findAllByUser(user);
        }

        model.addAttribute("applications", applications);
        return "applications";
    }

    @GetMapping("/applications/add")
    public String showAddForm(Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        model.addAttribute("application", new JobApplication());
        model.addAttribute("companies", companyService.findAllByUser(user));
        return "add-application";
    }

    @PostMapping("/applications/save")
    public String saveApplication(@Valid @ModelAttribute("application") JobApplication application, 
                                  BindingResult result, Authentication authentication, Model model) {
        User user = getCurrentUser(authentication);
        if (result.hasErrors()) {
            model.addAttribute("companies", companyService.findAllByUser(user));
            return "add-application";
        }
        
        application.setUser(user);
        applicationService.save(application);
        return "redirect:/applications";
    }

    @GetMapping("/applications/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        JobApplication application = applicationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        
        if (!application.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Application not found");
        }
        
        model.addAttribute("application", application);
        model.addAttribute("companies", companyService.findAllByUser(user));
        return "add-application";
    }

    @GetMapping("/applications/delete/{id}")
    public String deleteApplication(@PathVariable Long id, Authentication authentication) {
        User user = getCurrentUser(authentication);
        JobApplication application = applicationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
                
        if (application.getUser().getId().equals(user.getId())) {
            applicationService.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Application not found");
        }
        return "redirect:/applications";
    }
}
