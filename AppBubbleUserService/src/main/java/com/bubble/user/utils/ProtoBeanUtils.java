package com.bubble.user.utils;

import com.bubble.sms.grpc.user.message.UserMessageProto;
import com.bubble.user.dto.user.UserDto;
import com.google.common.base.Preconditions;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProtoBeanUtils {

    @SuppressWarnings("unchecked")
    public static void copyProperties(Message source, Object target){
        Preconditions.checkNotNull(source, "Message must not be null");
        Preconditions.checkNotNull(target, "Target must not be null");


        Descriptors.Descriptor messageDescriptor = source.getDescriptorForType();
        List<Descriptors.FieldDescriptor> fieldDescriptors = messageDescriptor.getFields();

        try{

            for(Descriptors.FieldDescriptor fieldDescriptor : fieldDescriptors){
                PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(target, fieldDescriptor.getName());
                Class<?> properyType = propertyDescriptor.getPropertyType();
                Descriptors.FieldDescriptor.JavaType protoType = fieldDescriptor.getType().getJavaType();

                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }


                checkType(fieldDescriptor, propertyDescriptor);



            }

        }catch (Throwable throwable){
            throw new IllegalStateException(throwable);
        }



        for(PropertyDescriptor propertyDescriptor : propertyDescriptors){
            String propertyName = propertyDescriptor.getName();
            Descriptors.FieldDescriptor fieldDescriptor = messageDescriptor.findFieldByName(propertyName);
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
                    if(clz.isPrimitive() || clz == String.class){
                        Object value = source.getField(fieldDescriptor);
                        if(!fieldDescriptor.isRepeated()){
                            if(source.hasField(fieldDescriptor)){
                                writeMethod.invoke(target, value);
                            }
                        }else{
                            throw new IllegalStateException("Repeated filed cannot not convert to primitive type");
                        }

                    } else if (Collection.class.isAssignableFrom(clz) || clz.isArray()) {
                        if(!fieldDescriptor.isRepeated()){
                            throw new IllegalStateException("Property of the bean not equals to type within message.");
                        }


                        int repeatedFieldCount = source.getRepeatedFieldCount(fieldDescriptor);
                        if (repeatedFieldCount <= 0) {
                            continue;
                        }

                        if (clz.isArray()) {
                            Class<?> componentType = clz.getComponentType();
                            Object array = Array.newInstance(clz.getComponentType(), repeatedFieldCount);
                            for(int index = 0; index < repeatedFieldCount;index++){

                                if (componentType.isPrimitive() || componentType == String.class) {
                                    Array.set(array, index, source.getRepeatedField(fieldDescriptor, index));
                                }else {



                                }

                            }

                            writeMethod.invoke(target, array);
                        }else{
                            ArrayList values = new ArrayList<>();
                            for(int index = 0; index < repeatedFieldCount;index++){
                                values.add(source.getRepeatedField(fieldDescriptor, index));
                            }

                            writeMethod.invoke(target, values);
                        }

                    }else{

                    }

                }
                catch (Throwable ex) {
                    throw new IllegalStateException(
                            "Could not copy property '" + propertyDescriptor.getName() + "' from source to target", ex);
                }
            }
        }
    }


    private static void checkType(Descriptors.FieldDescriptor fieldDescriptor, PropertyDescriptor propertyDescriptor){
        Class<?> propertyType = propertyDescriptor.getPropertyType();
        Descriptors.FieldDescriptor.JavaType protoType = fieldDescriptor.getType().getJavaType();


        Preconditions.checkState(fieldDescriptor.isRepeated() ^ (Collection.class.isAssignableFrom(propertyType) || propertyType.isArray()));
        Preconditions.checkState(fieldDescriptor.isMapField() ^ (Map.class.isAssignableFrom(propertyType)));

        if(fieldDescriptor.isRepeated()){
            if (propertyType.isArray()) {
                propertyType = propertyType.getComponentType();
            }else {
                ParameterizedType parameterizedType = (ParameterizedType) TypeToken.of(propertyType).getType();
                propertyType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            }
        }

        switch (protoType){

            case INT:
                Preconditions.checkState(propertyType != int.class && propertyType != Integer.class);
                break;
            case LONG:
                Preconditions.checkState(propertyType != long.class && propertyType != Long.class);
                break;
            case FLOAT:
                Preconditions.checkState(propertyType != float.class && propertyType != Float.class);
                break;
            case DOUBLE:
                Preconditions.checkState(propertyType != double.class && propertyType != Double.class);
                break;
            case BOOLEAN:
                Preconditions.checkState(propertyType != boolean.class && propertyType != Boolean.class);
                break;
            case STRING:
            case BYTE_STRING:
            case ENUM:

                break;
            case MESSAGE:
                Preconditions.checkState(!propertyType.isPrimitive());
                break;
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
        UserMessageProto.UserProfile.Builder userProfileBuilder = UserMessageProto.UserProfile.newBuilder();
        userProfileBuilder.setPhone("18817096723");
        userProfileBuilder.setPhoneExt("86");
        userProfileBuilder.setGender(1);
        userProfileBuilder.setCity("123");
        userProfileBuilder.setEmail("head");
        userProfileBuilder.setState("22");
        userProfileBuilder.setBirthday(100);
        userProfileBuilder.addAddress( "HeHe");
        userProfileBuilder.addAddress( "LaLa");
        UserMessageProto.UserProfile userProfile = userProfileBuilder.build();

        UserDto  userDto = new UserDto();

        copyProperties(userProfile, userDto);


        System.out.println(userDto);

    }
}
