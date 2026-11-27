package dev.sumana.portfolio.agent;

public record WorkflowResult(String plan, String review) {

    public String asText() {
        return "PLAN:\n" + plan + "\n\nREVIEW:\n" + review;
    }
}
