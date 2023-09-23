package app.taskmanager.project.controller;

import app.taskmanager.project.controller.model.ErrorResponse;
import app.taskmanager.project.controller.model.ProjectRequest;
import app.taskmanager.project.service.DuplicateDataException;
import app.taskmanager.project.service.NotFoundException;
import app.taskmanager.project.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Validated - to implement
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest projectRequest) {
        try {
            logger.info("Received new project post request with data: {}", projectRequest);
            return ResponseEntity.ok(projectService.createProject(projectRequest));
        } catch (DuplicateDataException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(ErrorResponse.CLIENT_ERROR_CODE);
            errorResponse.setErrorMessage("Duplicate data was found on request");
            return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable("id") Long id) {
        logger.info("Received project delete request with id: {}", id);
        projectService.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateProjectTitle(@PathVariable("id") Long id, @RequestBody ProjectRequest projectRequest) {
        try {
            logger.info("Received project title patch request for id: {} and request data: {}", id, projectRequest);
            return ResponseEntity.ok(projectService.updateProjectTitle(id, projectRequest));
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(ErrorResponse.CLIENT_ERROR_CODE);
            errorResponse.setErrorMessage("Data was not found");
            return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> showAllProjects() {
        logger.info("Received all projects get request");
        return ResponseEntity.ok(projectService.showAllProjects());
    }

  @GetMapping()
  public ResponseEntity<?> showProjectsByUserId(@RequestParam("userId") Long userId) {
    try {
      logger.info("Received all projects get request for user id: {}", userId);
      return ResponseEntity.ok(projectService.showProjectsByUserId(userId));
    } catch (NotFoundException e) {
      ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setErrorCode(ErrorResponse.CLIENT_ERROR_CODE);
      errorResponse.setErrorMessage("Data was not found");
      return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }
  }
}
