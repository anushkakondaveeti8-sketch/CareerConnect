
package com.placement.portal.controller;

import com.placement.portal.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminStudentController {

    private final StudentRepository studentRepository;

    public AdminStudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/admin/students")
    public String viewStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "admin-students";
    }
}
