package com.bubble.sms.vo;


import javax.validation.constraints.NotBlank;

/**
 * Created by yjwfn on 2018/1/26.
 */
public class SendSmsVO {

    @NotBlank(message = "Failed to send sms with a null or empty phone")
    private String phone;
    @NotBlank(message = "Invalid area code")
    private String phoneExt;

    public String getPhone() {
        return phone;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }
}
