package co.edu.ustavillavicencio.impostor.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteCreateRequest {
    @NotBlank
    private UUID votedId;
}
