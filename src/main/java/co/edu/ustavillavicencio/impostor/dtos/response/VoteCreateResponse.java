package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteCreateResponse {
    private String message;
    private int round;
}
