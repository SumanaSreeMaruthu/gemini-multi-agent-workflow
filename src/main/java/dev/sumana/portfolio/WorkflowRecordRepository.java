package dev.sumana.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowRecordRepository extends JpaRepository<WorkflowRecord, Long> {
    java.util.List<WorkflowRecord> findByStatusIgnoreCase(String value);
    java.util.List<WorkflowRecord> findByStatusIgnoreCase(String value);
}
