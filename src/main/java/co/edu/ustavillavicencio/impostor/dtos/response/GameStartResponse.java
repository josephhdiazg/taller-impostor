package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameStartResponse {
    private String status;
    private int currentRound;
}
