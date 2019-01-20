package com.bubble.common.proto;

import com.google.common.base.Preconditions;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

public class ProtoBeanUtils {

    @SuppressWarnings("unchecked")
    public static void toBean(Message message, Object target){
        Preconditions.checkNotNull(message, "Message must not be null");
        Preconditions.checkNotNull(target, "Target must not be null");


        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
        if (propertyDescriptors == null || propertyDescriptors.length <= 0) {
            return;
        }

        Descriptors.Descriptor descriptor = message.getDescriptorForType();
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




                    Class<?> clz = propertyDescriptor.getPropertyType();
                    if(!clz.isPrimitive()){
                        Object value = message.getField(fieldDescriptor);
                        if(!fieldDescriptor.isRepeated()){
                            if(message.hasField(fieldDescriptor)){
                                writeMethod.invoke(target, value);
                            }
                        }else{
                            throw new IllegalStateException("Repeated filed cannot not convert to primitive type");
                        }

                    } else if (Collection.class.isAssignableFrom(clz) || clz.isArray()) {
                        if(!fieldDescriptor.isRepeated()){
                            throw new IllegalStateException("Property of the bean not equals to type within message.");
                        }



                        int repeatedFieldCount = message.getRepeatedFieldCount(fieldDescriptor);
                        if (repeatedFieldCount <= 0) {
                            continue;
                        }

                        if (clz.isArray()) {
                            Object array = Array.newInstance(clz.getComponentType(), repeatedFieldCount);
                            for(int index = 0; index < repeatedFieldCount;index++){
                                Array.set(array, index, message.getRepeatedField(fieldDescriptor, index));
                            }
                        }else{
                            ArrayList values = new ArrayList<>();
                            for(int index = 0; index < repeatedFieldCount;index++){
                                values.add(message.getRepeatedField(fieldDescriptor, index));
                            }

                            writeMethod.invoke(target, values);
                        }

                    }

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

    public static void main(String[] args){



    }
}
