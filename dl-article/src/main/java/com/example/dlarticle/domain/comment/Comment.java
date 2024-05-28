package com.example.dlarticle.domain.comment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Getter
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "ARTICLE_ID", nullable = false)
    private Long articleId;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @Builder
    public Comment(Long memberId, Long articleId, String content) {
        this.memberId = memberId;
        this.articleId = articleId;
        this.content = content;
    }
}

