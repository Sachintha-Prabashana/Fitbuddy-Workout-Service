package lk.ijse.eca.workoutservice.repository;

import lk.ijse.eca.workoutservice.document.WorkoutPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutPlanRepository extends MongoRepository<WorkoutPlan, String> {
    List<WorkoutPlan> findByTrainerId(Long trainerId);
}
