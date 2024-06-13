package com.example.dlarticle.api.dto;

import com.example.dlarticle.domain.article.constant.ArticleCategory;

public record CreateArticleRequest(
        Long memberId,

        String title,

        String detail,

        ArticleCategory category
) {
}
