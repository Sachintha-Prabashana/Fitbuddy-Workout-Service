package lk.ijse.eca.workoutservice.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExerciseResponseDTO {
    private String exerciseId;
    private String name;
    private String category;
    private Integer defaultSets;
    private Integer defaultReps;
    private Double defaultWeight;
    private Integer defaultDurationSeconds;
    private Integer order;
}
