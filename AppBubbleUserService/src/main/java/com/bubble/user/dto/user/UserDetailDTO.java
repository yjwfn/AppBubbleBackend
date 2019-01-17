package com.bubble.user.dto.user;

/**
 * Created by yjwfn on 2018/3/8.
 */
public class UserDetailDTO extends UserDTO {

    private Boolean isFollowing;
    private Integer fansCount;
    private Integer followingCount;

    public Boolean getFollowing() {
        return isFollowing;
    }

    public void setFollowing(Boolean following) {
        isFollowing = following;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }
}
