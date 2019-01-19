package com.bubble.common.exception.biz;

public class BizException extends Exception{

    private final int status;

    private BizException(int status, String message){
        super(message);
        this.status = status;
    }


    private BizException(int status){
        this(status, null);
    }

    public int getStatus() {
        return status;
    }



    public static BizException from(int status, String message){
        return new BizException(status, message);
    }


    public static BizException from(int status){
        return new BizException(status);
    }
}
