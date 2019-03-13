package com.bubble.common.exception.biz;

public interface ServiceStatus {

    /** 默认0为成功状态  */
    int SUCCESS = 0;

    /** 资源未找到 */
    int NOT_FOUND = -1;

    /** 错误不正确  */
    int BAD_REQUEST = -2;

    /** 资源已经存在 */
    int ALREADY_EXISTS = -3;

    int UNKNOWN = -4;

    default boolean isSuccess(int code){
        return code == SUCCESS;
    }


}

