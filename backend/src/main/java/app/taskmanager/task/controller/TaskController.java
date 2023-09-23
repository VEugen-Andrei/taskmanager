package app.taskmanager.task.controller;

import app.taskmanager.task.controller.model.TaskRequest;
import app.taskmanager.task.service.TaskService;
import app.taskmanager.project.controller.model.ErrorResponse;
import app.taskmanager.project.service.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest) {
        try {
            logger.info("Received new task post request with data: {}", taskRequest);
            return ResponseEntity.ok(taskService.createTask(taskRequest));
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(ErrorResponse.CLIENT_ERROR_CODE);
            errorResponse.setErrorMessage("Data was not found");
            return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable("id") Long id) {
        logger.info("Received task delete request with id: {}", id);
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody TaskRequest taskRequest) {
        try {
            logger.info("Received task put request for id: {} and request data: {}", id, taskRequest);
            return ResponseEntity.ok(taskService.updateTask(id, taskRequest));
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(ErrorResponse.CLIENT_ERROR_CODE);
            errorResponse.setErrorMessage("Data was not found");
            return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping
    public ResponseEntity<?> showTasksByProjectId(@RequestParam("projectId") Long projectId) {
        try {
            logger.info("Received all tasks get request for project id: {}", projectId);
            return ResponseEntity.ok(taskService.showTasksByProjectId(projectId));
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(ErrorResponse.CLIENT_ERROR_CODE);
            errorResponse.setErrorMessage("Data was not found");
            return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }
    }
}
