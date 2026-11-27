package dev.sumana.portfolio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import dev.sumana.portfolio.agent.WorkflowOrchestrator;
import dev.sumana.portfolio.audit.ActivityService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class WorkflowServiceTest {

    @Mock
    private WorkflowRecordRepository repository;

    @Mock
    private WorkflowOrchestrator orchestrator;

    @Mock
    private ActivityService activity;

    @InjectMocks
    private WorkflowService service;

    @Test
    void listReturnsRepositoryResults() {
        when(repository.findAll()).thenReturn(List.of());
        assertEquals(0, service.list().size());
    }

    @Test
    void getThrowsWhenRecordIsMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.get(99L));
    }
}
