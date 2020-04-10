package com.scnu.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 评论model
 * Created by ChenZehao
 * on 2020/1/16
 */
public class CommentModel implements Serializable {


    /**
     * id : 4
     * commentId : 7936558208570687488
     * content : 哈哈
     * userId : 7932942737420779520
     * userName : 煕夏
     * userAvatar : /ca23beec5a664783b3e2445e00c7d1ef.jpg
     * issueId : 7936064875257724928
     * ownerUserId : 7936063423911100416
     * time : 2020-04-09 19:52:27
     * list1 : []
     */

    private int id;
    private String commentId;
    private String content;
    private String userId;
    private String userName;
    private String userAvatar;
    private String issueId;
    private String ownerUserId;
    private String time;
    private List<SecondaryCommentModel> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<SecondaryCommentModel> getList() {
        return list;
    }

    public void setList(List<SecondaryCommentModel> list) {
        this.list = list;
    }
}
