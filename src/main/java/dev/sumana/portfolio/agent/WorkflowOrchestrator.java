package dev.sumana.portfolio.agent;

import org.springframework.stereotype.Service;

@Service
public class WorkflowOrchestrator {

    private final TaskGuard guard;
    private final PlanningAgent planner;
    private final ReviewAgent reviewer;

    public WorkflowOrchestrator(TaskGuard guard, PlanningAgent planner, ReviewAgent reviewer) {
        this.guard = guard;
        this.planner = planner;
        this.reviewer = reviewer;
    }

    public WorkflowResult execute(String task) {
        String validated = guard.validate(task);
        String plan = planner.plan(validated);
        String review = reviewer.review(validated, plan);
        return new WorkflowResult(plan, review);
    }
}
