package lk.ijse.eca.workoutservice.controller;

import jakarta.validation.Valid;
import lk.ijse.eca.workoutservice.dto.request.*;
import lk.ijse.eca.workoutservice.dto.response.ApiResponse;
import lk.ijse.eca.workoutservice.dto.response.MemberWorkoutResponseDTO;
import lk.ijse.eca.workoutservice.dto.response.WorkoutPlanResponseDTO;
import lk.ijse.eca.workoutservice.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    // --- TEMPLATE PLANS ---

    @PostMapping("/plans")
    public ResponseEntity<ApiResponse<WorkoutPlanResponseDTO>> createPlan(@Valid @RequestBody WorkoutPlanCreateDTO dto) {
        WorkoutPlanResponseDTO created = workoutService.createPlan(dto);
        return new ResponseEntity<>(
                ApiResponse.success("Workout plan template created successfully", created),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/plans/trainer/{trainerId}")
    public ResponseEntity<ApiResponse<List<WorkoutPlanResponseDTO>>> getPlansByTrainer(@PathVariable Long trainerId) {
        List<WorkoutPlanResponseDTO> plans = workoutService.getPlansByTrainer(trainerId);
        return ResponseEntity.ok(
                ApiResponse.success("Trainer plans retrieved successfully", plans)
        );
    }

    @GetMapping("/plans/{planId}")
    public ResponseEntity<ApiResponse<WorkoutPlanResponseDTO>> getPlanById(@PathVariable String planId) {
        WorkoutPlanResponseDTO plan = workoutService.getPlanById(planId);
        return ResponseEntity.ok(
                ApiResponse.success("Workout plan template retrieved successfully", plan)
        );
    }

    // --- ASSIGNMENT & EXECUTION ---

    @PostMapping("/plans/{planId}/assign")
    public ResponseEntity<ApiResponse<MemberWorkoutResponseDTO>> assignPlanToMember(
            @PathVariable String planId,
            @Valid @RequestBody WorkoutAssignDTO dto) {
        MemberWorkoutResponseDTO assigned = workoutService.assignPlanToMember(planId, dto);
        return new ResponseEntity<>(
                ApiResponse.success("Workout plan successfully assigned to member", assigned),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/active/{memberId}")
    public ResponseEntity<ApiResponse<MemberWorkoutResponseDTO>> getMemberActiveWorkout(@PathVariable Long memberId) {
        MemberWorkoutResponseDTO active = workoutService.getMemberActiveWorkout(memberId);
        return ResponseEntity.ok(
                ApiResponse.success("Active workout retrieved successfully", active)
        );
    }

    @PutMapping("/active/{memberWorkoutId}/exercises/{exerciseId}/sets/{setNumber}")
    public ResponseEntity<ApiResponse<MemberWorkoutResponseDTO>> updateSetProgress(
            @PathVariable String memberWorkoutId,
            @PathVariable String exerciseId,
            @PathVariable int setNumber,
            @Valid @RequestBody SetProgressUpdateDTO dto) {
        MemberWorkoutResponseDTO updated = workoutService.updateSetProgress(memberWorkoutId, exerciseId, setNumber, dto);
        return ResponseEntity.ok(
                ApiResponse.success("Set progress logged successfully", updated)
        );
    }

    @PutMapping("/active/{memberWorkoutId}/exercises/{exerciseId}/status")
    public ResponseEntity<ApiResponse<MemberWorkoutResponseDTO>> updateExerciseStatus(
            @PathVariable String memberWorkoutId,
            @PathVariable String exerciseId,
            @Valid @RequestBody ExerciseProgressUpdateDTO dto) {
        MemberWorkoutResponseDTO updated = workoutService.updateExerciseStatus(memberWorkoutId, exerciseId, dto);
        return ResponseEntity.ok(
                ApiResponse.success("Exercise progress updated successfully", updated)
        );
    }

    @PostMapping("/active/{memberWorkoutId}/complete")
    public ResponseEntity<ApiResponse<MemberWorkoutResponseDTO>> completeWorkout(
            @PathVariable String memberWorkoutId,
            @RequestBody(required = false) WorkoutCompleteDTO dto) {
        String notes = dto != null ? dto.getNotes() : "";
        MemberWorkoutResponseDTO completed = workoutService.completeWorkout(memberWorkoutId, notes);
        return ResponseEntity.ok(
                ApiResponse.success("Workout session marked as completed", completed)
        );
    }

    @GetMapping("/history/{memberId}")
    public ResponseEntity<ApiResponse<List<MemberWorkoutResponseDTO>>> getMemberWorkoutHistory(@PathVariable Long memberId) {
        return new ResponseEntity<>(
                ApiResponse.success("Workout history retrieved successfully", workoutService.getMemberWorkoutHistory(memberId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/member/{memberId}/all")
    public ResponseEntity<ApiResponse<List<MemberWorkoutResponseDTO>>> getAllMemberWorkouts(@PathVariable Long memberId) {
        return new ResponseEntity<>(
                ApiResponse.success("All member workouts retrieved successfully", workoutService.getAllMemberWorkouts(memberId)),
                HttpStatus.OK
        );
    }
}
