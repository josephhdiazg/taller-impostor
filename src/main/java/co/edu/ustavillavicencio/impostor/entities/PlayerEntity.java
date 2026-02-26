package co.edu.ustavillavicencio.impostor.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "players")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID roomId;
    private String nickname;
    private boolean alive = true;
}
