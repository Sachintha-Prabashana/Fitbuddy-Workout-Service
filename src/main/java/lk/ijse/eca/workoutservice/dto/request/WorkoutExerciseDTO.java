package lk.ijse.eca.workoutservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseDTO {
    @NotBlank(message = "Exercise ID is required")
    private String exerciseId;

    @NotBlank(message = "Exercise name is required")
    private String name;

    private String category;

    @Min(value = 1, message = "Default sets must be at least 1")
    private Integer defaultSets;

    @Min(value = 1, message = "Default reps must be at least 1")
    private Integer defaultReps;

    private Double defaultWeight;
    private Integer defaultDurationSeconds;

    private Integer order;
}
