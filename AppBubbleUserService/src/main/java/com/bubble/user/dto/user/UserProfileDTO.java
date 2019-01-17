package com.bubble.user.dto.user;

import java.io.Serializable;

/**
 *视频点赞
 */
public class UserProfileDTO implements Serializable {

	//用户ID
	private Long id ;
	//用户头像URL
	private String avatarUrl ;
	//用户名称
	private String nickName ;
	//性别(0: 未设置）
	private Integer gender ;
	//用户个性签名
	private String selfSignature ;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getSelfSignature() {
		return selfSignature;
	}

	public void setSelfSignature(String selfSignature) {
		this.selfSignature = selfSignature;
	}
}

