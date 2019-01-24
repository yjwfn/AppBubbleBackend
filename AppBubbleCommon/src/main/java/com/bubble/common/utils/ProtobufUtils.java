package com.bubble.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

import java.util.Collections;

public class ProtobufUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @SuppressWarnings("unchecked")
    public static synchronized <T> T toBean(Message source, Class<T> clz){
        try {
            String jsonObject = JsonFormat
                    .printer()
                    .print(source);

            return objectMapper.readValue(jsonObject, clz);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }



    public static synchronized void merge(Object source, Message.Builder builder){
        try {
            String jsonObject = objectMapper.writeValueAsString(source);
            JsonFormat.parser()
                    .ignoringUnknownFields()
                    .merge(jsonObject, builder);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }

    }
}
