package com.scnu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户model
 * Created by ChenZehao
 * on 2020/1/16
 */
public class User implements Serializable {

    private String mobileNo;//手机号

    private String name;//昵称

    private String userId;//用户ID

    private String avatarUrl;//用户头像

    private List<Article> collectionList;//收藏列表

    private List<Article> likeList;//点赞列表

    public User(){
        mobileNo = "";
        name = "";
        userId = "";
        avatarUrl = "";
        collectionList = new ArrayList<>();
        likeList = new ArrayList<>();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<Article> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<Article> collectionList) {
        this.collectionList = collectionList;
    }

    public List<Article> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<Article> likeList) {
        this.likeList = likeList;
    }
}
