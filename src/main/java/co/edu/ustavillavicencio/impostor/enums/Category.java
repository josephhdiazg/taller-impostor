package co.edu.ustavillavicencio.impostor.enums;

import co.edu.ustavillavicencio.impostor.services.StaticResourceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public enum Category {
    TECHNOLOGY("Tecnologia", "tecnologia.txt"),
    FOOD("Comida", "comida.txt"),
    PLACES("Lugares", "lugares.txt"),
    OBJECTS("Objetos", "objetos.txt"),
    ANIMALS("Animales", "animales.txt");

    @Getter
    private final String label;
    private final String filename;

    public static Category from(String value) {
        for (Category c : Category.values()) {
            if (c.label.equalsIgnoreCase(value)) {
                return c;
            }
        }

        throw new IllegalArgumentException(value + " is not a valid category");
    }

    public List<String> getWordList(StaticResourceService staticResourceService) {
        try {
            return staticResourceService.readStaticFileLines(this.filename);
        } catch (IOException e) {
            log.log(Level.WARNING, "Error reading file " + this.filename, e);
            return new ArrayList<>();
        }
    }
}
