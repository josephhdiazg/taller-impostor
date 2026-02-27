package co.edu.ustavillavicencio.impostor.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerCreateRequest {
    @NotBlank
    private String nickname;
}
