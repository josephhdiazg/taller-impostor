package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerCreateResponse {
    private UUID id;
    private UUID roomId;
    private String nickname;
    private boolean alive;
}
