package app.taskmanager.task.controller.model;

import app.taskmanager.task.repository.Priority;
import app.taskmanager.task.repository.Status;
import com.google.gson.Gson;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Spring validation for enums
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    private static final Gson gson = new Gson();

    private Long projectId;
    @Pattern(regexp = "^.{0,50}$")
    private String title;
    @Pattern(regexp = "^.{0,200}$")
    private String description;
    private Priority priority;
    private Status status;

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
