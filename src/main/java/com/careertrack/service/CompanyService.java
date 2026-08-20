package com.careertrack.service;

import com.careertrack.entity.Company;
import com.careertrack.entity.User;
import com.careertrack.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAllByUser(User user) {
        return companyRepository.findByUser(user);
    }

    public Optional<Company> findByIdAndUser(Long id, User user) {
        return companyRepository.findByIdAndUser(id, user);
    }

    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id); // Needed globally sometimes, but use findByIdAndUser for security
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
