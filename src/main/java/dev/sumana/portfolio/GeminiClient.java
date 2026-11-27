package dev.sumana.portfolio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GeminiClient {

    private final ObjectMapper mapper;
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)).build();

    public GeminiClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String generate(String prompt) {
        String key = System.getenv("GEMINI_API_KEY");
        if (key == null || key.isBlank()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "GEMINI_API_KEY is required");
        }
        try {
            String body = mapper.writeValueAsString(Map.of(
                    "contents", new Object[] { Map.of(
                            "parts", new Object[] { Map.of("text", prompt) }) }));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"))
                    .header("x-goog-api-key", key)
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(40))
                    .POST(HttpRequest.BodyPublishers.ofString(body)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                        "Gemini returned HTTP " + response.statusCode());
            }
            JsonNode value = mapper.readTree(response.body()).path("candidates")
                    .path(0).path("content").path("parts").path(0).path("text");
            if (!value.isTextual()) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Empty Gemini response");
            }
            return value.asText();
        } catch (ResponseStatusException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Gemini request failed", exception);
        }
    }
}
