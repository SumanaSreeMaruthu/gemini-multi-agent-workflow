package dev.sumana.portfolio;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class WorkflowService {

    private final WorkflowRecordRepository repository;
    private final GeminiClient gemini;

    public WorkflowService(WorkflowRecordRepository repository, GeminiClient gemini) {
        this.repository = repository;
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
        return repository.save(new WorkflowRecord(request.task(), gemini.generate("You are a quality reviewer. Critique and improve this plan: " + gemini.generate("Create a numbered plan for: " + request.task()))));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found");
        }
        repository.deleteById(id);
    }

}
