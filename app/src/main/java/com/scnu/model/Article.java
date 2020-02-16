package com.scnu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章model
 * Created by ChenZehao
 * on 2020/1/16
 */
public class Article implements Serializable {

    private String title;//标题

    private String content;//正文

    private List<String> imageList;//图片列表

    private List<String> themeList;//主题列表

    private String location;//位置

    private int collectionNum;//收藏数

    private int likeNum;//点赞数

    private int commentNum;//评论数

    private User publisher;//发布者

    private String time;//发布时间

    public Article(){
        title = "";
        content = "";
        imageList = new ArrayList<>();
        themeList = new ArrayList<>();
        location = "";
        collectionNum = 0;
        likeNum = 0;
        commentNum = 0;
        publisher = new User();
        time = "";
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

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getThemeList() {
        return themeList;
    }

    public void setThemeList(List<String> themeList) {
        this.themeList = themeList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
