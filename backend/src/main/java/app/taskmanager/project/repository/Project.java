package app.taskmanager.project.repository;

import app.taskmanager.task.repository.Task;
import com.google.gson.Gson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private static final Gson gson = new Gson();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "project")
    @Cascade(CascadeType.ALL)
    List<Task> taskList;

    public Project(String title, List<Task> taskList) {
        this.title = title;
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
