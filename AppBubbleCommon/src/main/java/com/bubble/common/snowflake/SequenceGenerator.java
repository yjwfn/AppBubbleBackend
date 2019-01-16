package com.bubble.common.snowflake;

/**
 * 生成唯一ID
 */
public interface SequenceGenerator {


    /**
     * 获取下一个唯一ID
     * @return
     */
    long next();


}
