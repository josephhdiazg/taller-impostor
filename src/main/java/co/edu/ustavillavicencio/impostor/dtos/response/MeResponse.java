package co.edu.ustavillavicencio.impostor.dtos.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeResponse {
    private String role;
    private String word;
}
