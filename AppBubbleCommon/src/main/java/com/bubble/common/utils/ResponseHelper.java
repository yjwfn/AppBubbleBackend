package com.bubble.common.utils;

import com.bubble.common.ResponseProto;
import com.bubble.common.exception.biz.BizRuntimeException;
import com.bubble.common.exception.biz.ServiceStatus;
import com.google.common.base.Strings;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

public final class ResponseHelper {

    /**
     * 获取data数据
     * @param response
     * @param data
     * @param <T>
     * @return
     */
    public static <T extends Message> T unpack(ResponseProto.Response response, Class<T> data){
        try {
            return response.getData().unpack(data);
        } catch (InvalidProtocolBufferException e) {
            throw BizRuntimeException.from(ServiceStatus.UNKNOWN, "Failed to unpack data with type " + data);
        }
    }

    /**
     * 检查是否成功
     * @param response
     * @return
     */
    public static boolean isSuccessful(ResponseProto.Response response){
        return response.getCode() == ResponseProto.Code.SUCCESS_VALUE;
    }

    /**
     * 检查{@link ResponseProto.Response}是否成功，不成功则
     * 抛出异常。
     * @param response
     */
    public static void checkResponse(ResponseProto.Response response){
        if(!ResponseHelper.isSuccessful(response)){
            throw BizRuntimeException.from(response);
        }
    }

    /**
     * 构造一个成功的{@link ResponseProto.Response}实例
     * @param message
     * @return
     */
    public static ResponseProto.Response ok(Message message){
        return ResponseProto.Response.newBuilder()
                .setCode(ResponseProto.Code.SUCCESS_VALUE)
                .setData(Any.pack(message))
                .setMsg("ok")
                .build();
    }


    /**
     * 创建一个错误的{@link ResponseProto.Response}实例
     * @param code  错误码
     * @param msg 错误消息
     * @param errorBody 错误实体
     * @return
     */
    public static ResponseProto.Response error(int code, String msg, Message errorBody){
        ResponseProto.Response.Builder builder = ResponseProto.Response.newBuilder()
                .setCode(code);

        builder.setMsg(Strings.isNullOrEmpty(msg) ? "error" : msg);
        if (errorBody != null) {
            builder.setData(Any.pack(errorBody));
        }

        return builder.build();
    }

    public static ResponseProto.Response error(int code, String msg){
        return error(code, msg, null);
    }
}
