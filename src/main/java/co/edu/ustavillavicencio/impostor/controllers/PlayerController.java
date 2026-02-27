package co.edu.ustavillavicencio.impostor.controllers;

import co.edu.ustavillavicencio.impostor.dtos.request.PlayerCreateRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.PlayerCreateResponse;
import co.edu.ustavillavicencio.impostor.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService service;

    @GetMapping("/{roomCode}/players")
    public List<PlayerCreateResponse> index(@PathVariable String roomCode) {
        return service.findAll(roomCode);
    }

    @PostMapping("/{roomCode}/players")
    public PlayerCreateResponse create(@PathVariable String roomCode, @Validated @RequestBody PlayerCreateRequest dto) {
        return service.create(roomCode, dto);
    }
}
