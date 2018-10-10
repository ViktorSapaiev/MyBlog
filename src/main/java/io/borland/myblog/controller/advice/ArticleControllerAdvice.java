package io.borland.myblog.controller.advice;

import io.borland.myblog.controller.exception.ArticleHeaderAlreadyExistException;
import io.borland.myblog.controller.exception.ArticleNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error")
public class ArticleControllerAdvice {
    @ResponseBody
    @ExceptionHandler(ArticleHeaderAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    VndErrors articleAlreadyExistsExceptionHandler(ArticleHeaderAlreadyExistException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors couldNotFindArticleExceptionHandler(ArticleNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
