package co.edu.ustavillavicencio.impostor.controllers;

import co.edu.ustavillavicencio.impostor.dtos.request.RoomRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomCreateResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomResponse;
import co.edu.ustavillavicencio.impostor.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService service;

    @PostMapping
    public RoomCreateResponse create(@RequestBody RoomRequest dto) {
        return service.create(dto);
    }

    @GetMapping("/{code}")
    public RoomResponse show(@PathVariable String code) {
        return service.findByCode(code);
    }
}
