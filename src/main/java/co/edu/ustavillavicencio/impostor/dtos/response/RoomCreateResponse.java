package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomCreateResponse {
    private UUID id;
    private String code;
    private String status;
    private UUID hostPlayerId;
    private String category;
    private int impostorCount;
    private int currentRound;
}

