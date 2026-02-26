package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class RoundContinueResponse extends RoundCloseResponse {
    private PlayerExpelledResponse expelled;
    private int nextRound;
    private int aliveCount;
}
