package workout.workoutspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workout.workoutspring.domain.dto.request.WorkoutRoutineDTO;
import workout.workoutspring.domain.dto.response.WorkoutRoutineResponseDTO;
import workout.workoutspring.service.WorkoutService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workout")
public class WorkoutController {

    private final WorkoutService workoutService;

    //운동 일지 작성 핸들러
    @PostMapping("/create_routine")
    public ResponseEntity<String> saveWorkoutRoutine(@RequestBody WorkoutRoutineDTO workoutRoutineDTO) {
        workoutService.saveWorkoutRoutine(workoutRoutineDTO);
        return new ResponseEntity<>("기록 완료", HttpStatus.OK);
    }

    //운동 일지 조회 핸들러
    @GetMapping("/show_routine/{userId}")
    public ResponseEntity<WorkoutRoutineResponseDTO> showWorkoutRoutine(@PathVariable Long userId) {
        WorkoutRoutineResponseDTO workoutRoutineResponseDTO = workoutService.showWorkoutRoutine(userId);
        return ResponseEntity.ok(workoutRoutineResponseDTO);
    }
}
