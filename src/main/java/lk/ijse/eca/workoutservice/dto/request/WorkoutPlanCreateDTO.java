package lk.ijse.eca.workoutservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Valid
    @NotNull(message = "At least one exercise is required")
    @Size(min = 1, message = "At least one exercise is required")
    private List<WorkoutExerciseDTO> exercises;
}
