package lk.ijse.eca.workoutservice.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "workout_plans")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutPlan {

    @Id
    private String id;

    @Indexed
    private Long trainerId;

    private String name;
    private String description;
    private DifficultyLevel difficulty;

    @Builder.Default
    private List<WorkoutExercise> exercises = new ArrayList<>();

    private Instant createdAt;
    private Instant updatedAt;
}
