package workout.workoutspring.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.workoutspring.business.domain.entity.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

    void deleteByUserId(Long userId);
}
