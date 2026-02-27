package co.edu.ustavillavicencio.impostor.services;

import co.edu.ustavillavicencio.impostor.dtos.response.MeResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoundCloseResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.GameStartResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.VoteCreateResponse;

import java.util.UUID;

public interface GameService {
    GameStartResponse start(String roomCode, UUID hostPlayerId);
    MeResponse me(String roomCode, UUID playerId);
    VoteCreateResponse votes(String roomCode, UUID voterId, UUID votedId);
    RoundCloseResponse roundClose(String roomCode, UUID hostPlayerId);
}
