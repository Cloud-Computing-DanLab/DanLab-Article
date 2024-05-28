package com.example.dlarticle.api.service;

import com.example.dlarticle.IntegrationHelper;
import com.example.dlarticle.api.dto.ArticleResponse;
import com.example.dlarticle.api.dto.UpdateArticleRequest;
import com.example.dlarticle.common.exception.ExceptionMessage;
import com.example.dlarticle.common.exception.handler.ArticleException;
import com.example.dlarticle.domain.article.Article;
import com.example.dlarticle.domain.article.constant.ArticleCategory;
import com.example.dlarticle.domain.article.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.example.dlarticle.IntegrationHelper.NON_ASCII;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings(NON_ASCII)
class ArticleIntegrationTest extends IntegrationHelper {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleRepository.saveAll(List.of(
                Article.builder().memberId(1L).title("Article 1").detail("Detail 1").category(ArticleCategory.DIARY).build(),
                Article.builder().memberId(1L).title("Article 2").detail("Detail 2").category(ArticleCategory.REVIEW).build(),
                Article.builder().memberId(1L).title("Article 3").detail("Detail 3").category(ArticleCategory.QNA).build()
        ));
    }

    @Test
    void 전체_글_리스트_조회() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));

        // when
        Page<Article> articlePage = articleService.getAllArticles(pageable);

        List<Article> articles = articlePage.getContent();

        // then
        assertNotNull(articlePage);
        assertEquals(3, articlePage.getTotalElements());

        assertTrue(articles.get(0).getId() > articles.get(1).getId());
        assertTrue(articles.get(1).getId() > articles.get(2).getId());
    }

    @Test
    void 특정_카테고리_글_리스트_조회() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        ArticleCategory category = ArticleCategory.DIARY;

        // when
        Page<Article> articlePage = articleService.getArticlesByCategory(category, pageable);

        // then
        assertNotNull(articlePage);
        assertEquals(1, articlePage.getTotalElements());
        assertEquals(category, articlePage.getContent().get(0).getCategory());
    }

    @Test
    void 특정_글_상세_조회() {
        // given
        Article article = articleRepository.findByTitle("Article 1").orElseThrow();

        // when
        ArticleResponse articleResponse = articleService.getArticle(article.getId());

        // then
        assertNotNull(articleResponse);
        assertEquals("Article 1", articleResponse.title());
    }

    @Test
    void 존재하지_않는_글_상세_조회시_예외발생() {
        // given
        Long nonExistentArticleId = 999L;

        // when & then
        ArticleException exception = assertThrows(ArticleException.class, () -> articleService.getArticle(nonExistentArticleId));
        assertEquals(ExceptionMessage.ARTICLE_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void 글_수정() {
        // given
        Article article = articleRepository.findByTitle("Article 1").orElseThrow();
        UpdateArticleRequest request = new UpdateArticleRequest("Updated Article 1", "Updated Detail 1");

        // when
        articleService.updateArticle(article.getId(), request);

        // then
        Article updatedArticle = articleRepository.findById(article.getId()).orElseThrow();
        assertEquals("Updated Article 1", updatedArticle.getTitle());
        assertEquals("Updated Detail 1", updatedArticle.getDetail());
    }
}
