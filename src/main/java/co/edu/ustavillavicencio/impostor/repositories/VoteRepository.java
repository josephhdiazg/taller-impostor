package co.edu.ustavillavicencio.impostor.repositories;

import co.edu.ustavillavicencio.impostor.entities.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {
}
