package co.edu.ustavillavicencio.impostor.services.impl;

import co.edu.ustavillavicencio.impostor.dtos.request.PlayerCreateRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.PlayerCreateResponse;
import co.edu.ustavillavicencio.impostor.entities.PlayerEntity;
import co.edu.ustavillavicencio.impostor.entities.RoomEntity;
import co.edu.ustavillavicencio.impostor.repositories.PlayerRepository;
import co.edu.ustavillavicencio.impostor.repositories.RoomRepository;
import co.edu.ustavillavicencio.impostor.services.PlayerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final RoomRepository roomRepo;
    private final PlayerRepository repo;

    @Override
    public List<PlayerCreateResponse> findAll(String roomCode) {
        RoomEntity room = roomRepo.findByCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("RoomEntity not found " + roomCode));
        return repo.findAll().stream()
                .filter(p -> p.getRoomId().equals(room.getId()))
                .map(this::toDto).toList();
    }

    @Override
    public PlayerCreateResponse create(String roomCode, PlayerCreateRequest dto) {
        PlayerEntity saved = repo.save(toEntity(dto));
        RoomEntity room = findRoomByCode(roomCode);
        saved.setRoomId(room.getId());
        return toDto(saved);
    }

    private RoomEntity findRoomByCode(String roomCode) {
        return roomRepo.findByCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("RoomEntity not found " + roomCode));
    }

    private PlayerCreateResponse toDto(PlayerEntity p) {
        return new PlayerCreateResponse(p.getId(), p.getRoomId(), p.getNickname(), p.isAlive());
    }

    private PlayerEntity toEntity(PlayerCreateRequest p) {
        return new PlayerEntity(null, null, p.getNickname(), true);
    }
}
