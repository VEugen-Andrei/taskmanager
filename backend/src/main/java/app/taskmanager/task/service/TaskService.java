package app.taskmanager.task.service;

import app.taskmanager.project.repository.Project;
import app.taskmanager.project.repository.ProjectRepository;
import app.taskmanager.project.service.NotFoundException;
import app.taskmanager.task.controller.model.TaskRequest;
import app.taskmanager.task.controller.model.TaskResponse;
import app.taskmanager.task.repository.Task;
import app.taskmanager.task.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponse createTask(TaskRequest taskRequest) {
        if (!projectRepository.existsById(taskRequest.getProjectId())) {
            throw new NotFoundException();
        }

        Task task = new Task();
        Project project = new Project();

        project.setId(taskRequest.getProjectId());
        task.setProject(project);
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());
        task.setStatus(taskRequest.getStatus());
        taskRepository.save(task);
        logger.info("Received new task request with data: {}", taskRequest);
        return new TaskResponse(project.getId(), task.getTitle(), task.getDescription(), task.getPriority(), task.getStatus(), task.getId());
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
        logger.info("Successfully deleted task with id: {}", id);
    }

    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            throw new NotFoundException();
        }
        Task task = taskOptional.get();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());
        task.setStatus(taskRequest.getStatus());
        taskRepository.save(task);
        logger.info("Successfully updated task with id: {}", id);
        return new TaskResponse(task.getProject().getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getId());
    }

    public List<TaskResponse> showTasksByProjectId(Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            throw new NotFoundException();
        }
        Project project = projectOptional.get();
        logger.info("All tasks for project id: {}", projectId);
        return project.getTaskList().stream()
                .map(currentTask -> new TaskResponse(currentTask.getProject().getId(),
                        currentTask.getTitle(),
                        currentTask.getDescription(),
                        currentTask.getPriority(),
                        currentTask.getStatus(),
                        currentTask.getId()))
                .collect(Collectors.toList());
    }
}
