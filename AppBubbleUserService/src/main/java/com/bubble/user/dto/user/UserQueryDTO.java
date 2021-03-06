package com.bubble.user.dto.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *用户
 */
public class UserQueryDTO implements Serializable {

	//用户头像URL
	private String avatarUrl ;
	//用户名称
	private String nickName ;
	//手机号
	private String phone ;
    //手机区号
    private String phoneExt;
    //用户email
	private String email ;
	//用户密码
	private String password ;
	//性别(0: 未设置）
	private Integer gender ;
	//用户个性签名
	private String selfSignature ;
	//用户生日
	private String birthday ;
	//用户位置：经度
	private BigDecimal longitude ;
	//用户纬度
	private BigDecimal latitude ;
	//用户国家（地区）
	private String country ;
	//用户所在省份、洲
	private String state ;
	//用户所在城市
	private String city ;
	//记录创建时间
	private Timestamp createTime ;
	//页码
	private Integer page = 1;
	//条数
	private Integer size = 10;

    private Long startTime;
    private Long endTime;
  
 	public void setAvatarUrl(String  avatarUrl){
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl(){
		return this.avatarUrl;
	}
 
 	public void setNickName(String  nickName){
		this.nickName = nickName;
	}

	public String getNickName(){
		return this.nickName;
	}
 
 	public void setPhone(String  phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}
 
 	public void setEmail(String  email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}
 
 	public void setPassword(String  password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}
 
 	public void setGender(Integer  gender){
		this.gender = gender;
	}

	public Integer getGender(){
		return this.gender;
	}
 
 	public void setSelfSignature(String  selfSignature){
		this.selfSignature = selfSignature;
	}

	public String getSelfSignature(){
		return this.selfSignature;
	}
 
 	public void setBirthday(String  birthday){
		this.birthday = birthday;
	}

	public String getBirthday(){
		return this.birthday;
	}
 
 	public void setLongitude(BigDecimal  longitude){
		this.longitude = longitude;
	}

	public BigDecimal getLongitude(){
		return this.longitude;
	}
 
 	public void setLatitude(BigDecimal  latitude){
		this.latitude = latitude;
	}

	public BigDecimal getLatitude(){
		return this.latitude;
	}
 
 	public void setCountry(String  country){
		this.country = country;
	}

	public String getCountry(){
		return this.country;
	}
 
 	public void setState(String  state){
		this.state = state;
	}

	public String getState(){
		return this.state;
	}
 
 	public void setCity(String  city){
		this.city = city;
	}

	public String getCity(){
		return this.city;
	}
 
 	public void setCreateTime(Timestamp  createTime){
		this.createTime = createTime;
	}

	public Timestamp getCreateTime(){
		return this.createTime;
	}
    
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}

