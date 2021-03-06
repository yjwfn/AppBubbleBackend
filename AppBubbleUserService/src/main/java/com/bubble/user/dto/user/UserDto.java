package com.bubble.user.dto.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *用户
 */
public class UserDto implements Serializable {

	//用户ID
	private long id ;
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
	private Long longitude ;
	//用户纬度
	private Long latitude ;
	//用户国家（地区）
	private String country ;
	//用户所在省份、洲
	private String state ;
	//用户所在城市
	private String city ;
	//记录创建时间
	private Long createTime ;

	//用户状态
	private String status;

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


	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
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
 
 	public void setCreateTime(Long  createTime){
		this.createTime = createTime;
	}

	public Long getCreateTime(){
		return this.createTime;
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

}

