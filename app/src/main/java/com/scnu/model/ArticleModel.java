package com.scnu.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章model
 * Created by ChenZehao
 * on 2020/1/16
 */
public class ArticleModel implements Serializable {

    private String issueId;//文章id

    private String title;//标题

    private String content;//正文

    private String picture;//封面图片

    private List<PictureModel> pictureList;//发布图片列表

    @JSONField(serialize = false)
    private List<String> imageList;//接收图片列表

    private String theme;//主题

    private LocationModel location;//位置

    @JSONField(serialize = false)
    private int collectionNum;//收藏数

    @JSONField(serialize = false)
    private int likeNum;//点赞数

    @JSONField(serialize = false)
    private int commentNum;//评论数

    private String userId;//发布者id

    private String userName;//发布者名称

    private String userAvatar;//发布者头像

    private String time;//发布时间

    private String price;//价格区间

    private boolean likeFlag;//是否点赞

    private boolean collectionFlag;//是否收藏

    private boolean attentionFlag;//是否关注

    private double distance;//距离

    public ArticleModel(){
        issueId = "";
        title = "";
        content = "";
        picture = "";
        pictureList = new ArrayList<>();
        imageList = new ArrayList<>();
        theme = "";
        location = new LocationModel();
        collectionNum = 0;
        likeNum = 0;
        commentNum = 0;
        userId = "";
        userAvatar = "";
        userName = "";
        time = "";
        price = "";
        likeFlag = false;
        collectionFlag = false;
        attentionFlag = false;
        distance = -1;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<PictureModel> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<PictureModel> pictureList) {
        this.pictureList = pictureList;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(boolean likeFlag) {
        this.likeFlag = likeFlag;
    }

    public boolean isCollectionFlag() {
        return collectionFlag;
    }

    public void setCollectionFlag(boolean collectionFlag) {
        this.collectionFlag = collectionFlag;
    }

    public boolean isAttentionFlag() {
        return attentionFlag;
    }

    public void setAttentionFlag(boolean attentionFlag) {
        this.attentionFlag = attentionFlag;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
