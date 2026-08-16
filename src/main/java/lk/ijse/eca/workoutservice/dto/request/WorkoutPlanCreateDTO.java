package lk.ijse.eca.workoutservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.ijse.eca.workoutservice.document.DifficultyLevel;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutPlanCreateDTO {
    @NotNull(message = "Trainer ID is required")
    private Long trainerId;

    @NotBlank(message = "Workout plan name is required")
    private String name;

    private String description;

    private DifficultyLevel difficulty;

    private List<WorkoutExerciseDTO> exercises;
}
