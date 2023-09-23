package app.taskmanager.project.controller.model;

import com.google.gson.Gson;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    private static final Gson gson = new Gson();

    private Long userId;
    @Pattern(regexp = "^.{1,50}$")
    private String title;

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
