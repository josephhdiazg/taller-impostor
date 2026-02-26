package co.edu.ustavillavicencio.impostor.dtos.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerRequest {
    private UUID id;
    private String nickname;
}
