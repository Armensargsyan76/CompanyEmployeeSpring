package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company")
    public String companyPage(ModelMap modelMap) {
        List<Company> companies = companyRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "companyPage";
    }

    @GetMapping("/add/company/page")
    public String addCompanyPage() {
        return "addCompany";
    }

    @PostMapping("/add/company")
    public String addCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/company";
    }

    @GetMapping("/company/delete")
    public String deleteCompany(@RequestParam("id") int id) {
        companyRepository.deleteById(id);
        return "redirect:/company";
    }
}
