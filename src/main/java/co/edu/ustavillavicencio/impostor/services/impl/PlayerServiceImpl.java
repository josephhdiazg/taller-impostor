package co.edu.ustavillavicencio.impostor.services.impl;

import co.edu.ustavillavicencio.impostor.dtos.request.PlayerRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.PlayerResponse;
import co.edu.ustavillavicencio.impostor.entities.PlayerEntity;
import co.edu.ustavillavicencio.impostor.entities.RoomEntity;
import co.edu.ustavillavicencio.impostor.repositories.PlayerRepository;
import co.edu.ustavillavicencio.impostor.repositories.RoomRepository;
import co.edu.ustavillavicencio.impostor.services.PlayerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final RoomRepository roomRepo;
    private final PlayerRepository repo;

    @Override
    public List<PlayerResponse> findAll(String roomCode) {
        RoomEntity room = roomRepo.findByCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("RoomEntity not found " + roomCode));
        return repo.findAll().stream()
                .filter(p -> p.getRoomId().equals(room.getId()))
                .map(this::toDto).toList();
    }

    @Override
    public PlayerResponse create(String roomCode, PlayerRequest dto) {
        PlayerEntity saved = repo.save(toEntity(dto));
        RoomEntity room = findRoomByCode(roomCode);
        saved.setRoomId(room.getId());
        return toDto(saved);
    }

    private RoomEntity findRoomByCode(String roomCode) {
        return roomRepo.findByCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("RoomEntity not found " + roomCode));
    }

    private PlayerResponse toDto(PlayerEntity p) {
        return new PlayerResponse(p.getId(), p.getRoomId(), p.getNickname(), p.isAlive());
    }

    private PlayerEntity toEntity(PlayerRequest p) {
        return new PlayerEntity(null, null, p.getNickname(), true);
    }
}
