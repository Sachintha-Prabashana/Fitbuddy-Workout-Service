package lk.ijse.eca.workoutservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetProgressUpdateDTO {
    @NotNull(message = "Reps completed is required")
    @Min(value = 0, message = "Reps completed cannot be negative")
    private Integer repsCompleted;

    @NotNull(message = "Weight lifted is required")
    @Min(value = 0, message = "Weight lifted cannot be negative")
    private Double weightLifted;

    @NotNull(message = "Completed status is required")
    private Boolean completed;
}
