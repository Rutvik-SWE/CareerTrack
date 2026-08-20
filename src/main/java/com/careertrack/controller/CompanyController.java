package com.careertrack.controller;

import com.careertrack.entity.Company;
import com.careertrack.entity.User;
import com.careertrack.exception.ResourceNotFoundException;
import com.careertrack.service.CompanyService;
import com.careertrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;

    public CompanyController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    private User getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @GetMapping
    public String listCompanies(Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        model.addAttribute("companies", companyService.findAllByUser(user));
        return "companies";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("company", new Company());
        return "add-company";
    }

    @PostMapping("/save")
    public String saveCompany(@Valid @ModelAttribute("company") Company company, BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "add-company";
        }
        User user = getCurrentUser(authentication);
        company.setUser(user);
        companyService.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Company company = companyService.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        model.addAttribute("company", company);
        return "add-company";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Company company = companyService.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        companyService.deleteById(company.getId());
        return "redirect:/companies";
    }
}
