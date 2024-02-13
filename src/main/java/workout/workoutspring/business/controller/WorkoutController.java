package workout.workoutspring.business.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workout.workoutspring.business.domain.dto.request.WorkoutNextRoutineDTO;
import workout.workoutspring.business.domain.dto.request.WorkoutRoutineDTO;
import workout.workoutspring.business.domain.dto.response.FiveWorkoutResponse;
import workout.workoutspring.business.domain.dto.response.WorkoutResponseDTO;
import workout.workoutspring.business.domain.dto.response.WorkoutRoutineResponseDTO;
import workout.workoutspring.business.service.WorkoutService;
import workout.workoutspring.global.response.ApiResponse;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workout")
public class WorkoutController {

    private final WorkoutService workoutService;

    //운동 일지 작성 핸들러(만약 비즈니스 로직이 운동 일지 작성에서 완료 버튼 누를 때 이때까지 작성된 기록 한번에 저장할 때)
    @PostMapping("/create-routine")
    public ApiResponse<String> saveWorkoutRoutine(@RequestBody WorkoutRoutineDTO workoutRoutineDTO) {
        workoutService.saveWorkoutRoutine(workoutRoutineDTO);
        return ApiResponse.onSuccess("운동 일지 작성 완료");
    }

    //운동 일지 조회 핸들러
    @GetMapping("/my-workout/show-routine/{userId}/{date}")
    public ApiResponse<WorkoutRoutineResponseDTO> showWorkoutRoutine(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ApiResponse.onSuccess(
                workoutService.showWorkoutRoutine(userId, date));
    }

    //오늘 운동 기록하기 버튼 클릭시
    @GetMapping("/today-workout-write")
    public ResponseEntity<FiveWorkoutResponse> clickTodayWorkoutWrite() {
        FiveWorkoutResponse fiveWorkoutResponse = FiveWorkoutResponse.builder()
                .title("운동하실 부위를 선택해 주세요.")
                .chest("가슴")
                .back("등")
                .shoulder("어깨")
                .leg("하체")
                .arm("팔")
                .build();
        return ResponseEntity.ok(fiveWorkoutResponse);
    }

    //가슴, 등, 어깨, 하체, 팔 (chest, back, shoulder, leg, arm 중에서 클릭하면)
    @GetMapping("/today-workout-write/{workoutKind}")
    public ApiResponse<List<WorkoutResponseDTO>> clickChest(@PathVariable String workoutKind) {
        return ApiResponse.onSuccess(
                workoutService.findWorkoutList(workoutKind));
    }

    //다음 종목 선택 시
    @PostMapping("/today-workout-write/next-workout")
    public ApiResponse<String> chooseNext(@RequestBody WorkoutNextRoutineDTO workoutNextRoutineDTO) {
        workoutService.saveWorkoutNextRoutine(workoutNextRoutineDTO);
        return ApiResponse.onSuccess("저장 완료, 다음 종목 선택");
    }
}
