package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StartResponse {
    private String status;
    private int currentRound;
}
