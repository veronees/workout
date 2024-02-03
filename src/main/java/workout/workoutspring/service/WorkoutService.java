package workout.workoutspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.workoutspring.domain.dto.request.WorkoutLogDTO;
import workout.workoutspring.domain.dto.request.WorkoutRoutineDTO;
import workout.workoutspring.domain.dto.request.WorkoutSetDTO;
import workout.workoutspring.domain.dto.response.WorkoutLogResponseDTO;
import workout.workoutspring.domain.dto.response.WorkoutRoutineResponseDTO;
import workout.workoutspring.domain.dto.response.WorkoutSetResponseDTO;
import workout.workoutspring.domain.entity.User;
import workout.workoutspring.domain.entity.WorkoutLog;
import workout.workoutspring.domain.entity.WorkoutRoutine;
import workout.workoutspring.domain.entity.WorkoutSet;
import workout.workoutspring.repository.UserRepository;
import workout.workoutspring.repository.WorkoutLogRepository;
import workout.workoutspring.repository.WorkoutRoutineRepository;
import workout.workoutspring.repository.WorkoutSetRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutService {

    private final WorkoutLogRepository workoutLogRepository;
    private final WorkoutSetRepository workoutSetRepository;
    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO) {
        List<WorkoutLogDTO> workoutLogDTOS = workoutRoutineDTO.getWorkoutLogs();
        User user = userRepository.findByUsername(workoutRoutineDTO.getUsername());
        WorkoutRoutine workoutRoutine = WorkoutRoutine.builder()
                .user(user)
                .title(workoutRoutineDTO.getTitle())
                .build();
        workoutRoutineRepository.save(workoutRoutine);


        for (WorkoutLogDTO workoutLogDTO : workoutLogDTOS) {
            WorkoutLog workoutLog = WorkoutLog.builder()
                    .workoutName(workoutLogDTO.getWorkoutName())
                    .workoutRoutine(workoutRoutine)
                    .build();
            workoutLogRepository.save(workoutLog);

            List<WorkoutSetDTO> workoutSetDTOS = workoutLogDTO.getSets();
            for (WorkoutSetDTO workoutSetDTO : workoutSetDTOS) {
                WorkoutSet workoutSet = WorkoutSet.builder()
                        .weight(workoutSetDTO.getWeight())
                        .repetitionCount(workoutSetDTO.getRepetitionCount())
                        .allVolume(workoutSetDTO.getWeight() * workoutSetDTO.getRepetitionCount())
                        .workoutLog(workoutLog)
                        .build();
                workoutSetRepository.save(workoutSet);
            }

        }
    }

    public WorkoutRoutineResponseDTO showWorkoutRoutine(Long userId) {
        WorkoutRoutine workoutRoutine = workoutRoutineRepository.findWorkoutRoutine(userId);
        List<WorkoutLog> workoutLogs = workoutRoutine.getWorkoutLogs();

        List<WorkoutLogResponseDTO> workoutLogResponseDTOList = new ArrayList<>();
        double allVolume = 0;
        for (WorkoutLog workoutLog : workoutLogs) {
            List<WorkoutSet> sets = workoutLog.getSets();

            List<WorkoutSetResponseDTO> workoutSetResponseDTOList = new ArrayList<>();
            double perVolume = 0;
            for (WorkoutSet set : sets) {
                perVolume += set.getAllVolume();
                WorkoutSetResponseDTO workoutSetResponseDTO = WorkoutSetResponseDTO.builder()
                        .weight(set.getWeight())
                        .repetitionCount(set.getRepetitionCount())
                        .build();
                workoutSetResponseDTOList.add(workoutSetResponseDTO);
            }
            allVolume += perVolume;
            WorkoutLogResponseDTO workoutLogResponseDTO = WorkoutLogResponseDTO.builder()
                    .workoutName(workoutLog.getWorkoutName())
                    .sets(workoutSetResponseDTOList)
                    .perVolume(perVolume)
                    .build();
            workoutLogResponseDTOList.add(workoutLogResponseDTO);
        }

        WorkoutRoutineResponseDTO workoutRoutineResponseDTO = WorkoutRoutineResponseDTO.builder()
                .title(workoutRoutine.getTitle())
                .workoutLogs(workoutLogResponseDTOList)
                .allVolume(allVolume)
                .build();

        return workoutRoutineResponseDTO;
    }


}
