package com.bubble.user.dto.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *用户
 */
public class UserDto implements Serializable {

	//用户ID
	private Long id ;
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
	//性别(0: 未设置）
	private Integer gender ;
	//用户个性签名
	private String selfSignature ;
	//用户生日
	private Long birthday ;
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
	private long createTime ;
 
 	public void setId(Long  id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}
 
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


	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
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
 
 	public void setCreateTime(long  createTime){
		this.createTime = createTime;
	}

	public long getCreateTime(){
		return this.createTime;
	}


	public String getPhoneExt() {
		return phoneExt;
	}

	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}
}

