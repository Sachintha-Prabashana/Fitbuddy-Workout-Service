package lk.ijse.eca.workoutservice.repository;

import lk.ijse.eca.workoutservice.document.MemberWorkout;
import lk.ijse.eca.workoutservice.document.WorkoutStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberWorkoutRepository extends MongoRepository<MemberWorkout, String> {
    List<MemberWorkout> findByMemberId(Long memberId);
    
    Optional<MemberWorkout> findFirstByMemberIdAndStatus(Long memberId, WorkoutStatus status);
}
