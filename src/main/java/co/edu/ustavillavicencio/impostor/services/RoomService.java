package co.edu.ustavillavicencio.impostor.services;

import co.edu.ustavillavicencio.impostor.dtos.request.RoomRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomCreateResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomResponse;

import java.util.List;

public interface RoomService {
    RoomResponse findByCode(String code);
    RoomCreateResponse create(RoomRequest dto);
}
