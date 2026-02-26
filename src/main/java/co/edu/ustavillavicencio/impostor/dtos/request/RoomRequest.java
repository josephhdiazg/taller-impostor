package co.edu.ustavillavicencio.impostor.dtos.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomRequest {
    private UUID id;
    private String hostNickname;
    private String category;
    private int impostorCount;
}
