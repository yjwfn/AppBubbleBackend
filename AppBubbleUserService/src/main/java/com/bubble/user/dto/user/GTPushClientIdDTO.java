package com.bubble.user.dto.user;

import java.io.Serializable;


/**
 *用户
 */
public class GTPushClientIdDTO implements Serializable {

	//ID
	private Long id ;
	//用户ID
	private Long userId ;
	//个推CID
	private String clientId ;
 
 	public void setId(Long  id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}
 
 	public void setUserId(Long  userId){
		this.userId = userId;
	}

	public Long getUserId(){
		return this.userId;
	}
 
 	public void setClientId(String  clientId){
		this.clientId = clientId;
	}

	public String getClientId(){
		return this.clientId;
	}

}

