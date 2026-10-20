
package com.placement.portal.repository;

import com.placement.portal.entity.AptitudeScore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AptitudeScoreRepository
        extends JpaRepository<AptitudeScore, Long> {

    List<AptitudeScore> findByStudentUsernameOrderByIdDesc(
            String username);
}
