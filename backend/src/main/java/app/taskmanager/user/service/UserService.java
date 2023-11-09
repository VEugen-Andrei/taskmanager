package app.taskmanager.user.service;

import app.taskmanager.project.repository.ProjectRepository;
import app.taskmanager.user.controller.model.MapEntity;
import app.taskmanager.user.controller.model.UserDto;
import app.taskmanager.user.repository.User;
import app.taskmanager.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnableCaching
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Cacheable("users")
  public List<UserDto> getUsers() {
    return userRepository.findAll().stream().map(user -> {
      UserDto userDto = new UserDto();
      MapEntity.mapUserToUserDto(user, userDto);
      return userDto;
    }).toList();
  }

  @Cacheable("user")
  public UserDto getUserById(Long id) {
    Optional<User> foundUser = userRepository.findById(id);
    if (foundUser.isPresent()) {
      UserDto userDto = new UserDto();
      MapEntity.mapUserToUserDto(foundUser.get(), userDto);
      return userDto;
    }
    return null;
  }

  @Caching(evict = {
    @CacheEvict(value = "users", allEntries = true),
    @CacheEvict(value = "user", key = "#user.id")})
  public UserDto addUser(User user) {
    userRepository.save(user);
    UserDto userDto = new UserDto();
    MapEntity.mapUserToUserDto(user, userDto);
    return userDto;
  }

  @Caching(evict = {
    @CacheEvict(value = "users", allEntries = true),
    @CacheEvict(value = "user", key = "#id")})
  public UserDto updateUser(Long id, User user) {
    Optional<User> userFound = userRepository.findById(id);
    if (userFound.isPresent()) {
      User userToUpdate = userFound.get();
      MapEntity.mapUserProperties(userToUpdate, user);
      userRepository.save(userToUpdate);

      UserDto userDto = new UserDto();
      MapEntity.mapUserToUserDto(userToUpdate, userDto);

      return userDto;
    }
    return null;
  }

  @Caching(evict = {
    @CacheEvict(value = "users", allEntries = true),
    @CacheEvict(value = "user", key = "#id")})
  public UserDto deleteUser(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      userRepository.deleteById(id);
      UserDto userDto = new UserDto();
      MapEntity.mapUserToUserDto(user.get(), userDto);
      return userDto;
    }
    return null;
  }
}
