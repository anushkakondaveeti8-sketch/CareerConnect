
package com.placement.portal.controller;

import com.placement.portal.entity.Student;
import com.placement.portal.repository.StudentRepository;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final StudentRepository studentRepository;

    public HomeController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {

        String username = principal.getName();

        if ("admin".equals(username)) {
            model.addAttribute("isAdmin", true);
            model.addAttribute("username", username);
            return "dashboard";
        }

        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() ->
                    new IllegalStateException("Student account not found"));

        model.addAttribute("isAdmin", false);
        model.addAttribute("student", student);

        return "dashboard";
    }
}
