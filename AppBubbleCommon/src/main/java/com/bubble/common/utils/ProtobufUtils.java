package com.bubble.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

public class ProtobufUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();

    }

    @SuppressWarnings("unchecked")
    public static synchronized <T> T toBean(Message source, Class<T> clz){
        try {
            String jsonObject = JsonFormat.printer().print(source);
            return objectMapper.readValue(jsonObject, clz);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }



    public static synchronized void merge(Object object, Message.Builder builder){
        try {
            String jsonObject = objectMapper.writeValueAsString(object);
            JsonFormat.parser().merge(jsonObject, builder);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }

    }
}
