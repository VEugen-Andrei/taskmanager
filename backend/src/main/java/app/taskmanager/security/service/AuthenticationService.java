package app.taskmanager.security.service;

import app.taskmanager.security.model.AuthenticationResponse;
import app.taskmanager.security.model.LoginRequest;
import app.taskmanager.security.model.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

  AuthenticationResponse register(RegisterRequest request);

  AuthenticationResponse authenticate(LoginRequest request);
}
