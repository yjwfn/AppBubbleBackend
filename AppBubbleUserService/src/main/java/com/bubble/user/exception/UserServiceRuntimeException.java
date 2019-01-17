package com.bubble.user.exception;

import com.bubble.common.exception.biz.BizRuntimeException;

public class UserServiceRuntimeException extends BizRuntimeException{

    public UserServiceRuntimeException() {
    }

    public UserServiceRuntimeException(String message) {
        super(message);
    }

    public UserServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceRuntimeException(Throwable cause) {
        super(cause);
    }

    public UserServiceRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
