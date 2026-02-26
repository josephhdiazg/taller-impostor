package co.edu.ustavillavicencio.impostor.repositories;

import co.edu.ustavillavicencio.impostor.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
    Optional<RoomEntity> findByCode(String code);
}
