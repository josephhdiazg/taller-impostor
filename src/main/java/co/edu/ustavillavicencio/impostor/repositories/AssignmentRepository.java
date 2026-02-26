package co.edu.ustavillavicencio.impostor.repositories;

import co.edu.ustavillavicencio.impostor.entities.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity, UUID> {
}
