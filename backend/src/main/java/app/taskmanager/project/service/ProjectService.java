package app.taskmanager.project.service;

import app.taskmanager.project.controller.model.ProjectRequest;
import app.taskmanager.project.controller.model.ProjectResponse;
import app.taskmanager.project.repository.Project;
import app.taskmanager.project.repository.ProjectRepository;
import app.taskmanager.task.controller.model.TaskResponse;
import app.taskmanager.user.repository.User;
import app.taskmanager.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectService {

  private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  @Autowired
  public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
    this.projectRepository = projectRepository;
    this.userRepository = userRepository;
  }

  public ProjectResponse createProject(ProjectRequest projectRequest) {
    if (projectRepository.existsByTitle(projectRequest.getTitle())) {
      throw new DuplicateDataException("Title already exists");
    }

    Optional<User> optionalUser = userRepository.findById(projectRequest.getUserId());

    if (!optionalUser.isPresent()) {
      throw new EntityNotFoundException("User with ID " + projectRequest.getUserId() + " not found");
    }

    User user = optionalUser.get();

    Project project = new Project();
    project.setUser(user);
    project.setTitle(projectRequest.getTitle());
    projectRepository.save(project);

    logger.info("Successfully created new project request with data: {}", projectRequest);

    return new ProjectResponse(user.getId(), project.getTitle(), project.getId());
  }

  public void deleteProjectById(Long id) {
    projectRepository.deleteById(id);
    logger.info("Successfully deleted project with id: {}", id);
  }

  public ProjectResponse updateProjectTitle(Long id, ProjectRequest projectRequest) {
    Optional<Project> projectOptional = projectRepository.findById(id);
    if (projectOptional.isEmpty()) {
      throw new NotFoundException();
    }
    Project project = projectOptional.get();
    project.setTitle(projectRequest.getTitle());
    projectRepository.save(project);
    logger.info("Successfully updated project title with id: {}", id);
    return new ProjectResponse(project.getUser().getId() ,project.getTitle(), project.getId());
  }

  public List<ProjectResponse> showAllProjects() {
    logger.info("Received show all projects request");
    return projectRepository.findAll()
      .stream()
      .map(currentProject -> new ProjectResponse(currentProject.getUser().getId().longValue(),currentProject.getTitle(), currentProject.getId()))
      .collect(Collectors.toList());
  }

  public List<ProjectResponse> showProjectsByUserId(Long userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new NotFoundException();
    }
    User user = userOptional.get();
    logger.info("All projects for user id: {}", userId);
    return user.getProjectList().stream()
      .map(currentProject -> new ProjectResponse(currentProject.getUser().getId(),
        currentProject.getTitle(),
        currentProject.getId()))
      .collect(Collectors.toList());
  }
}
