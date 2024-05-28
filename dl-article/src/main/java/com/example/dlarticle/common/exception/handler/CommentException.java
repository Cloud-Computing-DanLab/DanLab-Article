package com.example.dlarticle.common.exception.handler;

import com.example.dlarticle.common.exception.DanlabException;
import com.example.dlarticle.common.exception.ExceptionMessage;

public class CommentException extends DanlabException {
    public CommentException(ExceptionMessage message) {
        super(message.getText());
    }
}
