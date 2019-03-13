package com.bubble.user.relation.dto;


public class UserRelationDto {
    /**
     * 记录主键
     */
    private Long id;

    /**
     * 关注者
     */
    private Long followerId;

    /**
     * 关注者昵称
     */
    private String followerNickname;

    /**
     * 关注者个性签名
     */
    private String followerSelfSignature;

    /**
     * 关注者用户头像
     */
    private String followerAvatarUrl;

    /**
     * 被关注的用户的id
     */
    private Long userId;

    /**
     * 被关注的昵称
     */
    private String userNickname;

    /**
     * 被关注的个性签名
     */
    private String userSelfSignature;

    /**
     * 被关注的人的用户头像
     */
    private String userAvatarUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFollowerNickname() {
        return followerNickname;
    }

    public void setFollowerNickname(String followerNickname) {
        this.followerNickname = followerNickname;
    }

    public String getFollowerSelfSignature() {
        return followerSelfSignature;
    }

    public void setFollowerSelfSignature(String followerSelfSignature) {
        this.followerSelfSignature = followerSelfSignature;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserSelfSignature() {
        return userSelfSignature;
    }

    public void setUserSelfSignature(String userSelfSignature) {
        this.userSelfSignature = userSelfSignature;
    }

    public String getFollowerAvatarUrl() {
        return followerAvatarUrl;
    }

    public void setFollowerAvatarUrl(String followerAvatarUrl) {
        this.followerAvatarUrl = followerAvatarUrl;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }
}
