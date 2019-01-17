package com.bubble.user.dto.user;

import java.io.Serializable;


/**
 *用户
 */
public class GTPushClientIdQueryDTO implements Serializable {

	//用户ID
	private Long userId ;
	//个推CID
	private String clientId ;
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
 
 	public void setClientId(String  clientId){
		this.clientId = clientId;
	}

	public String getClientId(){
		return this.clientId;
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

