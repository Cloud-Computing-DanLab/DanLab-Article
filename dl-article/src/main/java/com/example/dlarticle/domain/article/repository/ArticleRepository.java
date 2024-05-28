package com.example.dlarticle.domain.article.repository;

import com.example.dlarticle.domain.article.Article;
import com.example.dlarticle.domain.article.constant.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByCategory(ArticleCategory category, Pageable pageable);  // ArticleCategory 타입 사용

    Optional<Article> findByTitle(String title);
}
