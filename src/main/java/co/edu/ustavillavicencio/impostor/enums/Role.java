package co.edu.ustavillavicencio.impostor.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    CIVIL("Civil"),
    IMPOSTOR("Impostor");

    @Getter
    private final String label;
}
