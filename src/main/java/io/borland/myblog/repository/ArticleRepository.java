package io.borland.myblog.repository;

import io.borland.myblog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByAuthorId(long id);

    Article findArticleByHeader(String header);
}
