package io.borland.myblog.service;

import io.borland.myblog.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getArticles();

    List<Article> getArticlesWithUserId(long id);

    Article getArticle(long id);

    Article getArticleWithHeader(String header);

    void addArticle(Article article);

    void updateArticle(Article article);

    void removeArticle(Article article);
}
