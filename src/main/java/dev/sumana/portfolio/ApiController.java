package dev.sumana.portfolio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workflows")
public class ApiController {

    public record Request(@NotBlank String task) {}

    private final WorkflowService service;

    public ApiController(WorkflowService service) {
        this.service = service;
    }

    @GetMapping
    public List<WorkflowRecord> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public WorkflowRecord get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkflowRecord create(@Valid @RequestBody Request request) {
        return service.create(request);
    }
}
