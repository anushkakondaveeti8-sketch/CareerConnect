
package com.placement.portal.controller;

import com.placement.portal.entity.Student;
import com.placement.portal.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(
            StudentRepository studentRepository,
            PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerStudent(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String branch,
            Model model) {

        if (studentRepository.existsByUsername(username)) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }

        if (studentRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email already registered.");
            return "register";
        }

        Student student = new Student();
        student.setFullName(fullName);
        student.setEmail(email);
        student.setUsername(username);
        student.setPassword(passwordEncoder.encode(password));
        student.setBranch(branch);

        studentRepository.save(student);

        return "redirect:/login?registered";
    }
}
