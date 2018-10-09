package io.borland.myblog.service.impl;

import io.borland.myblog.entity.Article;
import io.borland.myblog.repository.ArticleRepository;
import io.borland.myblog.repository.UserRepository;
import io.borland.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticleWithHeader(String header) {
        return articleRepository.findArticleByHeader(header);
    }

    @Override
    public List<Article> getArticlesWithUserId(long id) {
        return articleRepository.findAllByAuthorId(id);
    }

    @Override
    public Article getArticle(long id) {
        Optional<Article> optArticle = articleRepository.findById(id);
        return optArticle.orElse(null);
    }

    @Override
    public void addArticle(Article article) {
        articleRepository.saveAndFlush(article);
    }

    @Override
    public void updateArticle(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void removeArticle(Article article) {
        articleRepository.delete(article);
    }
}
