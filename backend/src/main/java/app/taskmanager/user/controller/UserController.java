package app.taskmanager.user.controller;

import app.taskmanager.user.controller.model.UserDto;
import app.taskmanager.user.repository.User;
import app.taskmanager.user.service.ExceptionHelper;
import app.taskmanager.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public ResponseEntity<?> getUsers() {
    List<UserDto> users = userService.getUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
    UserDto userDto = userService.getUserById(id);
    if (userDto != null) {
      return ResponseEntity.ok(userDto);
    } else {
      return new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }
  }

  @PostMapping
  public ResponseEntity<?> addUser(@RequestBody @Validated User user) throws Exception {
    try {
      UserDto userDto = userService.addUser(user);
      return ResponseEntity.ok(userDto);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(ExceptionHelper.extractException(e.getCause().toString()), HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) throws Exception {
    try {
      UserDto userDto = userService.updateUser(id, user);
      if (userDto != null) {
        return ResponseEntity.ok(userDto);
      } else {
        return new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      return new ResponseEntity<>(ExceptionHelper.extractException(e.getCause().toString()), HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    UserDto userDto = userService.deleteUser(id);
    if (userDto != null) {
      return ResponseEntity.ok(userDto);
    } else {
      return new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }
  }

}
