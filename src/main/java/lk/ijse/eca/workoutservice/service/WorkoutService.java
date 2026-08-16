package lk.ijse.eca.workoutservice.service;

import lk.ijse.eca.workoutservice.dto.request.*;
import lk.ijse.eca.workoutservice.dto.response.MemberWorkoutResponseDTO;
import lk.ijse.eca.workoutservice.dto.response.WorkoutPlanResponseDTO;

import java.util.List;

public interface WorkoutService {
    WorkoutPlanResponseDTO createPlan(WorkoutPlanCreateDTO dto);
    
    List<WorkoutPlanResponseDTO> getPlansByTrainer(Long trainerId);
    
    WorkoutPlanResponseDTO getPlanById(String planId);
    
    MemberWorkoutResponseDTO assignPlanToMember(String planId, WorkoutAssignDTO dto);
    
    MemberWorkoutResponseDTO getMemberActiveWorkout(Long memberId);
    
    MemberWorkoutResponseDTO updateSetProgress(String memberWorkoutId, String exerciseId, int setNumber, SetProgressUpdateDTO dto);
    
    MemberWorkoutResponseDTO updateExerciseStatus(String memberWorkoutId, String exerciseId, ExerciseProgressUpdateDTO dto);
    
    MemberWorkoutResponseDTO completeWorkout(String memberWorkoutId, String notes);
    
    List<MemberWorkoutResponseDTO> getMemberWorkoutHistory(Long memberId);
}
