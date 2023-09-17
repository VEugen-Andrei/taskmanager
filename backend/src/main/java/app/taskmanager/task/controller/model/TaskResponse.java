package app.taskmanager.task.controller.model;

import app.taskmanager.task.repository.Priority;
import app.taskmanager.task.repository.Status;
import com.google.gson.Gson;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse extends TaskRequest {

    private static final Gson gson = new Gson();

    private Long id;

    public TaskResponse(Long projectId,
                        @NotBlank @Pattern(regexp = "^.{1,50}$") String title,
                        @Pattern(regexp = "^.{1,200}$") String description,
                        @NotNull Priority priority,
                        @NotNull Status status,
                        Long id) {
        super(projectId, title, description, priority, status);
        this.id = id;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
