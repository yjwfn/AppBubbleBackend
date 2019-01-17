package com.bubble.user.dto.user;


/**
 * Created by yjwfn on 2018/1/26.
 */
public class UpdatePassword {

    private Long userId;
    private String newPassword;
    private String confirmPassword;
    private String oldPassword;


    public UpdatePassword(Long userId, String newPassword, String confirmPassword, String oldPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
        this.oldPassword = oldPassword;
    }

    public UpdatePassword() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
