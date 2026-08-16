package lk.ijse.eca.workoutservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidWorkoutStateException extends RuntimeException {
    public InvalidWorkoutStateException(String message) {
        super(message);
    }
}
