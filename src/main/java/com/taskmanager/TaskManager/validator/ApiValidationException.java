package com.taskmanager.TaskManager.validator;

import org.springframework.validation.FieldError;

import java.util.List;

public class ApiValidationException extends RuntimeException {

    private final List<FieldError> errorList;

    public ApiValidationException(List<FieldError> errorList) {
        super("Api Validation Exception");
        this.errorList = errorList;
    }

    public ApiValidationException(String message, List<FieldError> errorList) {
        super(message);

        this.errorList = errorList;
    }

    public List<FieldError> getErrorList() {
        return errorList;
    }
}
