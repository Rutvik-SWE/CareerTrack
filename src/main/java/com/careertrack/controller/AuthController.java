package com.careertrack.controller;

import com.careertrack.entity.User;
import com.careertrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * AuthController
 * What is this class? A Spring MVC Controller for authentication.
 * Why is it needed? To handle HTTP requests for registration and login pages.
 */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", null, "There is already an account registered with that email");
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login?registered";
    }
}
