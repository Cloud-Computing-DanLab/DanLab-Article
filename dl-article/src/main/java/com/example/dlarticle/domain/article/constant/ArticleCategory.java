package com.example.dlarticle.domain.article.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum ArticleCategory {
    DIARY("연구실 일상"),
    REVIEW("프로젝트 참여 및 기술 적용 후기"),
    QNA("QNA")

    ;
    private final String category;
}
