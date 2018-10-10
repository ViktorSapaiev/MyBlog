package io.borland.myblog.controller.exception;

public class ArticleHeaderAlreadyExistException extends RuntimeException {
    public ArticleHeaderAlreadyExistException(String header) {
        super("Article already exists for header: '" + header + "'");
    }
}
