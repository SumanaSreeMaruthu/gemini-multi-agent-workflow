package dev.sumana.portfolio.agent;

import dev.sumana.portfolio.GeminiClient;
import org.springframework.stereotype.Component;

@Component
public class PlanningAgent {

    private final GeminiClient gemini;

    public PlanningAgent(GeminiClient gemini) {
        this.gemini = gemini;
    }

    public String plan(String task) {
        return gemini.generate("Role: planning agent. Create a numbered, actionable plan for the "
                + "following task. State assumptions and avoid claiming actions were executed.\nTask:\n"
                + task);
    }
}
