package com.bubble.common.exception.biz;

public class BizException extends Exception{

    private int status;

    public BizException(int status, String message){
        super(message);
    }

    public BizException(int status, String message, Throwable cause){
        super(message, cause);
        this.status = status;
    }

}
