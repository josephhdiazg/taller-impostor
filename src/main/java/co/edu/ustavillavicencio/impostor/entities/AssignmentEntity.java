package co.edu.ustavillavicencio.impostor.entities;

import co.edu.ustavillavicencio.impostor.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "assignments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID roomId;
    private UUID playerId;
    private Role role;
    private String word;
}
