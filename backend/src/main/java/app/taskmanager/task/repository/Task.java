package app.taskmanager.task.repository;

import app.taskmanager.project.repository.Project;
import com.google.gson.Gson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private static final Gson gson = new Gson();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;

    public Task(String title, String description, Priority priority, Status status, Project project) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.project = project;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
