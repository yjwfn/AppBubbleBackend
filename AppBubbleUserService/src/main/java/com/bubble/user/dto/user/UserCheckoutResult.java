package com.bubble.user.dto.user;

public class UserCheckoutResult {

    private String key;
    private String value;
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
