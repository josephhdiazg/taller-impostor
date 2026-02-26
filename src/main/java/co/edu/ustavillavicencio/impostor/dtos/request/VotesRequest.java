package co.edu.ustavillavicencio.impostor.dtos.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VotesRequest {
    private UUID votedId;
}
