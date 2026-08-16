package lk.ijse.eca.workoutservice.mapper;

import lk.ijse.eca.workoutservice.document.*;
import lk.ijse.eca.workoutservice.dto.request.WorkoutExerciseDTO;
import lk.ijse.eca.workoutservice.dto.request.WorkoutPlanCreateDTO;
import lk.ijse.eca.workoutservice.dto.response.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutMapper {

    public static WorkoutPlan toEntity(WorkoutPlanCreateDTO dto) {
        if (dto == null) return null;

        List<WorkoutExercise> exercises = dto.getExercises() == null ? Collections.emptyList() :
                dto.getExercises().stream()
                        .map(WorkoutMapper::toEntity)
                        .collect(Collectors.toList());

        return WorkoutPlan.builder()
                .trainerId(dto.getTrainerId())
                .name(dto.getName())
                .description(dto.getDescription())
                .difficulty(dto.getDifficulty())
                .exercises(exercises)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public static WorkoutExercise toEntity(WorkoutExerciseDTO dto) {
        if (dto == null) return null;
        return WorkoutExercise.builder()
                .exerciseId(dto.getExerciseId())
                .name(dto.getName())
                .category(dto.getCategory())
                .defaultSets(dto.getDefaultSets())
                .defaultReps(dto.getDefaultReps())
                .defaultWeight(dto.getDefaultWeight())
                .defaultDurationSeconds(dto.getDefaultDurationSeconds())
                .order(dto.getOrder())
                .build();
    }

    public static WorkoutPlanResponseDTO toResponseDTO(WorkoutPlan entity) {
        if (entity == null) return null;

        List<WorkoutExerciseResponseDTO> exercises = entity.getExercises() == null ? Collections.emptyList() :
                entity.getExercises().stream()
                        .map(WorkoutMapper::toResponseDTO)
                        .collect(Collectors.toList());

        return WorkoutPlanResponseDTO.builder()
                .id(entity.getId())
                .trainerId(entity.getTrainerId())
                .name(entity.getName())
                .description(entity.getDescription())
                .difficulty(entity.getDifficulty())
                .exercises(exercises)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static WorkoutExerciseResponseDTO toResponseDTO(WorkoutExercise entity) {
        if (entity == null) return null;
        return WorkoutExerciseResponseDTO.builder()
                .exerciseId(entity.getExerciseId())
                .name(entity.getName())
                .category(entity.getCategory())
                .defaultSets(entity.getDefaultSets())
                .defaultReps(entity.getDefaultReps())
                .defaultWeight(entity.getDefaultWeight())
                .defaultDurationSeconds(entity.getDefaultDurationSeconds())
                .order(entity.getOrder())
                .build();
    }

    public static MemberWorkoutResponseDTO toResponseDTO(MemberWorkout entity) {
        if (entity == null) return null;

        List<MemberExerciseProgressResponseDTO> exercises = entity.getExercises() == null ? Collections.emptyList() :
                entity.getExercises().stream()
                        .map(WorkoutMapper::toResponseDTO)
                        .collect(Collectors.toList());

        return MemberWorkoutResponseDTO.builder()
                .id(entity.getId())
                .memberId(entity.getMemberId())
                .trainerId(entity.getTrainerId())
                .planId(entity.getPlanId())
                .name(entity.getName())
                .description(entity.getDescription())
                .assignedDate(entity.getAssignedDate())
                .status(entity.getStatus())
                .exercises(exercises)
                .startedAt(entity.getStartedAt())
                .completedAt(entity.getCompletedAt())
                .notes(entity.getNotes())
                .build();
    }

    public static MemberExerciseProgressResponseDTO toResponseDTO(MemberExerciseProgress entity) {
        if (entity == null) return null;

        List<SetProgressResponseDTO> actualSets = entity.getActualSets() == null ? Collections.emptyList() :
                entity.getActualSets().stream()
                        .map(WorkoutMapper::toResponseDTO)
                        .collect(Collectors.toList());

        return MemberExerciseProgressResponseDTO.builder()
                .exerciseId(entity.getExerciseId())
                .name(entity.getName())
                .targetSets(entity.getTargetSets())
                .targetReps(entity.getTargetReps())
                .targetWeight(entity.getTargetWeight())
                .targetDurationSeconds(entity.getTargetDurationSeconds())
                .order(entity.getOrder())
                .status(entity.getStatus())
                .actualSets(actualSets)
                .build();
    }

    public static SetProgressResponseDTO toResponseDTO(SetProgress entity) {
        if (entity == null) return null;
        return SetProgressResponseDTO.builder()
                .setNumber(entity.getSetNumber())
                .repsCompleted(entity.getRepsCompleted())
                .weightLifted(entity.getWeightLifted())
                .completed(entity.getCompleted())
                .build();
    }
}
