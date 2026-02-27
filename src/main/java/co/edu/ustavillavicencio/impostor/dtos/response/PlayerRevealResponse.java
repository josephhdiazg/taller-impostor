package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerRevealResponse {
    private UUID playerId;
    private String nickname;
    private String role;
}
