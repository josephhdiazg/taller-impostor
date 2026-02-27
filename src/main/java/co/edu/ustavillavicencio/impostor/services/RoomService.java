package co.edu.ustavillavicencio.impostor.services;

import co.edu.ustavillavicencio.impostor.dtos.request.RoomCreateRequest;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomCreateResponse;
import co.edu.ustavillavicencio.impostor.dtos.response.RoomShowResponse;

public interface RoomService {
    RoomShowResponse findByCode(String code);
    RoomCreateResponse create(RoomCreateRequest dto);
}
