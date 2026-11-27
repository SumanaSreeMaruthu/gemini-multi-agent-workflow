package dev.sumana.portfolio.service.impl;

import dev.sumana.portfolio.WorkflowRecord;
import dev.sumana.portfolio.WorkflowService;
import dev.sumana.portfolio.service.WorkflowServiceOperations;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements WorkflowServiceOperations {

    private final WorkflowService delegate;

    public WorkflowServiceImpl(WorkflowService delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<WorkflowRecord> findAll() {
        return delegate.list();
    }

    @Override
    public WorkflowRecord findById(Long id) {
        return delegate.get(id);
    }
}
