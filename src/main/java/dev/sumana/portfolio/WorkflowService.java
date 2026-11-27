package dev.sumana.portfolio;

import java.util.List;
import dev.sumana.portfolio.agent.WorkflowOrchestrator;
import dev.sumana.portfolio.agent.WorkflowResult;
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
    private final WorkflowOrchestrator orchestrator;

    public WorkflowService(WorkflowRecordRepository repository, WorkflowOrchestrator orchestrator, ActivityService activity) {
        this.repository = repository;
        this.activity = activity;
        this.orchestrator = orchestrator;
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
        WorkflowResult result = orchestrator.execute(request.task());
        WorkflowRecord saved = repository.save(new WorkflowRecord(request.task(), result.asText()));
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
