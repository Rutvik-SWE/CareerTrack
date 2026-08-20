package com.careertrack.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (isApiRequest(request)) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } else {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return new ModelAndView("redirect:/dashboard");
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        if (isApiRequest(request)) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            // For Thymeleaf forms, Spring handles BindingResult automatically, 
            // but this catch-all can be useful if an unexpected validation occurs.
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("message", "Form validation failed.");
            return mav;
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (isApiRequest(request)) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return new ModelAndView("redirect:/dashboard");
        }
    }

    @ExceptionHandler(Exception.class)
    public Object handleGlobalException(Exception ex, HttpServletRequest request) {
        if (isApiRequest(request)) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("message", "An unexpected error occurred. Please try again later.");
            return mav;
        }
    }
}
