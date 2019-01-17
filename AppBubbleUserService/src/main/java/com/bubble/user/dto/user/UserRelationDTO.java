package com.bubble.user.dto.user;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *用户关系
 */
public class UserRelationDTO implements Serializable {

	//记录ID
	private Long id ;
	//
	private Timestamp relationTime ;
	//被关注的用户ID
	private Long followerId ;
	//关注者名称
	private String followerNickName ;
	//关注者头像
	private String followerAvatarUrl ;
	//关注者用户个性签名
	private String followerSelfSignature ;
	//关注的用户ID
	private Long followingId ;
	//关注的用户名称
	private String followingNickName ;
	//关注的用户头像
	private String followingAvatarUrl ;
	//关注的用户个性签名
	private String followingSelfSignature ;
	//记录创建时间
	private Timestamp createTime ;
 
 	public void setId(Long  id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}
 
 	public void setRelationTime(Timestamp  relationTime){
		this.relationTime = relationTime;
	}

	public Timestamp getRelationTime(){
		return this.relationTime;
	}
 
 	public void setFollowerId(Long  followerId){
		this.followerId = followerId;
	}

	public Long getFollowerId(){
		return this.followerId;
	}
 
 	public void setFollowerNickName(String  followerNickName){
		this.followerNickName = followerNickName;
	}

	public String getFollowerNickName(){
		return this.followerNickName;
	}
 
 	public void setFollowerAvatarUrl(String  followerAvatarUrl){
		this.followerAvatarUrl = followerAvatarUrl;
	}

	public String getFollowerAvatarUrl(){
		return this.followerAvatarUrl;
	}
 
 	public void setFollowerSelfSignature(String  followerSelfSignature){
		this.followerSelfSignature = followerSelfSignature;
	}

	public String getFollowerSelfSignature(){
		return this.followerSelfSignature;
	}
 
 	public void setFollowingId(Long  followingId){
		this.followingId = followingId;
	}

	public Long getFollowingId(){
		return this.followingId;
	}
 
 	public void setFollowingNickName(String  followingNickName){
		this.followingNickName = followingNickName;
	}

	public String getFollowingNickName(){
		return this.followingNickName;
	}
 
 	public void setFollowingAvatarUrl(String  followingAvatarUrl){
		this.followingAvatarUrl = followingAvatarUrl;
	}

	public String getFollowingAvatarUrl(){
		return this.followingAvatarUrl;
	}
 
 	public void setFollowingSelfSignature(String  followingSelfSignature){
		this.followingSelfSignature = followingSelfSignature;
	}

	public String getFollowingSelfSignature(){
		return this.followingSelfSignature;
	}
 
 	public void setCreateTime(Timestamp  createTime){
		this.createTime = createTime;
	}

	public Timestamp getCreateTime(){
		return this.createTime;
	}
    
}

