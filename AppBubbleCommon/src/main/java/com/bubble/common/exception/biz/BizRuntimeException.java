package com.bubble.common.exception.biz;

public class BizRuntimeException extends RuntimeException{

    private final int status;

    private BizRuntimeException(int status, String message){
        super(message);
        this.status = status;
    }


    private BizRuntimeException(int status){
        this(status, null);
    }

    public int getStatus() {
        return status;
    }



    public static BizRuntimeException from(int status, String message){
        return new BizRuntimeException(status, message);
    }


    public static BizRuntimeException from(int status){
        return new BizRuntimeException(status);
    }
}
