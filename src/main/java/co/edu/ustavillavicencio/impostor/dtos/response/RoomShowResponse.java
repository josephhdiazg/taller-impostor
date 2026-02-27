package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomShowResponse {
    private String status;
    private String category;
    private int currentRound;
    private List<PlayerListResponse> players;
}
