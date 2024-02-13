package workout.workoutspring.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workout.workoutspring.business.domain.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
