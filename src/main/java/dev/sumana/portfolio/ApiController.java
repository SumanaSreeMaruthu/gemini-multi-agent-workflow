package dev.sumana.portfolio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/workflows")
public class ApiController {

    public record Request(@NotBlank String task) {
    }

    private final WorkflowRecordRepository repository;
    private final GeminiClient gemini;

    public ApiController(WorkflowRecordRepository repository, GeminiClient gemini) {
        this.repository = repository;
        this.gemini = gemini;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkflowRecord run(@Valid @RequestBody Request request) {
        String plan = gemini.generate(
                "You are a planning assistant. Produce a short numbered plan for: " + request.task());
        String review = gemini.generate(
                "You are a quality reviewer. Critique this plan, identify risks, and propose improvements: " + plan);
        return repository.save(new WorkflowRecord(request.task(),
                "PLAN:\n" + plan + "\n\nREVIEW:\n" + review));
    }

    @GetMapping
    public List<WorkflowRecord> list() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public WorkflowRecord get(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }
}
