package lk.ijse.eca.workoutservice.dto.response;

import lk.ijse.eca.workoutservice.document.WorkoutStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberWorkoutResponseDTO {
    private String id;
    private Long memberId;
    private Long trainerId;
    private String planId;
    private String name;
    private String description;
    private LocalDate assignedDate;
    private WorkoutStatus status;
    private List<MemberExerciseProgressResponseDTO> exercises;
    private Instant startedAt;
    private Instant completedAt;
    private String notes;
}
