package co.edu.ustavillavicencio.impostor.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomCreateRequest {
    @NotEmpty
    private String hostNickname;
    @NotEmpty
    private String category;
    @NotNull
    private int impostorCount;
}
