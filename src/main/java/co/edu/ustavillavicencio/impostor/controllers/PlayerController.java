package co.edu.ustavillavicencio.impostor.controllers;

import co.edu.ustavillavicencio.impostor.dtos.request.PlayerRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.PlayerResponse;
import co.edu.ustavillavicencio.impostor.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService service;

    @GetMapping("/{roomCode}/players")
    public List<PlayerResponse> index(@PathVariable String roomCode) {
        return service.findAll(roomCode);
    }

    @PostMapping("/{roomCode}/players")
    public PlayerResponse create(@PathVariable String roomCode, @RequestBody PlayerRequest dto) {
        return service.create(roomCode, dto);
    }
}
