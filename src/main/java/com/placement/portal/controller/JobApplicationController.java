
package com.placement.portal.controller;

import com.placement.portal.entity.Job;
import com.placement.portal.entity.JobApplication;
import com.placement.portal.entity.Student;
import com.placement.portal.repository.JobApplicationRepository;
import com.placement.portal.repository.JobRepository;
import com.placement.portal.repository.StudentRepository;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JobApplicationController {

    private final JobApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final StudentRepository studentRepository;

    public JobApplicationController(
            JobApplicationRepository applicationRepository,
            JobRepository jobRepository,
            StudentRepository studentRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/jobs/{id}/apply")
    public String apply(@PathVariable Long id, Principal principal) {

        String username = principal.getName();

        if (applicationRepository
                .existsByStudentUsernameAndJobId(username, id)) {
            return "redirect:/my-applications?duplicate";
        }

        Student student = studentRepository.findByUsername(username)
                .orElseThrow();

        Job job = jobRepository.findById(id)
                .orElseThrow();

        JobApplication application = new JobApplication();
        application.setStudent(student);
        application.setJob(job);
        application.setStatus("Pending");

        applicationRepository.save(application);

        return "redirect:/my-applications?success";
    }

    @GetMapping("/my-applications")
    public String myApplications(Principal principal, Model model) {
        model.addAttribute("applications",
                applicationRepository.findByStudentUsername(
                        principal.getName()));
        return "my-applications";
    }
}
