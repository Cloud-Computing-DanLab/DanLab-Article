package com.example.dlarticle.domain.article;

import com.example.dlarticle.domain.article.constant.ArticleCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private Long id;

    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Lob
    @Column(name = "DETAIL", nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    @ColumnDefault(value = "'DIARY'")
    private ArticleCategory category;

    @Builder
    public Article(Long memberId, String title, String detail, ArticleCategory category) {
        this.memberId = memberId;
        this.title = title;
        this.detail = detail;
        this.category = category;
    }
}
