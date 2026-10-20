
package com.placement.portal.repository;

import com.placement.portal.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByStudentUsername(String username);

    boolean existsByStudentUsernameAndJobId(
            String username, Long jobId);
}
