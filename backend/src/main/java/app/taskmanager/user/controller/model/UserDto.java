package app.taskmanager.user.controller.model;

import app.taskmanager.project.repository.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

  private Long id;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  //private List<Project> projectList;

}
