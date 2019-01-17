package com.bubble.user.dto.social;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *社交平台信息
 */
public class SocialPlatformQueryDTO implements Serializable {

	//用户ID
	private Long userId ;
	//社交平台用户唯一标识
	private String openid ;
	//平台交互访问令牌
	private String accessToken ;
	//社交平台类型
	private String type ;
	//记录创建时间
	private Timestamp createTime ;
	//页码
	private Integer page = 1;
	//条数
	private Integer size = 10;

  
 	public void setUserId(Long  userId){
		this.userId = userId;
	}

	public Long getUserId(){
		return this.userId;
	}
 
 	public void setOpenid(String  openid){
		this.openid = openid;
	}

	public String getOpenid(){
		return this.openid;
	}
 
 	public void setAccessToken(String  accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return this.accessToken;
	}
 
 	public void setType(String  type){
		this.type = type;
	}

	public String getType(){
		return this.type;
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

}

