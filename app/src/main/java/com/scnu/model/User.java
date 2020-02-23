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

    private String sex;//性别

    private String userId;//用户ID

    private String avatarUrl;//用户头像

    private List<String> collectionList;//收藏文章id列表

    private List<String> likeList;//点赞文章id列表

    private List<String> attentionUserList;//关注用户id列表

    public User(){
        mobileNo = "";
        name = "";
        sex = "";
        userId = "";
        avatarUrl = "";
        attentionUserList = new ArrayList<>();
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<String> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<String> collectionList) {
        this.collectionList = collectionList;
    }

    public List<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<String> likeList) {
        this.likeList = likeList;
    }

    public List<String> getAttentionUserList() {
        return attentionUserList;
    }

    public void setAttentionUserList(List<String> attentionUserList) {
        this.attentionUserList = attentionUserList;
    }
}
