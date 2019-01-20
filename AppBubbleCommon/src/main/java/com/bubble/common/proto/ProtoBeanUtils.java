package com.bubble.common.proto;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.MessageOrBuilder;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ProtoBeanUtils {

    public static void toBean(Descriptors.Descriptor descriptor, Message message, Object target){
        Preconditions.checkNotNull(message, "Message must not be null");
        Preconditions.checkNotNull(descriptor, "Descriptor must not be null");
        Preconditions.checkNotNull(target, "Target must not be null");


        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
        if (propertyDescriptors == null || propertyDescriptors.length <= 0) {
            return;
        }

        for(PropertyDescriptor propertyDescriptor : propertyDescriptors){
            String propertyName = propertyDescriptor.getName();
            Descriptors.FieldDescriptor fieldDescriptor = descriptor.findFieldByName(propertyName);
            if (fieldDescriptor == null) {
                continue;
            }

            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (writeMethod != null) {
                try {
                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                        writeMethod.setAccessible(true);
                    }


                    Object value = message.getField(fieldDescriptor);
                    writeMethod.invoke(target, value);
                }
                catch (Throwable ex) {
                    throw new IllegalStateException(
                            "Could not copy property '" + propertyDescriptor.getName() + "' from source to target", ex);
                }
            }
        }
    }

    public static <T extends Message.Builder> T toMessage(Descriptors.Descriptor descriptor, T messageBuilder, Object target){
        Preconditions.checkNotNull(messageBuilder, "messageBuilder must not be null");
        Preconditions.checkNotNull(descriptor, "Descriptor must not be null");
        Preconditions.checkNotNull(target, "Target must not be null");


        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
        if (propertyDescriptors == null || propertyDescriptors.length <= 0) {
            return messageBuilder;
        }

        for(PropertyDescriptor propertyDescriptor : propertyDescriptors){
            String propertyName = propertyDescriptor.getName();
            Descriptors.FieldDescriptor fieldDescriptor = descriptor.findFieldByName(propertyName);
            if (fieldDescriptor == null) {
                continue;
            }

            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {
                try {
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }

                    Object value = readMethod.invoke(target);
                    Descriptors.FieldDescriptor.JavaType type = fieldDescriptor.getJavaType();

                    switch (type){
                        case INT:
                        case LONG:
                        case FLOAT:
                        case DOUBLE:
                        case BOOLEAN:
                        case STRING:
                        case BYTE_STRING:{

                        }

                            break;
                        case ENUM:

                            break;
                        case MESSAGE:


                            break;
                    }

                    messageBuilder.setField(fieldDescriptor, value);
                }
                catch (Throwable ex) {
                    throw new IllegalStateException(
                            "Could not copy property '" + propertyDescriptor.getName() + "' from source to target", ex);
                }
            }
        }

        return null;
    }
}
