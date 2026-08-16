package lk.ijse.eca.workoutservice.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "member_workouts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
    @CompoundIndex(name = "member_status_idx", def = "{'memberId': 1, 'status': 1}")
})
public class MemberWorkout {

    @Id
    private String id;

    @Indexed
    private Long memberId;

    private Long trainerId;

    private String planId; // Original workout template plan ID (can be null)

    private String name;
    private String description;

    private LocalDate assignedDate;

    @Builder.Default
    private WorkoutStatus status = WorkoutStatus.ASSIGNED;

    @Builder.Default
    private List<MemberExerciseProgress> exercises = new ArrayList<>();

    private Instant startedAt;
    private Instant completedAt;

    private String notes;
}
