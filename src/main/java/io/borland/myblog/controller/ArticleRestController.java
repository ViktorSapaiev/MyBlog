package io.borland.myblog.controller;

import io.borland.myblog.controller.exception.ArticleHeaderAlreadyExistException;
import io.borland.myblog.controller.exception.ArticleNotFoundException;
import io.borland.myblog.entity.Article;
import io.borland.myblog.entity.UserShort;
import io.borland.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/articles")
public class ArticleRestController {
    private ArticleService articleService;


    @Autowired
    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getArticles(@RequestParam(name = "authorId", required = false) Integer authorId) {
        return (authorId != null) ? articleService.getArticlesWithUserId(authorId)
                : articleService.getArticles();
    }

    @GetMapping(value = "/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable(name = "articleId") long id) {

        Article savedArticle = articleService.getArticle(id);
        if (savedArticle == null) {
            throw new ArticleNotFoundException(id);
        }

        return new ResponseEntity<>(savedArticle, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addArticle(@Valid @RequestBody Article article, UriComponentsBuilder ucBuilder, Principal principal) {

        if (checkAuthor(principal, article.getAuthor())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        if (articleService.getArticleWithHeader(article.getHeader()) != null) {
            throw new ArticleHeaderAlreadyExistException(article.getHeader());
        }

        articleService.addArticle(article);
        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(ucBuilder.path("/api/articles/{articleId}")
                .buildAndExpand(article.getId())
                .toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{articleId}")

    public ResponseEntity<?> remove(@PathVariable(value = "articleId") long id, Principal principal) {
        Article article = articleService.getArticle(id);

        if (article == null) {
            throw new ArticleNotFoundException(id);
        }

        if (checkAuthor(principal, article.getAuthor())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        articleService.removeArticle(article);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{articleId}")
    public ResponseEntity<?> update(@RequestBody Article article, @PathVariable(value = "articleId") long id, Principal principal) {

        if (checkAuthor(principal, article.getAuthor())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Article savedArticle = articleService.getArticle(id);
        if (savedArticle == null) {
            throw new ArticleNotFoundException(id);
        }
        savedArticle.setHeader(article.getHeader());
        savedArticle.setHeader(article.getText());
        articleService.updateArticle(savedArticle);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    private boolean checkAuthor(Principal principal, UserShort author) {
        if (principal == null) {
            return true;
        }
        return !principal.getName().equals(author.getUsername());
    }
}
