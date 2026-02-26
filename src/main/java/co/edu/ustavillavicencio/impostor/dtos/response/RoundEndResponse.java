package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class RoundEndResponse extends RoundCloseResponse {
    private String winner;
    private String secretWord;
    private List<PlayerRevealResponse> reveal;
}
