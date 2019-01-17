package com.bubble.user.dto.user;

/**
 * Created by yjwfn on 2018/3/19.
 */
public class UserContactDTO {
    private String phone;
    private String phoneExt;
    private String email;


    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
