package workout.workoutspring.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.workoutspring.business.domain.dto.request.WorkoutLogDTO;
import workout.workoutspring.business.domain.dto.request.WorkoutNextRoutineDTO;
import workout.workoutspring.business.domain.dto.request.WorkoutRoutineDTO;
import workout.workoutspring.business.domain.dto.request.WorkoutSetDTO;
import workout.workoutspring.business.domain.dto.response.WorkoutLogResponseDTO;
import workout.workoutspring.business.domain.dto.response.WorkoutResponseDTO;
import workout.workoutspring.business.domain.dto.response.WorkoutRoutineResponseDTO;
import workout.workoutspring.business.domain.dto.response.WorkoutSetResponseDTO;
import workout.workoutspring.business.domain.entity.*;
import workout.workoutspring.business.repository.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutService {

    private final WorkoutLogRepository workoutLogRepository;
    private final WorkoutSetRepository workoutSetRepository;
    private final WorkoutRoutineRepository workoutRoutineRepository;
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    @Transactional
    public void saveWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO) {
        List<WorkoutLogDTO> workoutLogDTOS = workoutRoutineDTO.getWorkoutLogs();
        User user = userRepository.findByUsername(workoutRoutineDTO.getUsername());
        WorkoutRoutine workoutRoutine = WorkoutRoutine.builder()
                .user(user)
                .title(workoutRoutineDTO.getTitle())
                .currentDate(LocalDate.now())
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

    public WorkoutRoutineResponseDTO showWorkoutRoutine(Long userId, LocalDate date) {
        WorkoutRoutine workoutRoutine = workoutRoutineRepository.findByUserIdAndDate(userId, date);
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
                .date(workoutRoutine.getCurrentDate())
                .workoutLogs(workoutLogResponseDTOList)
                .allVolume(allVolume)
                .build();

        return workoutRoutineResponseDTO;
    }

    // 가슴 등 어꺠 하체 팔 중 선택된 해당 운동의 모든 리스트 뽑기
    public List<WorkoutResponseDTO> findWorkoutList(String workoutKind) {
        List<Workout> findWorkoutList = workoutRepository.findByTarget(workoutKind);
        List<WorkoutResponseDTO> workoutResponseDTOList = new ArrayList<>();

        for (Workout workout : findWorkoutList) {
            WorkoutResponseDTO workoutResponseDTO = WorkoutResponseDTO.builder()
                    .workoutName(workout.getWorkoutName())
                    .imageUrl(fileUploadService.getPath(workout.getFile().getStoreFileName()))
                    .build();
            workoutResponseDTOList.add(workoutResponseDTO);
        }
        return workoutResponseDTOList;
    }


    //다음 종목 선택 누르면 일단 기존 작성 내용 db에 저장 시키는 비즈니스 로직으로 할 경우
    @Transactional
    public void saveWorkoutNextRoutine(WorkoutNextRoutineDTO workoutNextRoutineDTO) {
        WorkoutLogDTO workoutLogDTO = workoutNextRoutineDTO.getWorkoutLog();
        User user = userRepository.findByUsername(workoutNextRoutineDTO.getUsername());
        WorkoutRoutine workoutRoutine = WorkoutRoutine.builder()
                .title(workoutNextRoutineDTO.getTitle())
                .currentDate(LocalDate.now())
                .user(user)
                .build();
        workoutRoutineRepository.save(workoutRoutine);

        WorkoutLog workoutLog = WorkoutLog.builder()
                .workoutName(workoutLogDTO.getWorkoutName())
                .workoutRoutine(workoutRoutine)
                .build();
        workoutLogRepository.save(workoutLog);

        List<WorkoutSetDTO> workoutLogDTOSets = workoutLogDTO.getSets();
        for (WorkoutSetDTO workoutLogDTOSet : workoutLogDTOSets) {
            WorkoutSet workoutSet = WorkoutSet.builder()
                    .weight(workoutLogDTOSet.getWeight())
                    .repetitionCount(workoutLogDTOSet.getRepetitionCount())
                    .allVolume(workoutLogDTOSet.getWeight() * workoutLogDTOSet.getRepetitionCount())
                    .workoutLog(workoutLog)
                    .build();
            workoutSetRepository.save(workoutSet);
        }
    }

}
