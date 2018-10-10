package io.borland.myblog.controller.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(long id) {
        super("could not find article with id: '" + id + "'");
    }
}
