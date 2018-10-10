package io.borland.myblog.controller.advice;

import io.borland.myblog.controller.exception.UnauthorizedAccessException;
import io.borland.myblog.controller.exception.UserAlreadyExist;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserControllerAdvice {
    @ResponseBody
    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    VndErrors unauthorizedExceptionHeandler(UnauthorizedAccessException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    VndErrors userExistExceptionHeandler(UserAlreadyExist ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
