package lk.ijse.eca.workoutservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lk.ijse.eca.workoutservice.document.ExerciseStatus;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseProgressUpdateDTO {
    @NotNull(message = "Exercise status is required")
    private ExerciseStatus status;

    private List<SetProgressUpdateDTO> actualSets;
}
