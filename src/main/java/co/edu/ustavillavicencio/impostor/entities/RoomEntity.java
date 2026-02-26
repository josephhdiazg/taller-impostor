package co.edu.ustavillavicencio.impostor.entities;

import co.edu.ustavillavicencio.impostor.enums.Category;
import co.edu.ustavillavicencio.impostor.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String code;
    private RoomStatus status;
    private UUID hostPlayerId;
    private Category category;
    private int impostorCount;
    private int currentRound;
    private String secretWord;
    private String winner;
}
