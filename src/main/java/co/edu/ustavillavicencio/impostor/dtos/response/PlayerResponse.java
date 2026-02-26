package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerResponse {
    private UUID id;
    private UUID roomId;
    private String nickname;
    private boolean alive;
}
