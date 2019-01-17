package com.bubble.user.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UserCheckoutResult", description = "检查结果")
public class UserCheckoutResult {

    @ApiModelProperty(value = "传递给接口的字段类型")
    private String key;
    @ApiModelProperty(value = "检查的值")
    private String value;
    @ApiModelProperty(value = "表示value值是否可用")
    private boolean available;

    public UserCheckoutResult() {
    }

    public UserCheckoutResult(String key, String value, boolean available) {
        this.key = key;
        this.value = value;
        this.available = available;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isAvailable() {
        return available;
    }
}
