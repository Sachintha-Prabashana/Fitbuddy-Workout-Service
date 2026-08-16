package lk.ijse.eca.workoutservice.document;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExercise {
    private String exerciseId;
    private String name;
    private String category;
    private Integer defaultSets;
    private Integer defaultReps;
    private Double defaultWeight;
    private Integer defaultDurationSeconds;
    private Integer order;
}
