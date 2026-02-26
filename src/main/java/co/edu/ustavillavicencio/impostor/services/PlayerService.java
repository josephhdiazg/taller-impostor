package co.edu.ustavillavicencio.impostor.services;

import co.edu.ustavillavicencio.impostor.dtos.request.PlayerRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.PlayerResponse;

import java.util.List;

public interface PlayerService {
    List<PlayerResponse> findAll(String roomCode);
    PlayerResponse create(String roomCode, PlayerRequest dto);
}
