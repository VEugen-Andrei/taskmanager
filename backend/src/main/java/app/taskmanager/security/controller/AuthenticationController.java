package app.taskmanager.security.controller;

import app.taskmanager.security.model.AuthenticationResponse;
import app.taskmanager.security.model.LoginRequest;
import app.taskmanager.security.model.RegisterRequest;
import app.taskmanager.security.service.AuthenticationService;
import app.taskmanager.user.service.ExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @Autowired
  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
    try {
      return ResponseEntity.ok(authenticationService.register(registerRequest));
    } catch (Exception e) {
      return new ResponseEntity<>(ExceptionHelper.extractException(e.getCause().toString()), HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
  }


  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
  }
}
