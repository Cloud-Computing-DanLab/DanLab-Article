package com.example.dlarticle.api.controller;

import com.example.dlarticle.api.dto.ArticleResponse;
import com.example.dlarticle.api.dto.UpdateArticleRequest;
import com.example.dlarticle.api.service.ArticleService;
import com.example.dlarticle.common.response.JsonResult;
import com.example.dlarticle.domain.article.Article;
import com.example.dlarticle.domain.article.constant.ArticleCategory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    // 글 목록 조회 (전체 또는 특정 카테고리)
    @GetMapping("/")
    public JsonResult<?> getArticles(
            @RequestParam(required = false) ArticleCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Article> articlePage;

        if (category != null) {
            articlePage = articleService.getArticlesByCategory(category, pageable);
        } else {
            articlePage = articleService.getAllArticles(pageable);
        }

        return JsonResult.successOf(articlePage);
    }

    // 특정 글 상세 조회
    @GetMapping("/{articleId}")
    public JsonResult<?> getArticle(@PathVariable Long articleId) {
        ArticleResponse article = articleService.getArticle(articleId);
        return JsonResult.successOf(article);
    }

    // 특정 글 수정
    @PostMapping("/{articleId}/update")
    public JsonResult<?> updateArticle(@PathVariable Long articleId,
                                       @Valid @RequestBody UpdateArticleRequest request) {
        articleService.updateArticle(articleId, request);
        return JsonResult.successOf("글 수정이 완료되었습니다.");
    }
}
