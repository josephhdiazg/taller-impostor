package co.edu.ustavillavicencio.impostor.services;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
public class StaticResourceService {
    @Autowired
    private ResourceLoader resourceLoader;

    public String readStaticFileContent(String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/" + filename);
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public List<String> readStaticFileLines(String filename) throws IOException {
        String content = readStaticFileContent(filename);
        return Arrays.asList(content.split("\n"));
    }
}
