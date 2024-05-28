package com.example.dlarticle.api.service;

import com.example.dlarticle.api.dto.ArticleResponse;
import com.example.dlarticle.api.dto.UpdateArticleRequest;
import com.example.dlarticle.common.exception.ExceptionMessage;
import com.example.dlarticle.common.exception.handler.ArticleException;
import com.example.dlarticle.domain.article.Article;
import com.example.dlarticle.domain.article.constant.ArticleCategory;
import com.example.dlarticle.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Page<Article> getArticlesByCategory(ArticleCategory category, Pageable pageable) {
        return articleRepository.findAllByCategory(category, pageable);
    }

    public ArticleResponse getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> {
            log.warn("[DL WARN]: {} : {}", ExceptionMessage.ARTICLE_NOT_FOUND.getText(), articleId);
            throw new ArticleException(ExceptionMessage.ARTICLE_NOT_FOUND);
        });

        return ArticleResponse.of(article);
    }

    @Transactional
    public void updateArticle(Long articleId, UpdateArticleRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> {
            log.warn("[DL WARN]: {} : {}", ExceptionMessage.ARTICLE_NOT_FOUND.getText(), articleId);
            throw new ArticleException(ExceptionMessage.ARTICLE_NOT_FOUND);
        });

        article.updateArticle(request.title(), request.detail());
    }
}
