package app.taskmanager.project.controller.model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse extends ProjectRequest {

  private static final Gson gson = new Gson();

  private Long id;

  public ProjectResponse(Long userId, String title, Long id) {
    super(userId, title);
    this.id = id;
  }

  @Override
  public String toString() {
    return gson.toJson(this);
  }
}
