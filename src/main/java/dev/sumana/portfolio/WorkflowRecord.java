package dev.sumana.portfolio;

import jakarta.persistence.*;

@Entity
public class WorkflowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4000)
    private String task;

    @Column(length = 16000)
    private String result;

    private String status;

    protected WorkflowRecord() {
    }

    public WorkflowRecord(String task, String result) {
        this.task = task;
        this.result = result;
        this.status = "COMPLETED";
    }

    public Long getId() { return id; }
    public String getTask() { return task; }
    public String getResult() { return result; }
    public String getStatus() { return status; }
}
