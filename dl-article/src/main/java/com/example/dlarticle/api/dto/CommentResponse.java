package com.example.dlarticle.api.dto;

import com.example.dlarticle.domain.article.Article;
import com.example.dlarticle.domain.article.constant.ArticleCategory;
import com.example.dlarticle.domain.comment.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        Long memberId,
        Long articleId,
        String content,
        LocalDateTime createdDateTime
) {
    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getMemberId(),
                comment.getArticleId(),
                comment.getContent(),
                comment.getCreatedDateTime()
        );
    }
}

