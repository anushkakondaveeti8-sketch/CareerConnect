
package com.placement.portal.controller;

import com.placement.portal.repository.JobRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JobController {

    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping("/jobs")
    public String showJobs(
            @RequestParam(required = false) String keyword,
            Model model) {

        if (keyword == null || keyword.trim().isEmpty()) {
            model.addAttribute("jobs", jobRepository.findAll());
        } else {
            model.addAttribute(
                "jobs",
                jobRepository
                    .findByCompanyNameContainingIgnoreCaseOrJobRoleContainingIgnoreCase(
                        keyword.trim(), keyword.trim())
            );
        }

        model.addAttribute("keyword", keyword);

        return "jobs";
    }
}
