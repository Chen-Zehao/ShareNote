package com.scnu.model;

import com.scnu.enums.SexEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户model
 * Created by ChenZehao
 * on 2020/1/16
 */
public class UserModel implements Serializable {

    private String mobileNo;//手机号

    private String name;//昵称

    private String sex;//性别

    private String userId;//用户ID

    private String avatarUrl;//用户头像

    private String birthDay;//用户生日

    private String location;//用户位置

    private int attentionNum;//关注数

    private int fansNum;//粉丝数

    private String collectionList;//收藏文章id列表

    private String likeList;//点赞文章id列表

    private String attentionUserList;//关注用户id列表

    private boolean attentionFlag = false;//是否被用户关注

    public UserModel(){
        mobileNo = "";
        name = "";
        sex = SexEnum.getUNKNOWN();
        userId = "";
        avatarUrl = "";
        birthDay = "";
        location = "";
        attentionNum = 0;
        fansNum = 0;
        attentionUserList = "";
        collectionList = "";
        likeList = "";
        attentionFlag = false;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(int attentionNum) {
        this.attentionNum = attentionNum;
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

    public String getLocation() {
        return location;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(String collectionList) {
        this.collectionList = collectionList;
    }

    public String getLikeList() {
        return likeList;
    }

    public void setLikeList(String likeList) {
        this.likeList = likeList;
    }

    public String getAttentionUserList() {
        return attentionUserList;
    }

    public void setAttentionUserList(String attentionUserList) {
        this.attentionUserList = attentionUserList;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public boolean isAttentionFlag() {
        return attentionFlag;
    }

    public void setAttentionFlag(boolean attentionFlag) {
        this.attentionFlag = attentionFlag;
    }
}
