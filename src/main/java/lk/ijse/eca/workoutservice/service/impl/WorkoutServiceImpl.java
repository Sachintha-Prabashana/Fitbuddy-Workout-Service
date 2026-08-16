package lk.ijse.eca.workoutservice.service.impl;

import lk.ijse.eca.workoutservice.document.*;
import lk.ijse.eca.workoutservice.dto.request.*;
import lk.ijse.eca.workoutservice.dto.response.ApiResponse;
import lk.ijse.eca.workoutservice.dto.response.MemberWorkoutResponseDTO;
import lk.ijse.eca.workoutservice.dto.response.UserResponseDTO;
import lk.ijse.eca.workoutservice.dto.response.WorkoutPlanResponseDTO;
import lk.ijse.eca.workoutservice.exception.InvalidWorkoutStateException;
import lk.ijse.eca.workoutservice.exception.ResourceNotFoundException;
import lk.ijse.eca.workoutservice.mapper.WorkoutMapper;
import lk.ijse.eca.workoutservice.repository.MemberWorkoutRepository;
import lk.ijse.eca.workoutservice.repository.WorkoutPlanRepository;
import lk.ijse.eca.workoutservice.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final MemberWorkoutRepository memberWorkoutRepository;
    private final RestClient restClient;

    @Override
    public WorkoutPlanResponseDTO createPlan(WorkoutPlanCreateDTO dto) {
        validateUserRole(dto.getTrainerId(), "TRAINER");
        WorkoutPlan plan = WorkoutMapper.toEntity(dto);
        WorkoutPlan saved = workoutPlanRepository.save(plan);
        return WorkoutMapper.toResponseDTO(saved);
    }

    @Override
    public List<WorkoutPlanResponseDTO> getPlansByTrainer(Long trainerId) {
        return workoutPlanRepository.findByTrainerId(trainerId).stream()
                .map(WorkoutMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutPlanResponseDTO getPlanById(String planId) {
        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout plan template not found with ID: " + planId));
        return WorkoutMapper.toResponseDTO(plan);
    }

    @Override
    public MemberWorkoutResponseDTO assignPlanToMember(String planId, WorkoutAssignDTO dto) {
        validateUserRole(dto.getMemberId(), "MEMBER");
        validateUserRole(dto.getTrainerId(), "TRAINER");

        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout plan template not found with ID: " + planId));

        // Check if member already has an active workout (ASSIGNED or IN_PROGRESS)
        Optional<MemberWorkout> activeWorkout = memberWorkoutRepository
                .findFirstByMemberIdAndStatus(dto.getMemberId(), WorkoutStatus.IN_PROGRESS);
        if (activeWorkout.isEmpty()) {
            activeWorkout = memberWorkoutRepository
                    .findFirstByMemberIdAndStatus(dto.getMemberId(), WorkoutStatus.ASSIGNED);
        }

        if (activeWorkout.isPresent()) {
            throw new InvalidWorkoutStateException("Member already has an active workout session. Complete or skip the active workout first.");
        }

        // Copy exercises and initialize sets
        List<MemberExerciseProgress> exercises = plan.getExercises().stream()
                .map(e -> {
                    List<SetProgress> sets = new ArrayList<>();
                    int setsCount = e.getDefaultSets() != null ? e.getDefaultSets() : 3;
                    for (int i = 1; i <= setsCount; i++) {
                        sets.add(SetProgress.builder()
                                .setNumber(i)
                                .repsCompleted(0)
                                .weightLifted(0.0)
                                .completed(false)
                                .build());
                    }
                    return MemberExerciseProgress.builder()
                            .exerciseId(e.getExerciseId())
                            .name(e.getName())
                            .targetSets(e.getDefaultSets())
                            .targetReps(e.getDefaultReps())
                            .targetWeight(e.getDefaultWeight())
                            .targetDurationSeconds(e.getDefaultDurationSeconds())
                            .order(e.getOrder())
                            .status(ExerciseStatus.PENDING)
                            .actualSets(sets)
                            .build();
                }).collect(Collectors.toList());

        MemberWorkout memberWorkout = MemberWorkout.builder()
                .memberId(dto.getMemberId())
                .trainerId(dto.getTrainerId())
                .planId(plan.getId())
                .name(plan.getName())
                .description(plan.getDescription())
                .assignedDate(LocalDate.now())
                .status(WorkoutStatus.ASSIGNED)
                .exercises(exercises)
                .build();

        MemberWorkout saved = memberWorkoutRepository.save(memberWorkout);
        return WorkoutMapper.toResponseDTO(saved);
    }

    @Override
    public MemberWorkoutResponseDTO getMemberActiveWorkout(Long memberId) {
        // Return active workout (IN_PROGRESS first, then ASSIGNED)
        Optional<MemberWorkout> active = memberWorkoutRepository
                .findFirstByMemberIdAndStatus(memberId, WorkoutStatus.IN_PROGRESS);
        if (active.isEmpty()) {
            active = memberWorkoutRepository
                    .findFirstByMemberIdAndStatus(memberId, WorkoutStatus.ASSIGNED);
        }
        
        return active.map(WorkoutMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No active workout session found for member ID: " + memberId));
    }

    @Override
    public MemberWorkoutResponseDTO updateSetProgress(String memberWorkoutId, String exerciseId, int setNumber, SetProgressUpdateDTO dto) {
        MemberWorkout workout = memberWorkoutRepository.findById(memberWorkoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Member workout session not found with ID: " + memberWorkoutId));

        if (workout.getStatus() == WorkoutStatus.COMPLETED || workout.getStatus() == WorkoutStatus.SKIPPED) {
            throw new InvalidWorkoutStateException("Cannot update progress on a completed or skipped workout.");
        }

        // If workout status is ASSIGNED, transition to IN_PROGRESS and record startedAt
        if (workout.getStatus() == WorkoutStatus.ASSIGNED) {
            workout.setStatus(WorkoutStatus.IN_PROGRESS);
            workout.setStartedAt(Instant.now());
        }

        // Find exercise
        MemberExerciseProgress exercise = workout.getExercises().stream()
                .filter(e -> e.getExerciseId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Exercise with ID " + exerciseId + " not found in this workout session."));

        List<SetProgress> sets = exercise.getActualSets();
        if (sets == null) {
            sets = new ArrayList<>();
            exercise.setActualSets(sets);
        }

        // Find set by setNumber or create/expand if it does not exist
        SetProgress targetSet = null;
        for (SetProgress s : sets) {
            if (s.getSetNumber() == setNumber) {
                targetSet = s;
                break;
            }
        }

        if (targetSet == null) {
            targetSet = SetProgress.builder()
                    .setNumber(setNumber)
                    .repsCompleted(dto.getRepsCompleted())
                    .weightLifted(dto.getWeightLifted())
                    .completed(dto.getCompleted())
                    .build();
            sets.add(targetSet);
        } else {
            targetSet.setRepsCompleted(dto.getRepsCompleted());
            targetSet.setWeightLifted(dto.getWeightLifted());
            targetSet.setCompleted(dto.getCompleted());
        }

        // Update overall exercise completion status
        boolean allSetsDone = sets.stream().allMatch(SetProgress::getCompleted);
        if (allSetsDone && !sets.isEmpty()) {
            exercise.setStatus(ExerciseStatus.COMPLETED);
        } else {
            exercise.setStatus(ExerciseStatus.PENDING);
        }

        MemberWorkout saved = memberWorkoutRepository.save(workout);
        return WorkoutMapper.toResponseDTO(saved);
    }

    @Override
    public MemberWorkoutResponseDTO updateExerciseStatus(String memberWorkoutId, String exerciseId, ExerciseProgressUpdateDTO dto) {
        MemberWorkout workout = memberWorkoutRepository.findById(memberWorkoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Member workout session not found with ID: " + memberWorkoutId));

        if (workout.getStatus() == WorkoutStatus.COMPLETED || workout.getStatus() == WorkoutStatus.SKIPPED) {
            throw new InvalidWorkoutStateException("Cannot update progress on a completed or skipped workout.");
        }

        if (workout.getStatus() == WorkoutStatus.ASSIGNED) {
            workout.setStatus(WorkoutStatus.IN_PROGRESS);
            workout.setStartedAt(Instant.now());
        }

        MemberExerciseProgress exercise = workout.getExercises().stream()
                .filter(e -> e.getExerciseId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Exercise with ID " + exerciseId + " not found in this workout session."));

        exercise.setStatus(dto.getStatus());
        if (dto.getActualSets() != null) {
            List<SetProgress> updatedSets = dto.getActualSets().stream()
                    .map(setDto -> SetProgress.builder()
                            .setNumber(setDto.getRepsCompleted()) // wait: setNumber should be set mapped from index
                            .repsCompleted(setDto.getRepsCompleted())
                            .weightLifted(setDto.getWeightLifted())
                            .completed(setDto.getCompleted())
                            .build())
                    .collect(Collectors.toList());
            
            // Fix set numbers
            for (int i = 0; i < updatedSets.size(); i++) {
                updatedSets.get(i).setSetNumber(i + 1);
            }
            exercise.setActualSets(updatedSets);
        }

        MemberWorkout saved = memberWorkoutRepository.save(workout);
        return WorkoutMapper.toResponseDTO(saved);
    }

    @Override
    public MemberWorkoutResponseDTO completeWorkout(String memberWorkoutId, String notes) {
        MemberWorkout workout = memberWorkoutRepository.findById(memberWorkoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Member workout session not found with ID: " + memberWorkoutId));

        if (workout.getStatus() == WorkoutStatus.COMPLETED || workout.getStatus() == WorkoutStatus.SKIPPED) {
            throw new InvalidWorkoutStateException("Workout is already completed or skipped.");
        }

        workout.setStatus(WorkoutStatus.COMPLETED);
        workout.setCompletedAt(Instant.now());
        workout.setNotes(notes);

        MemberWorkout saved = memberWorkoutRepository.save(workout);
        return WorkoutMapper.toResponseDTO(saved);
    }

    @Override
    public List<MemberWorkoutResponseDTO> getMemberWorkoutHistory(Long memberId) {
        return memberWorkoutRepository.findByMemberId(memberId).stream()
                .filter(w -> w.getStatus() == WorkoutStatus.COMPLETED || w.getStatus() == WorkoutStatus.SKIPPED)
                .map(WorkoutMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    private void validateUserRole(Long userId, String expectedRole) {
        try {
            ApiResponse<UserResponseDTO> response = restClient.get()
                    .uri("/api/v1/members/{userId}", userId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<UserResponseDTO>>() {});

            if (response == null || !response.isSuccess() || response.getData() == null || response.getData().getContent() == null) {
                throw new ResourceNotFoundException(expectedRole + " not found with ID: " + userId);
            }

            String actualRole = response.getData().getContent().getRole();
            if (!expectedRole.equalsIgnoreCase(actualRole)) {
                throw new InvalidWorkoutStateException("User with ID " + userId + " is not a " + expectedRole + " (actual role: " + actualRole + ")");
            }
        } catch (Exception ex) {
            if (ex instanceof ResourceNotFoundException || ex instanceof InvalidWorkoutStateException) {
                throw ex;
            }
            throw new ResourceNotFoundException("Could not verify " + expectedRole + " with ID " + userId + " through member-service: " + ex.getMessage());
        }
    }
}
