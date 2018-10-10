package io.borland.myblog.controller.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super("You have to authorise this operation first. Please login into the application");
    }
}
