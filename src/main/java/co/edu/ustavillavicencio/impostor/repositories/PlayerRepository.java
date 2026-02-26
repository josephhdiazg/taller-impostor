package co.edu.ustavillavicencio.impostor.repositories;

import co.edu.ustavillavicencio.impostor.entities.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<PlayerEntity, UUID> {
    Optional<PlayerEntity> findByNickname(String nickname);
}
