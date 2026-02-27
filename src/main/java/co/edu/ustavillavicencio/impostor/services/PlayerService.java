package co.edu.ustavillavicencio.impostor.services;

import co.edu.ustavillavicencio.impostor.dtos.request.PlayerCreateRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.PlayerCreateResponse;

import java.util.List;

public interface PlayerService {
    List<PlayerCreateResponse> findAll(String roomCode);
    PlayerCreateResponse create(String roomCode, PlayerCreateRequest dto);
}
