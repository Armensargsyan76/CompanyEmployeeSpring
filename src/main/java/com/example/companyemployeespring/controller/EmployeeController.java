package com.example.companyemployeespring.controller;

import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Value("${company.employees.images.folder}")
    private String folderPath;

    @GetMapping("/employee")
    public String employeePage(ModelMap modelMap) {
        List<Employee> employeeList = employeeRepository.findAll();
        modelMap.addAttribute("employees", employeeList);
        return "employeePage";
    }

    @GetMapping("/add/employee/page")
    public String addEmployeePage(ModelMap modelMap) {
        List<Company> companies = companyRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "addEmployee";
    }

    @PostMapping("add/employee")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Company company = companyRepository.getReferenceById(employee.getCompany().getId());
        company.setSize(company.getSize() + 1);
        companyRepository.save(company);
        if (!multipartFile.isEmpty() && multipartFile.getSize() > 0) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            String fullName = folderPath + File.separator + fileName;
            File file = new File(fullName);
            multipartFile.transferTo(file);
            employee.setProfilePic(fileName);
        }
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @GetMapping("/getImage")
    public @ResponseBody byte[] getImage(@RequestParam("picName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }


}
