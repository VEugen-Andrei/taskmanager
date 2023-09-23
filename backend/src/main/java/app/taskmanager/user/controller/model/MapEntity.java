package app.taskmanager.user.controller.model;

import app.taskmanager.user.repository.User;

public class MapEntity {

  public static void mapUserProperties(User initialUser, User user) {
    initialUser.setFirstName(user.getFirstName());
    initialUser.setLastName(user.getLastName());
    initialUser.setPassword(user.getPassword());
    initialUser.setEmail(user.getEmail());
    //initialUser.setProjectList(user.getProjectList());
  }

  public static void mapUserToUserDto(User user, UserDto userDTO) {
    userDTO.setId(user.getId());
    userDTO.setFirstName(user.getFirstName());
    userDTO.setLastName(user.getLastName());
    userDTO.setPassword(user.getPassword());
    userDTO.setEmail(user.getEmail());
    //userDTO.setProjects(user.getProjectList());
  }
}
