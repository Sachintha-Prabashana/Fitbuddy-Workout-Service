package lk.ijse.eca.workoutservice.document;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetProgress {
    private Integer setNumber;
    private Integer repsCompleted;
    private Double weightLifted;
    private Boolean completed;
}
