package co.edu.ustavillavicencio.impostor.services;

import co.edu.ustavillavicencio.impostor.dtos.response.MeResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoundCloseResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.StartResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.VotesResponse;

import java.util.UUID;

public interface GameService {
    StartResponse start(String roomCode, UUID hostPlayerId);
    MeResponse me(String roomCode, UUID playerId);
    VotesResponse votes(String roomCode, UUID voterId, UUID votedId);
    RoundCloseResponse roundClose(String roomCode, UUID hostPlayerId);
}
