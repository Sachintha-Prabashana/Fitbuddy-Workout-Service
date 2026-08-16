package lk.ijse.eca.workoutservice.dto.response;

import lk.ijse.eca.workoutservice.document.ExerciseStatus;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberExerciseProgressResponseDTO {
    private String exerciseId;
    private String name;
    private Integer targetSets;
    private Integer targetReps;
    private Double targetWeight;
    private Integer targetDurationSeconds;
    private Integer order;
    private ExerciseStatus status;
    private List<SetProgressResponseDTO> actualSets;
}
