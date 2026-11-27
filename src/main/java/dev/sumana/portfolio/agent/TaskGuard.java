package dev.sumana.portfolio.agent;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TaskGuard {

    public String validate(String task) {
        if (task == null || task.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task must not be blank");
        }
        String cleaned = task.trim();
        if (cleaned.length() > 2000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task exceeds 2000 characters");
        }
        return cleaned;
    }
}
