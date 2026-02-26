package co.edu.ustavillavicencio.impostor.services.impl;

import co.edu.ustavillavicencio.impostor.RandomStringGenerator;
import co.edu.ustavillavicencio.impostor.dtos.request.RoomRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.PlayerListResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomCreateResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomResponse;
import co.edu.ustavillavicencio.impostor.entities.PlayerEntity;
import co.edu.ustavillavicencio.impostor.entities.RoomEntity;
import co.edu.ustavillavicencio.impostor.enums.Category;
import co.edu.ustavillavicencio.impostor.enums.RoomStatus;
import co.edu.ustavillavicencio.impostor.repositories.PlayerRepository;
import co.edu.ustavillavicencio.impostor.repositories.RoomRepository;
import co.edu.ustavillavicencio.impostor.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final PlayerRepository playerRepo;
    private final RoomRepository repo;

    @Override
    public RoomResponse findByCode(String code) {
        RoomEntity r = repo.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException(("RoomEntity not found " + code)));
        return toDto(r);
    }

    @Override
    public RoomCreateResponse create(RoomRequest dto) {
        RoomEntity saved = repo.save(toEntity(dto));
        PlayerEntity hostPlayer = createHostPlayer(saved, dto.getHostNickname());
        saved.setHostPlayerId(hostPlayer.getId());
        return toCreateDto(saved);
    }

    private PlayerEntity createHostPlayer(RoomEntity room, String nickname) {
        return playerRepo.save(new PlayerEntity(null, room.getId(), nickname, true));
    }

    private RoomResponse toDto(RoomEntity r) {
        List<PlayerListResponse> players = playerRepo.findAll().stream()
                .filter(p -> p.getRoomId().equals(r.getId()))
                .map(p -> new PlayerListResponse(p.getId(), p.getNickname(), p.isAlive()))
                .toList();
        return new RoomResponse(r.getStatus().getLabel(), r.getCategory().getLabel(), r.getCurrentRound(), players);
    }

    private RoomCreateResponse toCreateDto(RoomEntity r) {
        return new RoomCreateResponse(r.getId(), r.getCode(), r.getStatus().getLabel(), r.getHostPlayerId(), r.getCategory().getLabel(), r.getImpostorCount(), r.getCurrentRound());
    }

    private RoomEntity toEntity(RoomRequest dto) {
        String code = RandomStringGenerator.generateRandomAlphanumeric(6);
        Category category = Category.from(dto.getCategory());

        return new RoomEntity(null, code, RoomStatus.LOBBY, null, category, dto.getImpostorCount(), 0, null, null);
    }
}
