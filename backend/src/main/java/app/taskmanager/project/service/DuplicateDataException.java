package app.taskmanager.project.service;

public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException() {
    }

    public DuplicateDataException(String message) {
        super(message);
    }
}
