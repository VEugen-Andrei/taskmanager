package app.taskmanager.project.service;

import app.taskmanager.project.controller.model.ProjectRequest;
import app.taskmanager.project.controller.model.ProjectResponse;
import app.taskmanager.project.repository.Project;
import app.taskmanager.project.repository.ProjectRepository;
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

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse createProject(ProjectRequest projectRequest) {
        if (projectRepository.existsByTitle(projectRequest.getTitle())) {
            throw new DuplicateDataException("Title already exists");
        }
        Project project = new Project();
        project.setTitle(projectRequest.getTitle());
        projectRepository.save(project);
        logger.info("Successfully created new project request with data: {}", projectRequest);
        return new ProjectResponse(project.getTitle(), project.getId());
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
        return new ProjectResponse(project.getTitle(), project.getId());
    }

    public List<ProjectResponse> showAllProjects() {
        logger.info("Received show all projects request");
        return projectRepository.findAll()
                .stream()
                .map(currentProject -> new ProjectResponse(currentProject.getTitle(), currentProject.getId()))
                .collect(Collectors.toList());
    }
}
