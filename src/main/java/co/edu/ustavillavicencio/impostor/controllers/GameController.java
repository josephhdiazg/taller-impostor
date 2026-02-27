package co.edu.ustavillavicencio.impostor.controllers;

import co.edu.ustavillavicencio.impostor.dtos.request.VoteCreateRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.MeResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoundCloseResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.GameStartResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.VoteCreateResponse;
import co.edu.ustavillavicencio.impostor.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class GameController {
    private final GameService service;

    @PostMapping("/{roomCode}/start")
    public GameStartResponse start(@PathVariable String roomCode, @RequestParam UUID hostPlayerId) {
        return service.start(roomCode, hostPlayerId);
    }

    @GetMapping("/{roomCode}/me")
    public MeResponse me(@PathVariable String roomCode, @RequestParam UUID playerId) {
        return service.me(roomCode, playerId);
    }

    @PostMapping("/{roomCode}/votes")
    public VoteCreateResponse votes(@PathVariable String roomCode, @RequestParam UUID voterId, @Validated @RequestBody VoteCreateRequest request) {
        return service.votes(roomCode, voterId, request.getVotedId());
    }

    @PostMapping("/{roomCode}/round/close")
    public RoundCloseResponse roundClose(@PathVariable String roomCode, @RequestParam UUID hostPlayerId) {
        return service.roundClose(roomCode, hostPlayerId);
    }
}
