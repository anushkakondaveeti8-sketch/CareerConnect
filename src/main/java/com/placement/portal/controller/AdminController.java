
package com.placement.portal.controller;

import com.placement.portal.entity.Job;
import com.placement.portal.repository.JobApplicationRepository;
import com.placement.portal.repository.JobRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    private final JobRepository jobRepository;
    private final JobApplicationRepository applicationRepository;

    public AdminController(
            JobRepository jobRepository,
            JobApplicationRepository applicationRepository) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    // View all jobs
    @GetMapping("/admin/jobs")
    public String manageJobs(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "admin-jobs";
    }

    // Add a new job
    @PostMapping("/admin/jobs")
    public String addJob(
            @RequestParam String companyName,
            @RequestParam String jobRole,
            @RequestParam String description,
            @RequestParam String eligibility,
            @RequestParam String salaryPackage) {

        Job job = new Job();
        job.setCompanyName(companyName);
        job.setJobRole(jobRole);
        job.setDescription(description);
        job.setEligibility(eligibility);
        job.setSalaryPackage(salaryPackage);

        jobRepository.save(job);

        return "redirect:/admin/jobs";
    }

    // Display the Edit Job page
    @GetMapping("/admin/jobs/{id}/edit")
    public String showEditJob(
            @PathVariable Long id,
            Model model) {

        Job job = jobRepository.findById(id).orElseThrow();

        model.addAttribute("job", job);

        return "edit-job";
    }

    // Save updated job details
    @PostMapping("/admin/jobs/{id}/edit")
    public String updateJob(
            @PathVariable Long id,
            @RequestParam String companyName,
            @RequestParam String jobRole,
            @RequestParam String description,
            @RequestParam String eligibility,
            @RequestParam String salaryPackage) {

        Job job = jobRepository.findById(id).orElseThrow();

        job.setCompanyName(companyName);
        job.setJobRole(jobRole);
        job.setDescription(description);
        job.setEligibility(eligibility);
        job.setSalaryPackage(salaryPackage);

        jobRepository.save(job);

        return "redirect:/admin/jobs";
    }

    // View all student job applications
    @GetMapping("/admin/applications")
    public String viewApplications(Model model) {
        model.addAttribute(
                "applications",
                applicationRepository.findAll());

        return "admin-applications";
    }

    // Update application status
    @PostMapping("/admin/applications/status")
    public String updateStatus(
            @RequestParam Long applicationId,
            @RequestParam String status) {

        if (!status.equals("Pending")
                && !status.equals("Accepted")
                && !status.equals("Rejected")) {

            return "redirect:/admin/applications";
        }

        var application = applicationRepository
                .findById(applicationId)
                .orElseThrow();

        application.setStatus(status);
        applicationRepository.save(application);

        return "redirect:/admin/applications";
    }
}
