package dev.sumana.portfolio.agent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class WorkflowOrchestratorTest {

    @Mock
    private TaskGuard guard;

    @Mock
    private PlanningAgent planner;

    @Mock
    private ReviewAgent reviewer;

    @InjectMocks
    private WorkflowOrchestrator orchestrator;

    @Test
    void executesPlannerThenReviewer() {
        when(guard.validate("Task")).thenReturn("Task");
        when(planner.plan("Task")).thenReturn("Plan");
        when(reviewer.review("Task", "Plan")).thenReturn("Review");

        WorkflowResult result = orchestrator.execute("Task");

        assertEquals("Plan", result.plan());
        assertEquals("Review", result.review());
        verify(reviewer).review("Task", "Plan");
    }

    @Test
    void taskGuardRejectsBlankInput() {
        assertThrows(ResponseStatusException.class, () -> new TaskGuard().validate("  "));
    }
}
