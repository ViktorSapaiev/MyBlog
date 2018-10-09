package io.borland.myblog.controller;

import io.borland.myblog.entity.Article;
import io.borland.myblog.exception.ArticleHeaderAlreadyExistException;
import io.borland.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/api/articles")
public class ArticleRestController {
    private ArticleService articleService;


    // TODO: Return appropriate HTTP status code from each method
    // For happy path and for path with an errors

    @Autowired
    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getArticles(@RequestParam(name = "authorId", required = false) Integer authorId) {
        // TODO: Validate authorId (Optional)
        return (authorId != null) ? articleService.getArticlesWithUserId(authorId)
                : articleService.getArticles();
    }

    @GetMapping(value = "/{articleId}")
    public Article getArticle(@PathVariable(name = "articleId") long id) {
        // return status 404 if article doesn't exist
        return articleService.getArticle(id);
    }

    @PostMapping
    public ResponseEntity<?> addArticle(@RequestBody Article article, UriComponentsBuilder ucBuilder) {
        if (articleService.getArticleWithHeader(article.getHeader()) != null) {
            throw new ArticleHeaderAlreadyExistException(article.getHeader());
        }
        articleService.addArticle(article);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/articles/{articleId}").buildAndExpand(article.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{articleId}")
    public void remove(@PathVariable(value = "articleId") long id) {
        Article article = articleService.getArticle(id);
        articleService.removeArticle(article);
    }

    @PutMapping(value = "{articleId}")
    public void update(@RequestBody Article article, @PathVariable(value = "articleId") long id) {
        Article savedArticle = articleService.getArticle(id);
        if (savedArticle != null) {
            articleService.updateArticle(article);
        }
    }
}
