package com.example.dlarticle.api.dto;

import com.example.dlarticle.domain.article.Article;
import com.example.dlarticle.domain.article.constant.ArticleCategory;

public record ArticleResponse(
        Long id,
        Long memberId,
        String title,
        String detail,
        ArticleCategory category
) {
    public static ArticleResponse of(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getMemberId(),
                article.getTitle(),
                article.getDetail(),
                article.getCategory()
        );
    }
}

