package dev.sumana.portfolio.agent;

import dev.sumana.portfolio.GeminiClient;
import org.springframework.stereotype.Component;

@Component
public class ReviewAgent {

    private final GeminiClient gemini;

    public ReviewAgent(GeminiClient gemini) {
        this.gemini = gemini;
    }

    public String review(String task, String plan) {
        return gemini.generate("Role: independent reviewer. Evaluate the proposed plan for "
                + "completeness, feasibility, risks, and missing steps. "
                + "Do not claim the plan was executed.\nOriginal task:\n"
                + task + "\nProposed plan:\n" + plan);
    }
}
