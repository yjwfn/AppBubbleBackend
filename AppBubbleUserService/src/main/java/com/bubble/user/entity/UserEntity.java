package com.bubble.user.entity;

/**
*用户
*/
public class UserEntity  {

    private Long id;
    //用户名称
    private String username;
    //用户头像URL
    private String avatarUrl ;
    //用户名称
    private String nickName ;
    //手机号
    private String phone ;
    private String phoneExt;
    //区号
    //用户email
    private String email ;
    //用户密码
    private String password ;
    //用户密码混淆值
    private String passwordSalt;
    //性别(0: 未设置）
    private Integer gender ;
    //用户个性签名
    private String selfSignature ;
    //用户生日
    private Long birthday ;
    //用户状态
    private String status;

    //用户国家（地区）
    private String country ;
    //用户所在省份、洲
    private String state;
    //用户所在城市
    private String city ;

    private long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUrl(){
        return this.avatarUrl;
    }

    public void setAvatarUrl(String  avatarUrl){
        this.avatarUrl = avatarUrl;
    }
        
    public String getNickName(){
        return this.nickName;
    }

    public void setNickName(String  nickName){
        this.nickName = nickName;
    }
        
    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String  phone){
        this.phone = phone;
    }
        
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String  email){
        this.email = email;
    }
        
    public String getPassword(){
        return this.password;
    }

    public void setPassword(String  password){
        this.password = password;
    }
        
    public Integer getGender(){
        return this.gender;
    }

    public void setGender(Integer  gender){
        this.gender = gender;
    }
        
    public String getSelfSignature(){
        return this.selfSignature;
    }

    public void setSelfSignature(String  selfSignature){
        this.selfSignature = selfSignature;
    }


    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getCountry(){
        return this.country;
    }

    public void setCountry(String  country){
        this.country = country;
    }
        
    public String getState(){
        return this.state;
    }

    public void setState(String  state){
        this.state = state;
    }
        
    public String getCity(){
        return this.city;
    }

    public void setCity(String  city){
        this.city = city;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}

