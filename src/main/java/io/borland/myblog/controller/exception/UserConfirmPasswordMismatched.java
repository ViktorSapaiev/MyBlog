package io.borland.myblog.controller.exception;

public class UserConfirmPasswordMismatched extends RuntimeException {
    public UserConfirmPasswordMismatched() {
        super("Passwords mismatched");
    }
}
