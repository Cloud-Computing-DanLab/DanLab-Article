package com.example.dlarticle.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    // Article Exception
    ARTICLE_NOT_FOUND("Article not found"),

    // Comment Exception

    ;
    private final String text;
}
