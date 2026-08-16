package lk.ijse.eca.workoutservice.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetProgressResponseDTO {
    private Integer setNumber;
    private Integer repsCompleted;
    private Double weightLifted;
    private Boolean completed;
}
