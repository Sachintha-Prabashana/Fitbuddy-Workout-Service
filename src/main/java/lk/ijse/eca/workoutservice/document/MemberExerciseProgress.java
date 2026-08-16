package lk.ijse.eca.workoutservice.document;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberExerciseProgress {
    private String exerciseId;
    private String name;
    private Integer targetSets;
    private Integer targetReps;
    private Double targetWeight;
    private Integer targetDurationSeconds;
    private Integer order;

    @Builder.Default
    private ExerciseStatus status = ExerciseStatus.PENDING;

    @Builder.Default
    private List<SetProgress> actualSets = new ArrayList<>();
}
