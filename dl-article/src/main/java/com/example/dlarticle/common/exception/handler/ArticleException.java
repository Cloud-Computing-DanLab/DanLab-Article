package com.example.dlarticle.common.exception.handler;

import com.example.dlarticle.common.exception.DanlabException;
import com.example.dlarticle.common.exception.ExceptionMessage;

public class ArticleException extends DanlabException {
    public ArticleException(ExceptionMessage message) {
        super(message.getText());
    }
}
