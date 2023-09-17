package app.taskmanager.project.controller.model;

import lombok.Data;

@Data
public class ErrorResponse {

    public static final String CLIENT_ERROR_CODE = "001";
    public static final String INTERNAL_ERROR_CODE = "000";
    private String errorMessage;
    private String errorCode;

}
