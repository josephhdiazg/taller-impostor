package co.edu.ustavillavicencio.impostor.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoomStatus {
    /**
     * sala abierta, se unen jugadores
     */
    LOBBY("Sala de espera"),
    /**
     * juego iniciado (rondas de votación)
     */
    IN_GAME("En juego"),
    /**
     * juego terminado (resultado final)
     */
    FINISHED("Juego terminado");

    @Getter
    private final String label;
}
