package io.borland.myblog.controller.exception;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String email) {
        super("Email already exists: '" + email + "'");
    }
}
