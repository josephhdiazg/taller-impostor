package co.edu.ustavillavicencio.impostor.controllers;

import co.edu.ustavillavicencio.impostor.dtos.request.RoomCreateRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomCreateResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomShowResponse;
import co.edu.ustavillavicencio.impostor.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService service;

    @PostMapping
    public RoomCreateResponse create(@Validated @RequestBody RoomCreateRequest dto) {
        return service.create(dto);
    }

    @GetMapping("/{code}")
    public RoomShowResponse show(@PathVariable String code) {
        return service.findByCode(code);
    }
}
