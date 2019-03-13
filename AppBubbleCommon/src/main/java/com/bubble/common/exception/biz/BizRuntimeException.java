package com.bubble.common.exception.biz;

import com.bubble.common.ResponseProto;

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

    public static BizRuntimeException from(ResponseProto.Response response){
        return new BizRuntimeException(response.getCode(), response.getMsg());
    }


    public static BizRuntimeException from(int status){
        return new BizRuntimeException(status);
    }
}
