package lk.ijse.eca.workoutservice.dto.response;

import lk.ijse.eca.workoutservice.document.DifficultyLevel;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutPlanResponseDTO {
    private String id;
    private Long trainerId;
    private String name;
    private String description;
    private DifficultyLevel difficulty;
    private List<WorkoutExerciseResponseDTO> exercises;
    private Instant createdAt;
    private Instant updatedAt;
}
