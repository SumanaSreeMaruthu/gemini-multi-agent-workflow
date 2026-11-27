package dev.sumana.portfolio;

import java.util.List;
import dev.sumana.portfolio.audit.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class WorkflowService {

    private final WorkflowRecordRepository repository;
    private final ActivityService activity;
    private final GeminiClient gemini;

    public WorkflowService(WorkflowRecordRepository repository, GeminiClient gemini, ActivityService activity) {
        this.repository = repository;
        this.activity = activity;
        this.gemini = gemini;
    }

    public List<WorkflowRecord> list() {
        return repository.findAll();
    }

    public WorkflowRecord get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
    }

    @Transactional
    public WorkflowRecord create(ApiController.Request request) {
        WorkflowRecord saved = repository.save(new WorkflowRecord(request.task(), gemini.generate("You are a quality reviewer. Critique and improve this plan: " + gemini.generate("Create a numbered plan for: " + request.task()))));
        activity.record("CREATED", "WorkflowRecord", saved.getId());
        return saved;
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found");
        }
        repository.deleteById(id);
        activity.record("DELETED", "WorkflowRecord", id);
    }

    public List<WorkflowRecord> search(String value) {
        if (value == null || value.isBlank()) {
            return repository.findAll();
        }
        return repository.findByStatusIgnoreCase(value.trim());
    }
}
