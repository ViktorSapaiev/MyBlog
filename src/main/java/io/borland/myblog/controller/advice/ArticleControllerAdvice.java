package io.borland.myblog.controller.advice;

import io.borland.myblog.exception.ArticleHeaderAlreadyExistException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error")
public class ArticleControllerAdvice {
    @ResponseBody
    @ExceptionHandler(ArticleHeaderAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    VndErrors bookIsbnAlreadyExistsExceptionHandler(ArticleHeaderAlreadyExistException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
