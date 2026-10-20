
package com.placement.portal;

import com.placement.portal.entity.Job;
import com.placement.portal.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobDataLoader {

    @Bean
    CommandLineRunner loadJobs(JobRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Job job1 = new Job();
                job1.setCompanyName("Tech Solutions");
                job1.setJobRole("Java Developer");
                job1.setDescription("Develop Java applications.");
                job1.setEligibility("B.Tech - IT/CSE");
                job1.setSalaryPackage("6 LPA");
                repository.save(job1);

                Job job2 = new Job();
                job2.setCompanyName("Innovate Systems");
                job2.setJobRole("Software Engineer");
                job2.setDescription("Work on software development projects.");
                job2.setEligibility("B.Tech - IT/CSE");
                job2.setSalaryPackage("8 LPA");
                repository.save(job2);
            }
        };
    }
}
