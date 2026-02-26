package co.edu.ustavillavicencio.impostor;

import java.security.SecureRandom;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class RandomStringGenerator {
    private static final String ALPHANUMERIC_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateRandomAlphanumeric(int length) {
        if (length <= 0) {
            return "";
        }

        return IntStream.range(0, length)
                .map(_ -> SECURE_RANDOM.nextInt(ALPHANUMERIC_POOL.length()))
                .mapToObj(ALPHANUMERIC_POOL::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
