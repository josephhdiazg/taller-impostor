package co.edu.ustavillavicencio.impostor.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "votes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID roomId;
    private int roundNumber;
    private UUID voterId;
    private UUID votedId;
}
