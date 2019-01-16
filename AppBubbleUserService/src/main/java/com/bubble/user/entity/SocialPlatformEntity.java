package com.bubble.user.entity;

/**
*社交平台信息
*/
public class SocialPlatformEntity   {

    //用户ID
    private Long userId ;
    //社交平台用户唯一标识
    private String openid ;
    //平台交互访问令牌
    private String accessToken ;
    //社交平台类型
    private String type ;
        
    public Long getUserId(){
        return this.userId;
    }

    public void setUserId(Long  userId){
        this.userId = userId;
    }
        
    public String getOpenid(){
        return this.openid;
    }

    public void setOpenid(String  openid){
        this.openid = openid;
    }
        
    public String getAccessToken(){
        return this.accessToken;
    }

    public void setAccessToken(String  accessToken){
        this.accessToken = accessToken;
    }
        
    public String getType(){
        return this.type;
    }

    public void setType(String  type){
        this.type = type;
    }
                        
}

