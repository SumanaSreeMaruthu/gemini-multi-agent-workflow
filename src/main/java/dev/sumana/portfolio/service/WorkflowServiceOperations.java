package dev.sumana.portfolio.service;

import dev.sumana.portfolio.WorkflowRecord;
import java.util.List;

public interface WorkflowServiceOperations {
    List<WorkflowRecord> findAll();
    WorkflowRecord findById(Long id);
}
